package com.cineloftsolutions.uhvati_termin.service.impl;

import com.cineloftsolutions.uhvati_termin.dto.AuthRequestDTO;
import com.cineloftsolutions.uhvati_termin.dto.RefreshTokenDTO;
import com.cineloftsolutions.uhvati_termin.dto.RegisterRequestDTO;
import com.cineloftsolutions.uhvati_termin.entity.Role;
import com.cineloftsolutions.uhvati_termin.entity.User;
import com.cineloftsolutions.uhvati_termin.maper.UserMapper;
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

    @Autowired
    public AuthServiceImpl(JwtTokenService jwtTokenService, UserRepository userRepository,
                           RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.jwtTokenService = jwtTokenService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public ResponseEntity<?> register(RegisterRequestDTO request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "status", 409,
                    "error", "Email je već zauzet",
                    "message", "Korisnik sa ovom email adresom već postoji"
            ));
        }

        Role userRole = roleRepository.findByName("USER")
                .orElseGet(() -> roleRepository.save(new Role(null, "USER")));

        User user = userMapper.fromRegisterRequest(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(userRole);

        userRepository.save(user);

        String accessToken = jwtTokenService.generateAccessToken(user.getEmail(), userRole.getName());
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
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isEmpty() || !passwordEncoder.matches(request.getPassword(), userOpt.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "status", 401,
                    "error", "Neispravni podaci",
                    "message", "Email ili lozinka nisu ispravni"
            ));
        }

        User user = userOpt.get();
        String accessToken = jwtTokenService.generateAccessToken(user.getEmail(), user.getRole().getName());
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
            String role = userRepository.findByEmail(email).map(u -> u.getRole().getName()).orElse(null);

            if (role == null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                        "status", 403,
                        "error", "Nepostojeći korisnik",
                        "message", "Ne postoji korisnik povezan sa datim tokenom"
                ));
            }

            String newAccessToken = jwtTokenService.generateAccessToken(email, role);
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

