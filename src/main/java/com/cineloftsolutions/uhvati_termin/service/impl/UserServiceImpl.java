package com.cineloftsolutions.uhvati_termin.service.impl;

import com.cineloftsolutions.uhvati_termin.dto.CreateEmployeeDTO;
import com.cineloftsolutions.uhvati_termin.dto.ReadUserDTO;
import com.cineloftsolutions.uhvati_termin.dto.UpdateEmployeeDTO;
import com.cineloftsolutions.uhvati_termin.dto.UpdateUserProfileDTO;
import com.cineloftsolutions.uhvati_termin.entity.Location;
import com.cineloftsolutions.uhvati_termin.entity.Role;
import com.cineloftsolutions.uhvati_termin.entity.User;
import com.cineloftsolutions.uhvati_termin.maper.UserMapper;
import com.cineloftsolutions.uhvati_termin.repository.LocationRepository;
import com.cineloftsolutions.uhvati_termin.repository.RoleRepository;
import com.cineloftsolutions.uhvati_termin.repository.UserRepository;
import com.cineloftsolutions.uhvati_termin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final LocationRepository locationRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, LocationRepository locationRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.locationRepository = locationRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<?> getUserProfile(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", 404,
                    "error", "Korisnik nije pronađen",
                    "message", "Ne postoji korisnik sa ID-em: " + id
            ));
        }
        User user = userOpt.get();

        ReadUserDTO userDTO = userMapper.toReadUserDTO(user);

        return ResponseEntity.ok(Map.of(
                "status", 200,
                "message", "Podaci korisnika",
                "data", userDTO
        ));
    }


    @Override
    public ResponseEntity<?> updateUserProfile(Long userId, UpdateUserProfileDTO updateUserProfileDTO) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", 404,
                    "error", "Korisnik nije pronađen",
                    "message", "Nema korisnika sa ovim ID-em"
            ));
        }

        User user = userOpt.get();
        user.setName(updateUserProfileDTO.getName());
        userRepository.save(user);

        return ResponseEntity.ok(Map.of(
                "status", 200,
                "message", "Korisnički profil je uspešno ažuriran",
                "data", Map.of("name", user.getName())
        ));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> getEmployeesByLocation(Long locationId) {
        Optional<Location> locationOptional = locationRepository.findById(locationId);
        if (locationOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", 404,
                    "error", "Lokacija nije pronađena",
                    "message", "Lokacija sa ID-em " + locationId + " nije pronađena"
            ));
        }

        Location location = locationOptional.get();
        List<User> users = userRepository.findByLocation(location);

        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", 404,
                    "error", "Nema zaposlenih",
                    "message", "Nema zaposlenih za lokaciju sa ID-em: " + locationId
            ));
        }

        List<ReadUserDTO> employeeDTOs = users.stream()
                .map(userMapper::toReadUserDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(Map.of(
                "status", 200,
                "message", "Lista zaposlenih za lokaciju",
                "data", employeeDTOs
        ));
    }


    @Override
    public ResponseEntity<?> createEmployee(CreateEmployeeDTO createEmployeeDTO) {
        Optional<Location> locationOptional = locationRepository.findById(createEmployeeDTO.getLocationId());
        if (locationOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", 404,
                    "error", "Lokacija nije pronađena",
                    "message", "Lokacija sa datim ID-em ne postoji"
            ));
        }

        Location location = locationOptional.get();

        if (userRepository.findByEmailAndLocation(createEmployeeDTO.getEmail(), location).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "status", 409,
                    "error", "Email već postoji",
                    "message", "Korisnik sa ovim email-om već postoji u ovoj lokaciji"
            ));
        }

        User user = new User();
        user.setName(createEmployeeDTO.getName());
        user.setEmail(createEmployeeDTO.getEmail());
        user.setPassword(passwordEncoder.encode(createEmployeeDTO.getPassword()));
        Role userRole = roleRepository.findByName("EMPLOYEE")
                .orElseGet(() -> roleRepository.save(new Role(null, "EMPLOYEE")));
        user.setRole(userRole);
        user.setLocation(location);
        user.setBusiness(location.getBusiness());

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "status", 201,
                "message", "Zaposleni je uspešno kreiran",
                "data", Map.of("name", user.getName(), "email", user.getEmail())
        ));
    }

    @Override
    public ResponseEntity<?> updateEmployee(Long employeeId, UpdateEmployeeDTO updateEmployeeDTO) {
        Optional<User> userOpt = userRepository.findById(employeeId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", 404,
                    "error", "Zaposleni nije pronađen",
                    "message", "Nema zaposlenog sa datim ID-em"
            ));
        }

        User user = userOpt.get();

        if (!user.getEmail().equals(updateEmployeeDTO.getEmail())) {
            Optional<User> existingUser = userRepository.findByEmail(updateEmployeeDTO.getEmail());
            if (existingUser.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                        "status", 409,
                        "error", "Email zauzet",
                        "message", "Korisnik sa ovim emailom već postoji"
                ));
            }
            user.setEmail(updateEmployeeDTO.getEmail());
        }

        user.setName(updateEmployeeDTO.getName());

        if (updateEmployeeDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(updateEmployeeDTO.getPassword()));
        }

        Optional<Location> locationOpt = locationRepository.findById(updateEmployeeDTO.getLocationId());
        if (locationOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", 404,
                    "error", "Lokacija nije pronađena",
                    "message", "Nema lokacije sa datim ID-em"
            ));
        }
        user.setLocation(locationOpt.get());

        userRepository.save(user);

        return ResponseEntity.ok(Map.of(
                "status", 200,
                "message", "Zaposleni je uspešno ažuriran",
                "data", userMapper.toReadUserDTO(user)
        ));
    }




}

