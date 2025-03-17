package com.itera.cunsultantscheduler;

import com.itera.cunsultantscheduler.dto.Booking;
import com.itera.cunsultantscheduler.model.Assignment;
import com.itera.cunsultantscheduler.model.Consultant;
import com.itera.cunsultantscheduler.model.Customer;
import com.itera.cunsultantscheduler.repository.Repository;
import com.itera.cunsultantscheduler.service.AssignmentService;
import lombok.extern.slf4j.Slf4j;
import org.dataloader.BatchLoaderEnvironment;
import org.dataloader.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.graphql.execution.BatchLoaderRegistry;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class GraphQlController {
    final private Repository repository;
    final private AssignmentService assignmentService;

    @Autowired
    public GraphQlController(
            Repository repository,
            AssignmentService assignmentService,
            BatchLoaderRegistry batchLoaderRegistry) {
        this.repository = repository;
        this.assignmentService = assignmentService;

        batchLoaderRegistry.forTypePair(Long.class, Customer.class)
                .registerMappedBatchLoader((Set<Long> keys, BatchLoaderEnvironment env) -> {
                    log.info("Load customers with keys {}", keys);
                    Map<Long, Customer> customers =
                            repository.getCustomers(keys).stream()
                                            .map(c -> Map.entry(c.getId(), c))
                                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                    return Mono.just(customers);
                });
        batchLoaderRegistry.forTypePair(Long.class, Consultant.class)
                .registerMappedBatchLoader((Set<Long> keys, BatchLoaderEnvironment env) -> {
                    log.info("Load consultants with keys {}", keys);
                    Map<Long, Consultant> consultants =
                            repository.getConsultants(keys).stream()
                                    .map(c -> Map.entry(c.getId(), c))
                                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                    return Mono.just(consultants);
                });
    }

    @QueryMapping
    public List<Booking> bookings() {
        return assignmentService.getAllBookings();
    }

    @QueryMapping
    public List<Consultant> consultants() {
        return repository.getConsultants();
    }

    @QueryMapping
    public List<Customer> customers() {
        return repository.getCustomers();
    }

    @QueryMapping
    public List<Assignment> assignments() {
        return repository.getAllAssignments();
    }

    @SchemaMapping
    public CompletableFuture<Customer> customer(Assignment assignment, DataLoader<Long, Customer> dataLoader) {
        return dataLoader.load(assignment.getCustomerId());
    }

    @SchemaMapping
    public CompletableFuture<Consultant> consultant(Assignment assignment, DataLoader<Long, Consultant> dataLoader) {
        return dataLoader.load(assignment.getConsultantId());
    }
}
