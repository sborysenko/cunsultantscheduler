package com.itera.cunsultantscheduler.dto;

import lombok.Data;

@Data
public class RevenueDay {
    private String day;
    private Double revenue = 0.0;
    private Integer hours = 0;
}
