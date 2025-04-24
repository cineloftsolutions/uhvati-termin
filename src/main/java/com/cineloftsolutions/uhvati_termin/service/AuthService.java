package com.cineloftsolutions.uhvati_termin.service;

import com.cineloftsolutions.uhvati_termin.dto.AuthRequestDTO;
import com.cineloftsolutions.uhvati_termin.dto.RefreshTokenDTO;
import com.cineloftsolutions.uhvati_termin.dto.RegisterRequestDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> register(RegisterRequestDTO request);
    ResponseEntity<?> login(AuthRequestDTO request);
    ResponseEntity<?> logout(RefreshTokenDTO request);
    ResponseEntity<?> refreshToken(RefreshTokenDTO request);
}
