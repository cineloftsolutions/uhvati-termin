package com.cineloftsolutions.uhvati_termin.service.impl;

import com.cineloftsolutions.uhvati_termin.dto.CreateBusinessDTO;
import com.cineloftsolutions.uhvati_termin.entity.Business;
import com.cineloftsolutions.uhvati_termin.maper.BusinessMapper;
import com.cineloftsolutions.uhvati_termin.repository.BusinessRepository;
import com.cineloftsolutions.uhvati_termin.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BusinessServiceImpl implements BusinessService {
    private final BusinessRepository businessRepository;
    private final BusinessMapper businessMapper;

    @Autowired
    public BusinessServiceImpl(BusinessRepository businessRepository, BusinessMapper businessMapper) {
        this.businessRepository = businessRepository;
        this.businessMapper = businessMapper;
    }

    @Override
    public ResponseEntity<?> createBusiness(CreateBusinessDTO businessDTO) {
        if (businessRepository.existsByName(businessDTO.getName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "status", 409,
                    "error", "Biznis sa ovim imenom već postoji",
                    "message", "Biznis sa imenom " + businessDTO.getName() + " već postoji."
            ));
        }

        Business business = businessMapper.toEntity(businessDTO);
        business.setBusinessId(System.currentTimeMillis());
        
        business = businessRepository.save(business);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "status", 201,
                "message", "Biznis uspešno kreiran",
                "data", businessMapper.toDTO(business)
        ));
    }
}
