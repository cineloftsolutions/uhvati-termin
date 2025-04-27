package com.cineloftsolutions.uhvati_termin.service;

import com.cineloftsolutions.uhvati_termin.dto.AssignEmployeesToServiceDTO;
import com.cineloftsolutions.uhvati_termin.dto.AssignLocationsToServiceDTO;
import com.cineloftsolutions.uhvati_termin.dto.CreateServiceDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

public interface ServiceService {

    ResponseEntity<?> createService(Long businessId, CreateServiceDTO createServiceDTO);

    ResponseEntity<?> updateService(Long serviceId, CreateServiceDTO createServiceDTO);

    @Transactional(readOnly = true)
    ResponseEntity<?> getServicesByEmployee(Long employeeId);

    ResponseEntity<?> assignLocations(Long serviceId, AssignLocationsToServiceDTO dto);

    ResponseEntity<?> assignEmployees(Long serviceId, AssignEmployeesToServiceDTO dto);
}
