package com.cineloftsolutions.uhvati_termin.controller;

import com.cineloftsolutions.uhvati_termin.dto.UserDTO;
import com.cineloftsolutions.uhvati_termin.entity.User;
import com.cineloftsolutions.uhvati_termin.repository.UserRepository;
import com.cineloftsolutions.uhvati_termin.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Korisnici", description = "API za upravljanje korisnicima")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(
            summary = "Dohvatanje svih korisnika",
            description = "Vraća listu svih registrovanih korisnika"
    )
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
