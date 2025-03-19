package com.itera.cunsultantscheduler.service;

import com.itera.cunsultantscheduler.dto.ConsultantInput;
import com.itera.cunsultantscheduler.dto.CustomerInput;
import com.itera.cunsultantscheduler.model.Assignment;
import com.itera.cunsultantscheduler.model.Consultant;
import com.itera.cunsultantscheduler.model.Customer;
import com.itera.cunsultantscheduler.model.ForecastDay;
import com.itera.cunsultantscheduler.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
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

    public Iterable<Consultant> getAllConsultants() {
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
}
