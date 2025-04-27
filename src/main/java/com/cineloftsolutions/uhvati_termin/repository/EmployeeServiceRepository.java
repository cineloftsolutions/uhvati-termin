package com.cineloftsolutions.uhvati_termin.repository;

import com.cineloftsolutions.uhvati_termin.entity.EmployeeService;
import com.cineloftsolutions.uhvati_termin.entity.Service;
import com.cineloftsolutions.uhvati_termin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeServiceRepository extends JpaRepository<EmployeeService, Long> {
    List<EmployeeService> findByUserId(Long userId);
    void deleteByService(Service service);
    boolean existsByServiceAndUser(Service service, User user);
}
