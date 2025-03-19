package com.itera.cunsultantscheduler;

import com.itera.cunsultantscheduler.dto.ConsultantInput;
import com.itera.cunsultantscheduler.dto.CustomerInput;
import com.itera.cunsultantscheduler.model.Assignment;
import com.itera.cunsultantscheduler.model.Consultant;
import com.itera.cunsultantscheduler.model.Customer;
import com.itera.cunsultantscheduler.model.ForecastDay;
import com.itera.cunsultantscheduler.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.dataloader.BatchLoaderEnvironment;
import org.dataloader.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.graphql.execution.BatchLoaderRegistry;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class GraphQlController {
    final private BookingService bookingService;

    @Autowired
    public GraphQlController(
            BookingService bookingService,
            BatchLoaderRegistry batchLoaderRegistry) {
        this.bookingService = bookingService;

        batchLoaderRegistry.forTypePair(Long.class, Customer.class)
                .registerMappedBatchLoader((Set<Long> keys, BatchLoaderEnvironment env) -> {
                    log.info("Load customers with keys {}", keys);
                    return Mono.just(bookingService
                            .getCustomers(keys)
                            .stream()
                            .map(c -> Map.entry(c.getId(), c))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
                });
        batchLoaderRegistry.forTypePair(Long.class, Consultant.class)
                .registerMappedBatchLoader((Set<Long> keys, BatchLoaderEnvironment env) -> {
                    log.info("Load consultants with keys {}", keys);
                    return Mono.just(bookingService
                            .getConsultants(keys)
                            .stream()
                            .map(c -> Map.entry(c.getId(), c))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
                });
    }

    @QueryMapping
    public Iterable<Consultant> consultants() {
        return bookingService.getAllConsultants();
    }

    @QueryMapping
    public Iterable<Customer> customers() {
        return bookingService.getAllCustomers();
    }

    @QueryMapping
    public Iterable<Assignment> assignments() {
        return bookingService.getAllAssignmens();
    }

    @SchemaMapping
    public CompletableFuture<Customer> customer(Assignment assignment, DataLoader<Long, Customer> dataLoader) {
        return dataLoader.load(assignment.getCustomer().getId());
    }

    @SchemaMapping
    public CompletableFuture<Consultant> consultant(Assignment assignment, DataLoader<Long, Consultant> dataLoader) {
        return dataLoader.load(assignment.getConsultant().getId());
    }

    @MutationMapping
    public Consultant addConsultant(@Argument ConsultantInput consultantInput) {
        log.info("Adding consultant: {}", consultantInput);
        return bookingService.addConsultant(consultantInput);
    }

    @MutationMapping
    public Customer addCustomer(@Argument CustomerInput customerInput) {
        log.info("Adding customer: {}", customerInput);
        return bookingService.addCustomer(customerInput);
    }

    @MutationMapping
    public Assignment assign(@Argument Long consultantId, @Argument Long customerId, @Argument String name) {
        log.info("Adding assignment: [{}, {}, {}]", consultantId, customerId, name);
        return bookingService.assign(consultantId, customerId, name);

    }

    @MutationMapping
    public ForecastDay addForecastDay(@Argument Long assignmentId, @Argument String date, @Argument Integer hoursWorked) {
        log.info("Adding forecast day to assignment: [{}, {}, {}]", assignmentId, date, hoursWorked);
        return bookingService.addForecastDay(assignmentId, date, hoursWorked);
    }

}
