package com.cineloftsolutions.uhvati_termin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserProfileDTO {
    @NotBlank(message = "Ime je obavezno")
    @Size(min = 2, max = 50, message = "Ime mora imati između 2 i 50 karaktera")
    @Pattern(regexp = "^[A-Za-zČčĆćŠšĐđŽž ]+$", message = "Ime može sadržati samo slova i razmake")
    private String name;
}
