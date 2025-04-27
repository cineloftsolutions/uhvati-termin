package com.cineloftsolutions.uhvati_termin.service;

import com.cineloftsolutions.uhvati_termin.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {
    ResponseEntity<?> getUserProfile(Long id);
    ResponseEntity<?> updateUserProfile(Long userId, UpdateUserProfileDTO updateUserProfileDTO);

    @Transactional(readOnly = true)
    ResponseEntity<?> getEmployeesByLocation(Long locationId);

    ResponseEntity<?> createEmployee(CreateEmployeeDTO createEmployeeDTO);
    ResponseEntity<?> updateEmployee(Long employeeId, UpdateEmployeeDTO createEmployeeDTO);
}
