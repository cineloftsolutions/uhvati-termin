package com.cineloftsolutions.uhvati_termin.repository;

import com.cineloftsolutions.uhvati_termin.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
