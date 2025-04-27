package com.cineloftsolutions.uhvati_termin.controller;

import com.cineloftsolutions.uhvati_termin.dto.CreateBusinessDTO;
import com.cineloftsolutions.uhvati_termin.service.BusinessService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/businesses")
@Tag(name = "Biznisi", description = "API za upravljanje biznisima")
public class BusinessController {
    private final BusinessService businessService;

    @Autowired
    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @PostMapping("/admin/create")
    @Operation(
            summary = "Kreiranje novog biznisa",
            description = "Kreira novi biznis"
    )
    public ResponseEntity<?> createBusiness(@Valid @RequestBody CreateBusinessDTO businessDTO) {
        return businessService.createBusiness(businessDTO);
    }
}
