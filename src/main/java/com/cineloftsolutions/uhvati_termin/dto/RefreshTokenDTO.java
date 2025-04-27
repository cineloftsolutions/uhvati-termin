package com.cineloftsolutions.uhvati_termin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Zahtev za osvežavanje tokena")
public class RefreshTokenDTO {
    @NotBlank(message = "Refresh token je obavezan")
    @Size(min = 20, message = "Neispravan format refresh tokena")
    @Schema(description = "Refresh token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String refreshToken;
}
