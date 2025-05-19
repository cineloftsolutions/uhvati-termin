package com.cineloftsolutions.uhvati_termin.controller;

import com.cineloftsolutions.uhvati_termin.dto.*;
import com.cineloftsolutions.uhvati_termin.service.JwtTokenService;
import com.cineloftsolutions.uhvati_termin.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Korisnici", description = "API za upravljanje korisnicima")
public class UserController {
    private final UserService userService;
    private final JwtTokenService jwtTokenService;

    @Autowired
    public UserController(UserService userService, JwtTokenService jwtTokenService) {
        this.userService = userService;
        this.jwtTokenService = jwtTokenService;
    }

    @GetMapping("/profile")
    @Operation(
            summary = "Dohvatanje korisničkog profila",
            description = "Vraća trenutne podatke o korisničkom profilu"
    )
    public ResponseEntity<?> getUserProfile(@RequestHeader("Authorization") String authorizationHeader) {
        Long userId = jwtTokenService.extractUserId(authorizationHeader);
        return userService.getUserProfile(userId);
    }

/*    @PostMapping("/update-profile")
    @Operation(
            summary = "Ažuriranje korisničkog profila",
            description = "Omogućava korisniku da izmeni svoje podatke"
    )
    public ResponseEntity<?> updateUserProfile(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody UpdateUserProfileDTO updateUserProfileDTO) {
        return userService.updateUserProfile(jwtTokenService.extractUserId(authorizationHeader), updateUserProfileDTO);
    }*/

    @GetMapping("/admin/employee/{locationId}")
    @Operation(
            summary = "Dohvatanje svih zaposlenih za lokaciju",
            description = "Vraća listu svih zaposlenih za datu lokaciju"
    )
    public ResponseEntity<?> getEmployeesByLocation(
            @PathVariable Long locationId) {
        return userService.getEmployeesByLocation(locationId);
    }

    @PostMapping("/admin/employee/create")
    @Operation(
            summary = "Kreiranje novog zaposlenog",
            description = "Kreira novog korisnika sa rolom EMPLOYEE i dodeljuje ga određenoj lokaciji"
    )
    public ResponseEntity<?> createEmployee(
            @RequestBody @Valid CreateEmployeeDTO createEmployeeDTO) {
        return userService.createEmployee(createEmployeeDTO);
    }

    @PutMapping("/admin/employee/update/{id}")
    @Operation(
            summary = "Izmena podataka o zaposlenom",
            description = "Omogućava adminu da izmeni podatke o zaposlenom"
    )
    public ResponseEntity<?> updateEmployee(
            @PathVariable Long id,
            @RequestBody @Valid UpdateEmployeeDTO updateEmployeeDTO) {
        return userService.updateEmployee(id, updateEmployeeDTO);
    }

    @PostMapping("/admin/create")
    @Operation(
            summary = "Registracija novog admina biznisa",
            description = "Kreira novog korisnika sa rolom ADMIN i dodeljuje ga biznisu"
    )
    public ResponseEntity<?> createAdmin(@RequestBody @Valid RegisterRequestDTO request) {
        return userService.createAdmin(request);
    }

}

