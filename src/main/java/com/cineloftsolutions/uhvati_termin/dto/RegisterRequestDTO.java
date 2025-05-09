package com.cineloftsolutions.uhvati_termin.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "Zahtev za registraciju korisnika")
public class RegisterRequestDTO {

    @NotBlank(message = "Ime je obavezno")
    @Size(min = 2, max = 50, message = "Ime mora imati između 2 i 50 karaktera")
    @Pattern(regexp = "^[A-Za-zČčĆćŠšĐđŽž ]+$", message = "Ime može sadržati samo slova i razmake")
    @Schema(description = "Ime korisnika", example = "Miloš")
    private String name;

    @NotBlank(message = "Prezime je obavezno")
    @Size(min = 2, max = 50, message = "Prezime mora imati između 2 i 50 karaktera")
    @Pattern(regexp = "^[A-Za-zČčĆćŠšĐđŽž ]+$", message = "Prezime može sadržati samo slova i razmake")
    @Schema(description = "Prezime korisnika", example = "Petrović")
    private String surname;

    @NotBlank(message = "Broj telefona je obavezan")
    @Pattern(regexp = "^\\+?[0-9\\s-]{6,20}$", message = "Broj telefona nije u ispravnom formatu")
    @Schema(description = "Kontakt telefon", example = "+381601234567")
    private String phone;

    @NotBlank(message = "Email je obavezan")
    @Email(message = "Email nije u ispravnom formatu")
    @Size(max = 100, message = "Email ne sme imati više od 100 karaktera")
    @Schema(description = "Email korisnika", example = "milos@gmail.com")
    private String email;

    @NotBlank(message = "Lozinka je obavezna")
    @Size(min = 6, max = 100, message = "Lozinka mora imati između 6 i 100 karaktera")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
            message = "Lozinka mora sadržati bar jedno veliko slovo, jedno malo slovo i jedan broj"
    )
    @Schema(description = "Lozinka korisnika", example = "Lozinka123")
    private String password;

    @NotNull(message = "Biznis ID je obavezan")
    @Schema(description = "Biznis ID", example = "1")
    private Long businessId;
}


