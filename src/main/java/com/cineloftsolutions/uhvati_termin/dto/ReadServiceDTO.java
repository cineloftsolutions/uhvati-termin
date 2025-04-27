package com.cineloftsolutions.uhvati_termin.dto;

import lombok.Data;
import java.util.List;

@Data
public class ReadServiceDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer durationMinutes;

    private List<ReadLocationDTO> locations;
    private List<ReadUserDTO> employees;
}
