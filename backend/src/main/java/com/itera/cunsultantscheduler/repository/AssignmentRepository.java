package com.itera.cunsultantscheduler.repository;

import com.itera.cunsultantscheduler.model.Assignment;
import org.springframework.data.repository.CrudRepository;

public interface AssignmentRepository extends CrudRepository<Assignment, Long> {
    Iterable<Assignment> getAllByConsultantId(Long id);
}
