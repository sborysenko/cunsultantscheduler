package com.itera.cunsultantscheduler.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Assignment {
    private Long id;
    private String name;
    private Long consultantId;
    private Long customerId;
    private List<ForecastDay> forecastDays;
}
