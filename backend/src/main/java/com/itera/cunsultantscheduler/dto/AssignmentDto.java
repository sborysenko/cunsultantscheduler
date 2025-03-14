package com.itera.cunsultantscheduler.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AssignmentDto {
    private Long id;
    private String name;
    private Double revenue;
    private Integer hoursWorked;
    private CustomerDto customer;
    private List<RevenueMonth> revenueMonth;
}
