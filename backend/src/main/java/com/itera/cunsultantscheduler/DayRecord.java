package com.itera.cunsultantscheduler;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DayRecord {
    private LocalDate date;
    private String data;
}
