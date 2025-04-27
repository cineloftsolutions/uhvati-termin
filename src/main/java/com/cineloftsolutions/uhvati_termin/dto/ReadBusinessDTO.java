package com.cineloftsolutions.uhvati_termin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Podaci o biznisu")
public class ReadBusinessDTO {

    private Long id;
    private Long businessId;
    private String name;
    private String phone;
    private String email;
    private String website;
    private String description;
    private String logoUrl;
}
