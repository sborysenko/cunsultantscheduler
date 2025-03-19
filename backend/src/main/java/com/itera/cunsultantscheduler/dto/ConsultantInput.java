package com.itera.cunsultantscheduler.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConsultantInput {
    private String name;
    private Double ratePerHour;
    private Integer hoursPerDay;
}
