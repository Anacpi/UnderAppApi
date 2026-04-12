package com.tcc.underapp_api.database.repository;

import com.tcc.underapp_api.database.models.State;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for state persistence operations.
 */
public interface StateRepository extends JpaRepository<State, Long> {
    List<State> findAllByOrderByNameAsc();

    Optional<State> findByIbgeCode(Integer ibgeCode);
}
