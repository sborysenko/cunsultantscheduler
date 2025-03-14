package com.itera.cunsultantscheduler.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RevenueWeek {
    private Integer weekNumber = 0;
    private Double revenue = 0.0;
    private List<RevenueDay> days = new ArrayList<>();
}
