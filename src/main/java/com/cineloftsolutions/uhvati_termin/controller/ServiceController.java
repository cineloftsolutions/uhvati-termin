package com.cineloftsolutions.uhvati_termin.controller;

import com.cineloftsolutions.uhvati_termin.dto.AssignEmployeesToServiceDTO;
import com.cineloftsolutions.uhvati_termin.dto.AssignLocationsToServiceDTO;
import com.cineloftsolutions.uhvati_termin.dto.CreateServiceDTO;
import com.cineloftsolutions.uhvati_termin.service.JwtTokenService;
import com.cineloftsolutions.uhvati_termin.service.ServiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/services")
@Tag(name = "Usluge", description = "API za upravljanje uslugama")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceService serviceService;

    private final JwtTokenService jwtTokenService;

    @PostMapping("/admin/create")
    @Operation(
            summary = "Kreiranje nove usluge",
            description = "Kreira novu uslugu sa osnovnim podacima. Lokacije i zaposleni se mogu naknadno povezati."
    )
    public ResponseEntity<?> createService(@RequestHeader("Authorization") String authorizationHeader, @RequestBody @Valid CreateServiceDTO dto) {
        return serviceService.createService(jwtTokenService.extractBusinessId(authorizationHeader), dto);
    }

    @PutMapping("/admin/{id}")
    @Operation(
            summary = "Ažuriranje postojeće usluge",
            description = "Ažurira osnovne podatke postojeće usluge. Lokacije i zaposleni se ne ažuriraju ovom metodom."
    )
    public ResponseEntity<?> updateService(@PathVariable Long id, @RequestBody @Valid CreateServiceDTO dto) {
        return serviceService.updateService(id, dto);
    }

    @PostMapping("/admin/{id}/locations")
    @Operation(
            summary = "Povezivanje lokacija sa uslugom",
            description = "Povezuje postojeću uslugu sa jednom ili više lokacija."
    )
    public ResponseEntity<?> assignLocations(@PathVariable Long id, @RequestBody @Valid AssignLocationsToServiceDTO dto) {
        return serviceService.assignLocations(id, dto);
    }

    @PostMapping("/admin/{id}/employees")
    @Operation(
            summary = "Povezivanje zaposlenih sa uslugom",
            description = "Povezuje postojeću uslugu sa jednim ili više zaposlenih, uz definisanje trajanja usluge za svakog zaposlenog."
    )
    public ResponseEntity<?> assignEmployees(@PathVariable Long id, @RequestBody @Valid AssignEmployeesToServiceDTO dto) {
        return serviceService.assignEmployees(id, dto);
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(
            summary = "Dohvatanje usluga koje izvodi zaposleni",
            description = "Vraća sve usluge koje određeni zaposleni izvodi."
    )
    public ResponseEntity<?> getServicesByEmployee(@PathVariable Long employeeId) {
        return serviceService.getServicesByEmployee(employeeId);
    }
}
