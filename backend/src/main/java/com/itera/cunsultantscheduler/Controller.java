package com.itera.cunsultantscheduler;

import com.itera.cunsultantscheduler.dto.Booking;
import com.itera.cunsultantscheduler.model.Consultant;
import com.itera.cunsultantscheduler.model.Customer;
import com.itera.cunsultantscheduler.repository.Repository;
import com.itera.cunsultantscheduler.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Controller {
    final private Repository repository;
    final private AssignmentService assignmentService;

    @Autowired
    public Controller(Repository repository, AssignmentService assignmentService) {
        this.repository = repository;
        this.assignmentService = assignmentService;
    }

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return repository.getCustomers();
    }

    @GetMapping("/consultants")
    public List<Consultant> hetAllConsultants() {
        return repository.getConsultants();
    }

    @GetMapping("/assignments")
    public List<Booking> getAllAssignments() {
        return assignmentService.getAllBookings();
    }
}
