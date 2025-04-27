package com.cineloftsolutions.uhvati_termin.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
public class AssignLocationsToServiceDTO {
    @NotEmpty(message = "Lista lokacija ne sme biti prazna")
    private List<Long> locationIds;
}
