package com.cineloftsolutions.uhvati_termin.repository;

import com.cineloftsolutions.uhvati_termin.entity.Location;
import com.cineloftsolutions.uhvati_termin.entity.LocationService;
import com.cineloftsolutions.uhvati_termin.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationServiceRepository extends JpaRepository<LocationService, Long> {
    void deleteByService(Service service);
    boolean existsByServiceAndLocation(Service service, Location location);
}
