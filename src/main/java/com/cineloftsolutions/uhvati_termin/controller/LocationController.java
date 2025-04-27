package com.cineloftsolutions.uhvati_termin.controller;

import com.cineloftsolutions.uhvati_termin.dto.CreateLocationDTO;
import com.cineloftsolutions.uhvati_termin.dto.ReadLocationDTO;
import com.cineloftsolutions.uhvati_termin.service.JwtTokenService;
import com.cineloftsolutions.uhvati_termin.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/locations")
@Tag(name = "Lokacije", description = "API za upravljanje lokacijama")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;
    private final JwtTokenService jwtTokenService;

    @GetMapping
    @Operation(
            summary = "Dohvatanje svih lokacija za biznis",
            description = "Vraća listu svih lokacija koje pripadaju određenom biznisu"
    )
    public ResponseEntity<?> getLocations(@RequestHeader("Authorization") String authorizationHeader) {
        return locationService.getLocations(jwtTokenService.extractBusinessId(authorizationHeader));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Dohvatanje detalja jedne lokacije",
            description = "Vraća detalje jedne lokacije za određeni biznis"
    )
    public ResponseEntity<?> getLocation(
            @PathVariable Long id) {
        return locationService.getLocation(id);
    }

    @PostMapping("/admin/create")
    @Operation(
            summary = "Kreiranje nove lokacije",
            description = "Kreira novu lokaciju za određeni biznis"
    )
    public ResponseEntity<?> createLocation(
            @RequestHeader("Authorization") String authorizationHeader,
            @Valid @RequestBody CreateLocationDTO locationDTO) {
        return locationService.createLocation(jwtTokenService.extractBusinessId(authorizationHeader), locationDTO);
    }

    @PutMapping("/admin/update/{id}")
    @Operation(
            summary = "Ažuriranje lokacije",
            description = "Ažurira detalje određene lokacije za određeni biznis"
    )
    public ResponseEntity<?> updateLocation(
            @PathVariable Long id,
            @Valid @RequestBody CreateLocationDTO locationDTO) {
        return locationService.updateLocation(id, locationDTO);
    }
}
