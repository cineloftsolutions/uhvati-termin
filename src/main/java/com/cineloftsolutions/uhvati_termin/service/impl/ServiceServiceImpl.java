package com.cineloftsolutions.uhvati_termin.service.impl;

import com.cineloftsolutions.uhvati_termin.dto.*;
import com.cineloftsolutions.uhvati_termin.entity.*;
import com.cineloftsolutions.uhvati_termin.maper.LocationMapper;
import com.cineloftsolutions.uhvati_termin.maper.ServiceMapper;
import com.cineloftsolutions.uhvati_termin.maper.UserMapper;
import com.cineloftsolutions.uhvati_termin.repository.*;
import com.cineloftsolutions.uhvati_termin.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;
    private final LocationRepository locationRepository;
    private final LocationServiceRepository locationServiceRepository;
    private final UserRepository userRepository;
    private final EmployeeServiceRepository employeeServiceRepository;
    private final BusinessRepository businessRepository;
    private final ServiceMapper serviceMapper;
    private final LocationMapper locationMapper;
    private final UserMapper userMapper;

    @Override
    public ResponseEntity<?> createService(Long businessId, CreateServiceDTO dto) {
        Optional<Business> businessOpt = businessRepository.findByBusinessId(businessId);
        if (businessOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", 404,
                    "error", "Biznis nije pronađen",
                    "message", "Ne postoji biznis sa ID-em: " + businessId
            ));
        }
        Business business = businessOpt.get();

        if (serviceRepository.existsByNameAndBusiness(dto.getName(), business)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "status", 409,
                    "error", "Usluga već postoji",
                    "message", "Usluga sa ovim imenom već postoji za vaš biznis."
            ));
        }

        com.cineloftsolutions.uhvati_termin.entity.Service service = serviceMapper.toEntity(dto);
        service.setBusiness(business);
        service = serviceRepository.save(service);

        if (dto.getLocationIds() != null && !dto.getLocationIds().isEmpty()) {
            for (Long locationId : dto.getLocationIds()) {
                Optional<Location> locationOpt = locationRepository.findById(locationId);
                if (locationOpt.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                            "status", 404,
                            "error", "Lokacija nije pronađena",
                            "message", "Ne postoji lokacija sa ID-em: " + locationId
                    ));
                }
                Location location = locationOpt.get();

                LocationService locationService = new LocationService();
                locationService.setLocation(location);
                locationService.setService(service);
                locationServiceRepository.save(locationService);
            }
        }

        if (dto.getEmployeeIds() != null && !dto.getEmployeeIds().isEmpty()) {
            for (Long employeeId : dto.getEmployeeIds()) {
                Optional<User> employeeOpt = userRepository.findById(employeeId);
                if (employeeOpt.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                            "status", 404,
                            "error", "Zaposleni nije pronađen",
                            "message", "Ne postoji zaposleni sa ID-em: " + employeeId
                    ));
                }
                User employee = employeeOpt.get();

                EmployeeService employeeService = new EmployeeService();
                employeeService.setService(service);
                employeeService.setUser(employee);
                employeeService.setDurationMinutes(dto.getDurationMinutes());
                employeeServiceRepository.save(employeeService);
            }
        }

        return buildReadServiceResponse(service, HttpStatus.CREATED, "Usluga uspešno kreirana.");
    }



    @Override
    public ResponseEntity<?> updateService(Long serviceId, CreateServiceDTO dto) {
        Optional<com.cineloftsolutions.uhvati_termin.entity.Service> serviceOpt = serviceRepository.findById(serviceId);
        if (serviceOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", 404,
                    "error", "Usluga nije pronađena",
                    "message", "Ne postoji usluga sa ID-em: " + serviceId
            ));
        }
        com.cineloftsolutions.uhvati_termin.entity.Service service = serviceOpt.get();

        service.setName(dto.getName());
        service.setDescription(dto.getDescription());
        service.setPrice(dto.getPrice());
        service.setDurationMinutes(dto.getDurationMinutes());
        service = serviceRepository.save(service);

        if (dto.getLocationIds() != null && !dto.getLocationIds().isEmpty()) {
            locationServiceRepository.deleteByService(service);

            for (Long locationId : dto.getLocationIds()) {
                Optional<Location> locationOpt = locationRepository.findById(locationId);
                if (locationOpt.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                            "status", 404,
                            "error", "Lokacija nije pronađena",
                            "message", "Ne postoji lokacija sa ID-em: " + locationId
                    ));
                }
                Location location = locationOpt.get();

                LocationService locationService = new LocationService();
                locationService.setLocation(location);
                locationService.setService(service);
                locationServiceRepository.save(locationService);
            }
        }

        if (dto.getEmployeeIds() != null && !dto.getEmployeeIds().isEmpty()) {
            employeeServiceRepository.deleteByService(service);

            for (Long employeeId : dto.getEmployeeIds()) {
                Optional<User> employeeOpt = userRepository.findById(employeeId);
                if (employeeOpt.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                            "status", 404,
                            "error", "Zaposleni nije pronađen",
                            "message", "Ne postoji zaposleni sa ID-em: " + employeeId
                    ));
                }
                User employee = employeeOpt.get();

                EmployeeService employeeService = new EmployeeService();
                employeeService.setService(service);
                employeeService.setUser(employee);
                employeeService.setDurationMinutes(dto.getDurationMinutes());
                employeeServiceRepository.save(employeeService);
            }
        }

        return buildReadServiceResponse(service, HttpStatus.OK, "Usluga uspešno ažurirana.");
    }



    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> getServicesByEmployee(Long employeeId) {
        Optional<User> employeeOpt = userRepository.findById(employeeId);
        if (employeeOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", 404,
                    "error", "Zaposleni nije pronađen",
                    "message", "Ne postoji zaposleni sa ID-em: " + employeeId
            ));
        }

        List<EmployeeService> employeeServices = employeeServiceRepository.findByUserId(employeeId);
        List<Long> serviceIds = employeeServices.stream()
                .map(es -> es.getService().getId())
                .toList();

        if (serviceIds.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", 404,
                    "error", "Usluge nisu pronađene",
                    "message", "Zaposleni nema dodeljene usluge"
            ));
        }

        List<com.cineloftsolutions.uhvati_termin.entity.Service> services = serviceRepository.findAllByIdsWithDetails(serviceIds);

        List<ReadServiceDTO> serviceDtos = services.stream()
                .map(service -> {
                    List<ReadLocationDTO> locations = Optional.ofNullable(service.getLocationServices())
                            .orElse(Collections.emptySet())
                            .stream()
                            .map(ls -> locationMapper.toReadDTO(ls.getLocation()))
                            .collect(Collectors.toList());

                    List<ReadUserDTO> employees = Optional.ofNullable(service.getEmployeeServices())
                            .orElse(Collections.emptySet())
                            .stream()
                            .map(es -> userMapper.toReadUserDTO(es.getUser()))
                            .collect(Collectors.toList());

                    ReadServiceDTO response = serviceMapper.toDTO(service);
                    response.setLocations(locations);
                    response.setEmployees(employees);

                    return response;
                })
                .toList();

        return ResponseEntity.ok(Map.of(
                "status", 200,
                "message", "Lista usluga za zaposlenog",
                "data", serviceDtos
        ));
    }



    private ResponseEntity<?> buildReadServiceResponse(com.cineloftsolutions.uhvati_termin.entity.Service service, HttpStatus status, String message) {
        List<ReadLocationDTO> locations = Optional.ofNullable(service.getLocationServices())
                .orElse(Collections.emptySet())
                .stream()
                .map(ls -> locationMapper.toReadDTO(ls.getLocation()))
                .collect(Collectors.toList());

        List<ReadUserDTO> employees = Optional.ofNullable(service.getEmployeeServices())
                .orElse(Collections.emptySet())
                .stream()
                .map(es -> userMapper.toReadUserDTO(es.getUser()))
                .collect(Collectors.toList());

        ReadServiceDTO response = serviceMapper.toDTO(service);
        response.setLocations(locations);
        response.setEmployees(employees);

        return ResponseEntity.status(status).body(Map.of(
                "status", status.value(),
                "message", message,
                "data", response
        ));
    }


    @Override
    public ResponseEntity<?> assignLocations(Long serviceId, AssignLocationsToServiceDTO dto) {
        Optional<com.cineloftsolutions.uhvati_termin.entity.Service> serviceOpt = serviceRepository.findById(serviceId);
        if (serviceOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", 404,
                    "error", "Usluga nije pronađena",
                    "message", "Ne postoji usluga sa ID-em: " + serviceId
            ));
        }
        com.cineloftsolutions.uhvati_termin.entity.Service service = serviceOpt.get();

        for (Long locationId : dto.getLocationIds()) {
            Optional<Location> locationOpt = locationRepository.findById(locationId);
            if (locationOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                        "status", 404,
                        "error", "Lokacija nije pronađena",
                        "message", "Ne postoji lokacija sa ID-em: " + locationId
                ));
            }
            Location location = locationOpt.get();

            boolean alreadyLinked = locationServiceRepository.existsByServiceAndLocation(service, location);

            if (!alreadyLinked) {
                LocationService locationService = new LocationService();
                locationService.setService(service);
                locationService.setLocation(location);
                locationServiceRepository.save(locationService);
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "status", 200,
                "message", "Lokacije su uspešno povezane sa uslugom."
        ));
    }



    @Override
    public ResponseEntity<?> assignEmployees(Long serviceId, AssignEmployeesToServiceDTO dto) {
        Optional<com.cineloftsolutions.uhvati_termin.entity.Service> serviceOpt = serviceRepository.findById(serviceId);
        if (serviceOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", 404,
                    "error", "Usluga nije pronađena",
                    "message", "Ne postoji usluga sa ID-em: " + serviceId
            ));
        }
        com.cineloftsolutions.uhvati_termin.entity.Service service = serviceOpt.get();

        for (AssignEmployeesToServiceDTO.EmployeeMapping mapping : dto.getEmployeeMappings()) {
            Optional<User> employeeOpt = userRepository.findById(mapping.getEmployeeId());
            if (employeeOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                        "status", 404,
                        "error", "Zaposleni nije pronađen",
                        "message", "Ne postoji zaposleni sa ID-em: " + mapping.getEmployeeId()
                ));
            }
            User employee = employeeOpt.get();

            boolean alreadyLinked = employeeServiceRepository.existsByServiceAndUser(service, employee);

            if (!alreadyLinked) {
                EmployeeService employeeService = new EmployeeService();
                employeeService.setService(service);
                employeeService.setUser(employee);
                employeeService.setDurationMinutes(mapping.getDurationMinutes());
                employeeServiceRepository.save(employeeService);
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "status", 200,
                "message", "Zaposleni su uspešno povezani sa uslugom."
        ));
    }


}
