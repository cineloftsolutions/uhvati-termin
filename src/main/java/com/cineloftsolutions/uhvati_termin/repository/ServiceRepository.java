package com.cineloftsolutions.uhvati_termin.repository;

import com.cineloftsolutions.uhvati_termin.entity.Business;
import com.cineloftsolutions.uhvati_termin.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    @Query("SELECT DISTINCT s FROM Service s " +
            "LEFT JOIN FETCH s.locationServices ls " +
            "LEFT JOIN FETCH s.employeeServices es " +
            "WHERE s.id IN :ids")
    List<Service> findAllByIdsWithDetails(@Param("ids") List<Long> ids);
    boolean existsByNameAndBusiness(String name, Business business);
}
