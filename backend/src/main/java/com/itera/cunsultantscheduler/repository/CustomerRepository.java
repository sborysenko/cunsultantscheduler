package com.itera.cunsultantscheduler.repository;

import com.itera.cunsultantscheduler.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
