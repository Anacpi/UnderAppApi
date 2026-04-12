package com.tcc.underapp_api.database.repository;

import com.tcc.underapp_api.database.models.City;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for city persistence operations.
 */
public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findAllByOrderByNameAsc();

    List<City> findAllByStateIdOrderByNameAsc(Long stateId);

    Optional<City> findByIbgeCode(Integer ibgeCode);
}
