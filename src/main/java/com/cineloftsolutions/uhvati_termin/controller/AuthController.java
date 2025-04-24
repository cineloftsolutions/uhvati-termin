package com.cineloftsolutions.uhvati_termin.controller;

import com.cineloftsolutions.uhvati_termin.dto.AuthRequestDTO;
import com.cineloftsolutions.uhvati_termin.dto.RefreshTokenDTO;
import com.cineloftsolutions.uhvati_termin.dto.RegisterRequestDTO;
import com.cineloftsolutions.uhvati_termin.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autentikacija", description = "API za registraciju, prijavu i osvežavanje tokena")
@Validated
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(
            summary = "Registracija novog korisnika",
            description = "Kreira novog korisnika i vraća JWT tokene (access i refresh token)"
    )
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequestDTO request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    @Operation(
            summary = "Prijava korisnika",
            description = "Autentifikuje korisnika i vraća JWT tokene (access i refresh token)"
    )
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequestDTO request) {
        return authService.login(request);
    }

    @PostMapping("/logout")
    @Operation(
            summary = "Odjava korisnika",
            description = "Odjavljuje korisnika sa klijentske strane (brisanje refresh tokena na frontend-u)"
    )
    public ResponseEntity<?> logout(@Valid @RequestBody RefreshTokenDTO request) {
        return authService.logout(request);
    }

    @PostMapping("/refresh")
    @Operation(
            summary = "Osvežavanje JWT tokena",
            description = "Vraća novi access token na osnovu važećeg refresh tokena"
    )
    public ResponseEntity<?> refresh(@Valid @RequestBody RefreshTokenDTO request) {
        return authService.refreshToken(request);
    }
}

