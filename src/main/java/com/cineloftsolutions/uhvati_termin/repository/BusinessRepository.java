package com.cineloftsolutions.uhvati_termin.repository;

import com.cineloftsolutions.uhvati_termin.entity.Business;
import org.hibernate.usertype.BaseUserTypeSupport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusinessRepository extends JpaRepository<Business, Long> {
    Optional<Business> findByBusinessId(Long businessId);
    boolean existsByName(String name);
}
