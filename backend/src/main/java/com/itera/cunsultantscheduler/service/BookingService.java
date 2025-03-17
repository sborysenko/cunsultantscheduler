package com.itera.cunsultantscheduler.service;

import com.itera.cunsultantscheduler.repository.Repository;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    final private Repository repository;
    final private AssignmentService service;

    public BookingService(Repository repository, AssignmentService service) {
        this.repository = repository;
        this.service = service;
    }
}
