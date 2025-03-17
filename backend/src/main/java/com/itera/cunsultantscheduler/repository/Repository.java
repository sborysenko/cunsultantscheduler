package com.itera.cunsultantscheduler.repository;

import com.itera.cunsultantscheduler.model.Assignment;
import com.itera.cunsultantscheduler.model.Consultant;
import com.itera.cunsultantscheduler.model.Customer;
import com.itera.cunsultantscheduler.model.ForecastDay;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Repository {
    @Getter
    final private List<Consultant> consultants;
    @Getter
    final private List<Customer> customers;
    final private Map<Long, List<Assignment>> assignments;

    public Repository() {
        consultants = new ArrayList<>(
            List.of(
                Consultant.builder()
                        .id(1L)
                        .name("Alice Johnson")
                        .ratePerHour(50.0)
                        .hoursPerDay(8)
                        .build(),
                Consultant.builder()
                        .id(2L)
                        .name("Bob Smith")
                        .ratePerHour(60.00)
                        .hoursPerDay(8)
                        .build(),
                 Consultant.builder()
                        .id(3L)
                        .name("Scott Frederick")
                        .ratePerHour(40.00)
                         .hoursPerDay(8)
                         .build()
            )
        );

        customers = new ArrayList<>(
            List.of(
                Customer.builder()
                        .id(1L)
                        .name("Company A")
                        .build(),
                Customer.builder()
                        .id(2L)
                        .name("Company B")
                        .build()
            )
        );

        assignments = new HashMap<>();

        assignments.put(1L, new ArrayList<>(
                List.of(
                        Assignment.builder()
                                .id(1L)
                                .name("Assignment 1")
                                .consultantId(1L)
                                .customerId(1L)
                                .forecastDays(List.of(
                                        ForecastDay.builder().date(LocalDate.of(2025, 3, 1)).hoursWorked(8).build(),
                                        ForecastDay.builder().date(LocalDate.of(2025, 3, 2)).hoursWorked(8).build(),
                                        ForecastDay.builder().date(LocalDate.of(2025, 3, 3)).hoursWorked(8).build()
                                ))
                                .build(),
                        Assignment.builder()
                                .id(2L)
                                .name("Assignment 2")
                                .consultantId(1L)
                                .customerId(2L)
                                .forecastDays(List.of(
                                        ForecastDay.builder().date(LocalDate.of(2025, 3, 5)).hoursWorked(8).build(),
                                        ForecastDay.builder().date(LocalDate.of(2025, 3, 6)).hoursWorked(8).build(),
                                        ForecastDay.builder().date(LocalDate.of(2025, 3, 7)).hoursWorked(5).build()
                                ))
                                .build()
                )
        ));
        assignments.put(2L, new ArrayList<>(
                List.of(
                        Assignment.builder()
                                .id(3L)
                                .name("Assignment 3")
                                .consultantId(2L)
                                .customerId(1L)
                                .forecastDays(List.of(
                                        ForecastDay.builder().date(LocalDate.of(2025, 3, 1)).hoursWorked(8).build(),
                                        ForecastDay.builder().date(LocalDate.of(2025, 3, 2)).hoursWorked(8).build(),
                                        ForecastDay.builder().date(LocalDate.of(2025, 3, 3)).hoursWorked(8).build()
                                ))
                                .build(),
                        Assignment.builder()
                                .id(4L)
                                .name("Assignment 4")
                                .consultantId(2L)
                                .customerId(2L)
                                .forecastDays(List.of(
                                        ForecastDay.builder().date(LocalDate.of(2025, 3, 5)).hoursWorked(8).build(),
                                        ForecastDay.builder().date(LocalDate.of(2025, 3, 6)).hoursWorked(8).build(),
                                        ForecastDay.builder().date(LocalDate.of(2025, 3, 7)).hoursWorked(5).build()
                                ))
                                .build()
                )
        ));
        assignments.put(3L, new ArrayList<>(
                List.of(
                        Assignment.builder()
                                .id(5L)
                                .name("Assignment 5")
                                .consultantId(3L)
                                .customerId(1L)
                                .forecastDays(List.of(
                                        ForecastDay.builder().date(LocalDate.of(2025, 3, 1)).hoursWorked(8).build(),
                                        ForecastDay.builder().date(LocalDate.of(2025, 3, 2)).hoursWorked(8).build(),
                                        ForecastDay.builder().date(LocalDate.of(2025, 3, 3)).hoursWorked(8).build(),
                                        ForecastDay.builder().date(LocalDate.of(2025, 3, 4)).hoursWorked(8).build(),
                                        ForecastDay.builder().date(LocalDate.of(2025, 3, 5)).hoursWorked(8).build(),
                                        ForecastDay.builder().date(LocalDate.of(2025, 3, 6)).hoursWorked(8).build(),
                                        ForecastDay.builder().date(LocalDate.of(2025, 3, 7)).hoursWorked(8).build(),
                                        ForecastDay.builder().date(LocalDate.of(2025, 3, 10)).hoursWorked(8).build(),
                                        ForecastDay.builder().date(LocalDate.of(2025, 3, 11)).hoursWorked(8).build(),
                                        ForecastDay.builder().date(LocalDate.of(2025, 3, 12)).hoursWorked(8).build(),
                                        ForecastDay.builder().date(LocalDate.of(2025, 3, 13)).hoursWorked(8).build(),
                                        ForecastDay.builder().date(LocalDate.of(2025, 3, 14)).hoursWorked(8).build()
                                ))
                                .build()
                )
        ));
    }

    public Consultant getConsultant(Long id) {
        return consultants.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
    }

    public Customer getCustomer(Long id) {
        return customers.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
    }

    public List<Assignment> getAllAssignments() {
        return assignments.values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public List<Assignment> getConsultantAssignments(Long consultantId) {
        return assignments.get(consultantId);
    }
}

