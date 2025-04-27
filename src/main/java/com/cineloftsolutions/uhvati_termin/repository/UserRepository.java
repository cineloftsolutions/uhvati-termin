package com.cineloftsolutions.uhvati_termin.repository;

import com.cineloftsolutions.uhvati_termin.entity.Location;
import com.cineloftsolutions.uhvati_termin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndBusiness_BusinessId(String email, Long businessId);
    Optional<User> findByEmailAndLocation(String email, Location location);
    List<User> findByLocation(Location location);
}
