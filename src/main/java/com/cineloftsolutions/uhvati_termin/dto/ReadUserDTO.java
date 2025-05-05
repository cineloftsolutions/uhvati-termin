package com.cineloftsolutions.uhvati_termin.dto;

import lombok.Data;

@Data
public class ReadUserDTO {
    private Long id;
    private String name;
    private String surname;
    private String phone;
    private String email;
    private String role;
    private Long businessId;
}
