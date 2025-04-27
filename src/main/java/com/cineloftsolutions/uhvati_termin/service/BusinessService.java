package com.cineloftsolutions.uhvati_termin.service;

import com.cineloftsolutions.uhvati_termin.dto.CreateBusinessDTO;
import org.springframework.http.ResponseEntity;

public interface BusinessService {

    ResponseEntity<?> createBusiness(CreateBusinessDTO businessDTO);
}
