package com.itera.cunsultantscheduler;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupingExample {
    public static void main(String[] args) {
        List<DayRecord> records = Arrays.asList(
                new DayRecord(LocalDate.of(2024, 3, 1), "Data A"),
                new DayRecord(LocalDate.of(2024, 3, 2), "Data B"),
                new DayRecord(LocalDate.of(2024, 3, 8), "Data C"),
                new DayRecord(LocalDate.of(2024, 3, 15), "Data D"),
                new DayRecord(LocalDate.of(2024, 3, 19), "Data D1"),
                new DayRecord(LocalDate.of(2024, 3, 21), "Data D2"),
                new DayRecord(LocalDate.of(2024, 3, 31), "Data D3"),
                new DayRecord(LocalDate.of(2024, 4, 2), "Data E"),
                new DayRecord(LocalDate.of(2024, 4, 8), "Data F")
        );

        // Group by month -> week -> day
        Map<Integer, Map<Integer, List<DayRecord>>> groupedData = records.stream()
                .collect(Collectors.groupingBy(
                        record -> record.getDate().getMonthValue(), // Group by month
                        Collectors.groupingBy(
                                record -> record.getDate().get(WeekFields.of(Locale.getDefault()).weekOfMonth()), // Group by week
                                Collectors.toList() // Group by day within week
                        )
                ));

        // Print the grouped structure
        groupedData.forEach((month, weeks) -> {
            System.out.println("Month: " + month);
            weeks.forEach((week, days) -> {
                System.out.println("  Week: " + week);
                days.forEach(day -> System.out.println("    " + day));
            });
        });
    }

}
