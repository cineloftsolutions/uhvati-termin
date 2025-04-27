package com.cineloftsolutions.uhvati_termin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO za čitanje podataka o lokaciji")
public class ReadLocationDTO {

    @Schema(description = "ID lokacije", example = "1")
    private Long id;

    @Schema(description = "Ime lokacije", example = "Studio A")
    private String name;

    @Schema(description = "Adresa lokacije", example = "Kralja Petra 5")
    private String address;

    @Schema(description = "Grad u kojem se nalazi lokacija", example = "Beograd")
    private String city;
}
