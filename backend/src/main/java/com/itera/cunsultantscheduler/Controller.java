package com.itera.cunsultantscheduler;

import com.itera.cunsultantscheduler.dto.Booking;
import com.itera.cunsultantscheduler.model.Consultant;
import com.itera.cunsultantscheduler.model.Customer;
import com.itera.cunsultantscheduler.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Controller {
    final private BookingService bookingService;

    @Autowired
    public Controller(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return bookingService.getAllCustomers();
    }

    @GetMapping("/consultants")
    public List<Consultant> hetAllConsultants() {
        return bookingService.getAllConsultants();
    }

    @GetMapping("/assignments")
    public List<Booking> getAllAssignments() {
        return bookingService.getAllBookings();
    }
}
