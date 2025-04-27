package com.cineloftsolutions.uhvati_termin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;


@Data
public class CreateServiceDTO {

    @NotBlank(message = "Naziv usluge je obavezan")
    private String name;

    private String description;

    @NotNull(message = "Cena je obavezna")
    @Positive(message = "Cena mora biti pozitivan broj")
    private Double price;

    @NotNull(message = "Trajanje je obavezno")
    @Positive(message = "Trajanje mora biti pozitivan broj")
    private Integer durationMinutes;

    private List<Long> locationIds;
    private List<Long> employeeIds;
}
