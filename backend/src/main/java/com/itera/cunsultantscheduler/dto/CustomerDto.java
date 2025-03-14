package com.itera.cunsultantscheduler.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerDto {
        private Long id;
        private String name;
}
