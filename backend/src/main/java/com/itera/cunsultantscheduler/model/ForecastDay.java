package com.itera.cunsultantscheduler.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ForecastDay {
    private LocalDate date;
    private Integer hoursWorked;
}
