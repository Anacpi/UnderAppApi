package com.tcc.underapp_api.modules.location.service;

import com.tcc.underapp_api.database.models.City;
import com.tcc.underapp_api.database.models.State;
import com.tcc.underapp_api.database.repository.CityRepository;
import com.tcc.underapp_api.database.repository.StateRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service responsible for state and city retrieval.
 */
@Service
@RequiredArgsConstructor
public class LocationService {
    private final StateRepository stateRepository;
    private final CityRepository cityRepository;

    public List<State> listStates() {
        return stateRepository.findAllByOrderByNameAsc();
    }

    public List<City> listCities(Long stateId) {
        return stateId == null
                ? cityRepository.findAllByOrderByNameAsc()
                : cityRepository.findAllByStateIdOrderByNameAsc(stateId);
    }
}
