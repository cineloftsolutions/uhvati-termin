package com.cineloftsolutions.uhvati_termin.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class AssignEmployeesToServiceDTO {

    @NotEmpty(message = "Lista zaposlenih ne sme biti prazna")
    private List<EmployeeMapping> employeeMappings;

    @Data
    public static class EmployeeMapping {
        @NotNull(message = "ID zaposlenog je obavezan")
        private Long employeeId;

        @NotNull(message = "Trajanje usluge je obavezno")
        private Integer durationMinutes;
    }
}
