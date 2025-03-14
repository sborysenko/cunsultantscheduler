package com.itera.cunsultantscheduler.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RevenueMonth {
    private String name;
    private Double revenue = 0.0;
    private List<RevenueWeek> weeks = new ArrayList<>();
}
