package com.itera.cunsultantscheduler.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Consultant {
    private Long id;
    private String name;
    private Double ratePerHour;
    private Integer hoursPerDay;
}
