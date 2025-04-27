package com.cineloftsolutions.uhvati_termin.repository;

import com.cineloftsolutions.uhvati_termin.entity.Business;
import com.cineloftsolutions.uhvati_termin.entity.Location;
import com.cineloftsolutions.uhvati_termin.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByBusiness(Business business);
    boolean existsByBusinessAndAddress(Business business, String address);
    Optional<Location> findByBusinessAndAddress(Business business, String address);
}
