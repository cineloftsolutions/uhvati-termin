package com.cineloftsolutions.uhvati_termin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Zahtev za kreiranje biznisa")
public class CreateBusinessDTO {

    @NotBlank(message = "Ime biznisa je obavezno")
    @Size(min = 3, max = 100, message = "Ime biznisa mora imati između 3 i 100 karaktera")
    @Schema(description = "Ime biznisa", example = "CineLoft Studio")
    private String name;

    @NotBlank(message = "Telefon je obavezan")
    @Size(min = 10, max = 15, message = "Telefon mora imati između 10 i 15 karaktera")
    @Schema(description = "Kontakt telefon biznisa", example = "+381 60 1234567")
    private String phone;

    @NotBlank(message = "Email je obavezan")
    @Email(message = "Email nije u ispravnom formatu")
    @Size(max = 100, message = "Email ne sme imati više od 100 karaktera")
    @Schema(description = "Email biznisa", example = "contact@cineloft.com")
    private String email;

    @NotBlank(message = "Web sajt je obavezan")
    @Schema(description = "Web sajt biznisa", example = "www.cineloft.com")
    private String website;

    @Schema(description = "Opis biznisa", example = "Profesionalni studio za fotografiju")
    private String description;

    @Schema(description = "Logo URL biznisa", example = "https://example.com/logo.png")
    private String logoUrl;
}
