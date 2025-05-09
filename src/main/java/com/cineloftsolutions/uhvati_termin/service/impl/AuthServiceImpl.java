package com.cineloftsolutions.uhvati_termin.service.impl;

import com.cineloftsolutions.uhvati_termin.dto.AuthRequestDTO;
import com.cineloftsolutions.uhvati_termin.dto.RefreshTokenDTO;
import com.cineloftsolutions.uhvati_termin.dto.RegisterRequestDTO;
import com.cineloftsolutions.uhvati_termin.entity.Business;
import com.cineloftsolutions.uhvati_termin.entity.Role;
import com.cineloftsolutions.uhvati_termin.entity.User;
import com.cineloftsolutions.uhvati_termin.maper.UserMapper;
import com.cineloftsolutions.uhvati_termin.repository.BusinessRepository;
import com.cineloftsolutions.uhvati_termin.repository.RoleRepository;
import com.cineloftsolutions.uhvati_termin.repository.UserRepository;
import com.cineloftsolutions.uhvati_termin.service.AuthService;
import com.cineloftsolutions.uhvati_termin.service.JwtTokenService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    private final BusinessRepository businessRepository;

    @Autowired
    public AuthServiceImpl(JwtTokenService jwtTokenService, UserRepository userRepository,
                           RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserMapper userMapper, BusinessRepository businessRepository) {
        this.jwtTokenService = jwtTokenService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.businessRepository = businessRepository;
    }

    @Override
    public ResponseEntity<?> register(RegisterRequestDTO request) {
        if (userRepository.findByEmailAndBusiness_BusinessId(request.getEmail(), request.getBusinessId()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "status", 409,
                    "error", "Email je već zauzet.",
                    "message", "Korisnik sa ovom email adresom već postoji."
            ));
        }

        Optional<Business> businessOptional = businessRepository.findByBusinessId(request.getBusinessId());
        if (businessOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", 404,
                    "error", "Biznis nije pronađen.",
                    "message", "Biznis sa datim ID-em ne postoji."
            ));
        }

        Business business = businessOptional.get();

        Role userRole = roleRepository.findByName("USER")
                .orElseGet(() -> roleRepository.save(new Role(null, "USER")));

        User user = userMapper.fromRegisterRequest(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(userRole);
        user.setBusiness(business);

        userRepository.saveAndFlush(user);

        String accessToken = jwtTokenService.generateAccessToken(user.getEmail(), userRole.getName(), user.getId(), business.getBusinessId());
        String refreshToken = jwtTokenService.generateRefreshToken(user.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "status", 201,
                "message", "Uspešna registracija",
                "accessToken", accessToken,
                "refreshToken", refreshToken
        ));
    }


    @Override
    public ResponseEntity<?> login(AuthRequestDTO request) {
        Optional<User> userOpt = userRepository.findByEmailAndBusiness_BusinessId(request.getEmail(), request.getBusinessId());

        if (userOpt.isEmpty() || !passwordEncoder.matches(request.getPassword(), userOpt.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "status", 401,
                    "error", "Neispravni podaci",
                    "message", "Email, lozinka ili business ID nisu ispravni"
            ));
        }

        User user = userOpt.get();
        String accessToken = jwtTokenService.generateAccessToken(user.getEmail(), user.getRole().getName(), user.getId(), user.getBusiness().getBusinessId());
        String refreshToken = jwtTokenService.generateRefreshToken(user.getEmail());

        return ResponseEntity.ok(Map.of(
                "status", 200,
                "message", "Uspešna prijava",
                "accessToken", accessToken,
                "refreshToken", refreshToken
        ));
    }

    @Override
    public ResponseEntity<?> logout(RefreshTokenDTO request) {
        return ResponseEntity.ok(Map.of(
                "status", 200,
                "message", "Uspešna odjava"
        ));
    }

    @Override
    public ResponseEntity<?> refreshToken(RefreshTokenDTO request) {
        try {
            if (!jwtTokenService.isRefreshToken(request.getRefreshToken())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                        "status", 403,
                        "error", "Neispravan tip tokena",
                        "message", "Prosleđeni token nije osvežavajući (refresh) token"
                ));
            }

            String email = jwtTokenService.extractEmail(request.getRefreshToken());
            User user = userRepository.findByEmail(email).orElse(null);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                        "status", 403,
                        "error", "Nepostojeći korisnik",
                        "message", "Ne postoji korisnik povezan sa datim tokenom"
                ));
            }

            Business business = user.getBusiness();

            if (business == null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                        "status", 403,
                        "error", "Nepostojeći biznis",
                        "message", "Korisnik nije povezan sa poslovnim entitetom"
                ));
            }

            String role = user.getRole().getName();
            String newAccessToken = jwtTokenService.generateAccessToken(email, role, user.getId(), business.getBusinessId());

            return ResponseEntity.ok(Map.of(
                    "status", 200,
                    "message", "Token uspešno osvežen",
                    "accessToken", newAccessToken,
                    "refreshToken", request.getRefreshToken()
            ));

        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "status", 403,
                    "error", "Token istekao",
                    "message", "Vaš osvežavajući token je istekao. Molimo prijavite se ponovo."
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "status", 403,
                    "error", "Neuspešno osvežavanje",
                    "message", "Greška tokom osvežavanja tokena: " + e.getMessage()
            ));
        }
    }
}

