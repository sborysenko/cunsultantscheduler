package com.itera.cunsultantscheduler.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Consultant")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Consultant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double ratePerHour;
    private Integer hoursPerDay;
}
