package com.cineloftsolutions.uhvati_termin.repository;

import com.cineloftsolutions.uhvati_termin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
