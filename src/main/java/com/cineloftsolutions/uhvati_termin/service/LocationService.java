package com.cineloftsolutions.uhvati_termin.service;

import com.cineloftsolutions.uhvati_termin.dto.CreateLocationDTO;
import com.cineloftsolutions.uhvati_termin.dto.ReadLocationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LocationService {
    @Transactional(readOnly = true)
    ResponseEntity<?> getLocation(Long id);
    @Transactional(readOnly = true)
    ResponseEntity<?> getLocations(Long businessId);
    ResponseEntity<?> createLocation(Long businessId, CreateLocationDTO locationDTO);
    ResponseEntity<?> updateLocation(Long id, CreateLocationDTO locationDTO);
}
