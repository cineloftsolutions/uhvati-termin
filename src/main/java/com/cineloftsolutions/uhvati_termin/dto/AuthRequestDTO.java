package com.cineloftsolutions.uhvati_termin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Zahtev za prijavu korisnika")
public class AuthRequestDTO {

    @NotBlank(message = "Email je obavezan")
    @Email(message = "Email nije u ispravnom formatu")
    @Size(max = 100, message = "Email ne sme imati više od 100 karaktera")
    @Schema(description = "Email korisnika", example = "milos@gmail.com")
    private String email;

    @NotBlank(message = "Lozinka je obavezna")
    @Size(min = 6, max = 100, message = "Lozinka mora imati između 6 i 100 karaktera")
    @Schema(description = "Lozinka korisnika", example = "Lozinka123")
    private String password;
}
