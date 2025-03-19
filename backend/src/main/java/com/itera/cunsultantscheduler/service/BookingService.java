package com.itera.cunsultantscheduler.service;

import com.itera.cunsultantscheduler.dto.*;
import com.itera.cunsultantscheduler.model.Assignment;
import com.itera.cunsultantscheduler.model.Consultant;
import com.itera.cunsultantscheduler.model.Customer;
import com.itera.cunsultantscheduler.model.ForecastDay;
import com.itera.cunsultantscheduler.repository.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class BookingService {
    final private CustomerRepository customerRepository;
    final private ConsultantRepository consultantRepository;
    final private AssignmentRepository assignmentRepository;
    final private ForecastDayRepository forecastDayRepository;

    @Autowired
    public BookingService(
            CustomerRepository customerRepository,
            ConsultantRepository consultantRepository,
            AssignmentRepository assignmentRepository,
            ForecastDayRepository forecastDayRepository) {
        this.customerRepository = customerRepository;
        this.consultantRepository = consultantRepository;
        this.assignmentRepository = assignmentRepository;
        this.forecastDayRepository = forecastDayRepository;
    }

    public List<Consultant> getAllConsultants() {
        log.info("Get all consultants");
        return StreamSupport
                .stream(consultantRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public List<Consultant> getConsultants(Set<Long> ids) {
        log.info("Get consultants by ID set {}", ids);
        return StreamSupport
                .stream(consultantRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());

    }

    public List<Customer> getAllCustomers() {
        log.info("Get all customers");
        return StreamSupport
                .stream(customerRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public List<Customer> getCustomers(Set<Long> ids) {
        log.info("Get customers by ID set {}", ids);
        return StreamSupport
                .stream(customerRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    public List<Assignment> getAllAssignmens() {
        return StreamSupport
                .stream(assignmentRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Consultant addConsultant(ConsultantInput consultantInput) {
        return consultantRepository.save(Consultant.builder()
                        .name(consultantInput.getName())
                        .ratePerHour(consultantInput.getRatePerHour())
                        .hoursPerDay(consultantInput.getHoursPerDay())
                .build());
    }

    public Customer addCustomer(CustomerInput customerInput) {
        return customerRepository.save(Customer.builder()
                .name(customerInput.getName())
                .build());
    }

    public Assignment assign(Long consultantId, Long customerId, String name) {
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        Consultant consultant = consultantRepository.findById(consultantId).orElseThrow();

        return assignmentRepository.save(Assignment.builder()
                .customer(customer)
                .consultant(consultant)
                .name(name)
                .build());
    }

    public ForecastDay addForecastDay(Long assignmentId, String date, Integer hoursWorked) {
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow();
        return forecastDayRepository.save(ForecastDay.builder()
                .assignment(assignment)
                .date(LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE))
                .hoursWorked(hoursWorked)
                .build());
    }

    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        Iterable<Consultant> consultants = consultantRepository.findAll();
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
        Iterable<Assignment> assignments = assignmentRepository.getAllByConsultantId(consultant.getId());

        List<AssignmentDto> dtos = new ArrayList<>();
        assignments.forEach(a -> dtos.add(buildConsultantAssignment(a, consultant)));
        return dtos;
    }

    private AssignmentDto buildConsultantAssignment(Assignment assignment, Consultant consultant) {
        Customer customer = customerRepository.findById(assignment.getCustomer().getId()).orElseThrow();
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
