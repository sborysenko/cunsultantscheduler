package com.itera.cunsultantscheduler.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Booking {
    ConsultantDtd consultant;
    List<AssignmentDto> assignments;
}
