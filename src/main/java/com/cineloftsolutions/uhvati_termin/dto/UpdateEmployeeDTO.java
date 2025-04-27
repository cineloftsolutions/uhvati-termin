package com.cineloftsolutions.uhvati_termin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Zahtev za kreiranje novog zaposlenog")
public class UpdateEmployeeDTO {
    @NotBlank(message = "Ime je obavezno")
    @Size(min = 2, max = 50, message = "Ime mora imati između 2 i 50 karaktera")
    @Schema(description = "Ime zaposlenog", example = "Marko Marković")
    private String name;

    @NotBlank(message = "Email je obavezan")
    @Email(message = "Email nije u ispravnom formatu")
    @Size(max = 100, message = "Email ne sme imati više od 100 karaktera")
    @Schema(description = "Email zaposlenog", example = "marko@example.com")
    private String email;

    @Size(min = 6, max = 100, message = "Lozinka mora imati između 6 i 100 karaktera")
    @Schema(description = "Lozinka zaposlenog", example = "Lozinka123")
    private String password;

    @NotNull(message = "Lokacija je obavezna")
    @Schema(description = "Lokacija zaposlenog", example = "1")
    private Long locationId;
}
