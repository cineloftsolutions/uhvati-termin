package com.cineloftsolutions.uhvati_termin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO za kreiranje lokacije")
public class CreateLocationDTO {

    @NotBlank(message = "Ime lokacije je obavezno")
    @Size(min = 2, max = 100, message = "Ime lokacije mora imati između 2 i 100 karaktera")
    @Schema(description = "Ime lokacije", example = "Studio A")
    private String name;

    @NotBlank(message = "Adresa je obavezna")
    @Size(min = 5, max = 255, message = "Adresa mora imati između 5 i 255 karaktera")
    @Schema(description = "Adresa lokacije", example = "Kralja Petra 5")
    private String address;

    @NotBlank(message = "Grad je obavezan")
    @Size(min = 2, max = 100, message = "Grad mora imati između 2 i 100 karaktera")
    @Schema(description = "Grad u kojem se nalazi lokacija", example = "Beograd")
    private String city;
}
