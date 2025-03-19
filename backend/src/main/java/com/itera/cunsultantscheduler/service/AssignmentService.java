package com.itera.cunsultantscheduler.service;

import com.itera.cunsultantscheduler.dto.*;
import com.itera.cunsultantscheduler.model.Assignment;
import com.itera.cunsultantscheduler.model.Consultant;
import com.itera.cunsultantscheduler.model.Customer;
import com.itera.cunsultantscheduler.model.ForecastDay;
import com.itera.cunsultantscheduler.repository.Repository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AssignmentService {
    final private Repository repository;

    @Autowired
    public AssignmentService(Repository repository) {
        this.repository = repository;
    }

    public List<Booking> getAllBookings() {
        List<Consultant> consultants = repository.getConsultants();

        List<Booking> bookings = new ArrayList<>();
        consultants.forEach(consultant -> bookings.add(buildConsultantBooking(consultant)));
        return bookings;
    }

    private Booking buildConsultantBooking(Consultant consultant) {
        return Booking.builder()
                .consultant(ConsultantDtd.builder()
                                .id(consultant.getId())
                                .name(consultant.getName())
                                .hoursPerDay(consultant.getHoursPerDay())
                                .ratePerHour(consultant.getRatePerHour())
                                .build())
                .assignments(buildConsultantAssignments(consultant))
                .build();
    }

    private List<AssignmentDto> buildConsultantAssignments(Consultant consultant) {
        List<Assignment> assignments = repository.getConsultantAssignments(consultant.getId());

        List<AssignmentDto> dtos = new ArrayList<>();
        assignments.forEach(a -> dtos.add(buildConsultantAssignment(a, consultant)));
        return dtos;
    }

    private AssignmentDto buildConsultantAssignment(Assignment assignment, Consultant consultant) {
        Customer customer = repository.getCustomer(assignment.getCustomer().getId());
        RevenueHolder revenue = collectRevenue(assignment, consultant);

        return AssignmentDto.builder()
                .id(assignment.getId())
                .name(assignment.getName())
                .revenue(revenue.getRevenue())
                .hoursWorked(revenue.getHours())
                .customer(CustomerDto.builder().id(customer.getId()).name(customer.getName()).build())
                .revenueMonth(revenue.getMonths())
                .build();
    }

    private RevenueHolder collectRevenue(Assignment assignment, Consultant consultant) {
        Map<Month, Map<Integer, List<ForecastDay>>> groupedData = assignment.getForecastDays().stream()
                .collect(Collectors.groupingBy(
                        record -> record.getDate().getMonth(), // Group by month
                        Collectors.groupingBy(
                                record -> record.getDate().get(WeekFields.of(Locale.getDefault()).weekOfMonth()), // Group by week
                                Collectors.toList() // Group by day within week
                        )
                ));

        RevenueHolder holder = new RevenueHolder();

        // Print the grouped structure
        groupedData.forEach((month, weeks) -> {
            RevenueMonth rm = new RevenueMonth();
            rm.setName(month.name());

            weeks.forEach((week, days) -> {
                RevenueWeek rw = new RevenueWeek();
                rw.setWeekNumber(week);

                days.forEach(day -> {
                    RevenueDay rd = new RevenueDay();
                    rd.setRevenue(consultant.getRatePerHour() * day.getHoursWorked());
                    rd.setHours(day.getHoursWorked());
                    rd.setDay(day.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE));

                    holder.addHours(day.getHoursWorked());
                    rw.setRevenue(rw.getRevenue() + rd.getRevenue());
                    rw.getDays().add(rd);
                });

                rm.setRevenue(rm.getRevenue() + rw.getRevenue());
                rm.getWeeks().add(rw);
            });

            holder.addRevenue(rm.getRevenue());
            holder.getMonths().add(rm);
        });

        return holder;
    }

    @Data
    private static class RevenueHolder {
        private Double revenue = 0.0;
        private Integer hours = 0;
        private List<RevenueMonth> months = new ArrayList<>();

        private void addRevenue(Double revenue) {
            this.revenue = this.revenue + revenue;
        }

        private void addHours(Integer hours) {
            this.hours = this.hours + hours;
        }
    }
}
