package com.cineloftsolutions.uhvati_termin.service.impl;

import com.cineloftsolutions.uhvati_termin.dto.CreateLocationDTO;
import com.cineloftsolutions.uhvati_termin.dto.ReadLocationDTO;
import com.cineloftsolutions.uhvati_termin.entity.Business;
import com.cineloftsolutions.uhvati_termin.entity.Location;
import com.cineloftsolutions.uhvati_termin.maper.LocationMapper;
import com.cineloftsolutions.uhvati_termin.repository.LocationRepository;
import com.cineloftsolutions.uhvati_termin.repository.BusinessRepository;
import com.cineloftsolutions.uhvati_termin.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    private final BusinessRepository businessRepository;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository, LocationMapper locationMapper, BusinessRepository businessRepository) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
        this.businessRepository = businessRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> getLocation(Long id) {
        Optional<Location> locationOpt = locationRepository.findById(id);
        if (locationOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", 404,
                    "error", "Lokacija nije pronađena",
                    "message", "Ne postoji lokacija sa ID-em: " + id
            ));
        }
        Location location = locationOpt.get();

        ReadLocationDTO locationDTO = locationMapper.toReadDTO(location);

        return ResponseEntity.ok(Map.of(
                "status", 200,
                "message", "Detalji lokacije",
                "data", locationDTO
        ));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> getLocations(Long businessId) {
        Optional<Business> businessOptional = businessRepository.findByBusinessId(businessId);
        if (businessOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", 404,
                    "error", "Biznis nije pronađen",
                    "message", "Ne postoji biznis sa ID-em: " + businessId
            ));
        }

        Business business = businessOptional.get();
        List<Location> locations = locationRepository.findByBusiness(business);
        if (locations.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", 404,
                    "error", "Nema lokacija",
                    "message", "Nema lokacija za biznis sa ID-em: " + businessId
            ));
        }

        List<ReadLocationDTO> locationDTOs = locations.stream()
                .map(locationMapper::toReadDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(Map.of(
                "status", 200,
                "message", "Lista lokacija",
                "data", locationDTOs
        ));
    }
    @Override
    public ResponseEntity<?> createLocation(Long businessId, CreateLocationDTO locationDTO) {
        Optional<Business> businessOptional = businessRepository.findByBusinessId(businessId);
        if (businessOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", 404,
                    "error", "Biznis nije pronađen",
                    "message", "Ne postoji biznis sa ID-em: " + businessId
            ));
        }
        Business business = businessOptional.get();

        if (locationRepository.existsByBusinessAndAddress(business, locationDTO.getAddress())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "status", 409,
                    "error", "Lokacija već postoji",
                    "message", "Lokacija sa ovom adresom već postoji za ovaj biznis."
            ));
        }

        Location location = locationMapper.toEntity(locationDTO);
        location.setBusiness(business);

        location = locationRepository.save(location);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "status", 201,
                "message", "Lokacija uspešno kreirana.",
                "data", locationMapper.toReadDTO(location)
        ));
    }

    @Override
    public ResponseEntity<?> updateLocation(Long id, CreateLocationDTO locationDTO) {
        Optional<Location> locationOpt = locationRepository.findById(id);
        if (locationOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", 404,
                    "error", "Lokacija nije pronađena",
                    "message", "Ne postoji lokacija sa ID-em: " + id
            ));
        }
        Location location = locationOpt.get();

        Optional<Location> existingLocationOpt = locationRepository.findByBusinessAndAddress(location.getBusiness(), locationDTO.getAddress());

        if (existingLocationOpt.isPresent() && !existingLocationOpt.get().getId().equals(location.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "status", 409,
                    "error", "Lokacija već postoji",
                    "message", "Lokacija sa ovom adresom već postoji za ovaj biznis."
            ));
        }

        location.setName(locationDTO.getName());
        location.setAddress(locationDTO.getAddress());
        location.setCity(locationDTO.getCity());

        location = locationRepository.save(location);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "status", 200,
                "message", "Lokacija uspešno ažurirana.",
                "data", locationMapper.toReadDTO(location)
        ));
    }


}
