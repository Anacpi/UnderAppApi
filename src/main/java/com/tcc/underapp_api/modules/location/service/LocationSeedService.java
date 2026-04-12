package com.tcc.underapp_api.modules.location.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcc.underapp_api.database.models.City;
import com.tcc.underapp_api.database.models.State;
import com.tcc.underapp_api.database.repository.CityRepository;
import com.tcc.underapp_api.database.repository.StateRepository;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * Loads the bundled IBGE seed for states and cities when the database is empty.
 */
@Component
@RequiredArgsConstructor
public class LocationSeedService implements ApplicationRunner {
    private final ObjectMapper objectMapper;
    private final StateRepository stateRepository;
    private final CityRepository cityRepository;

    @Value("classpath:seeds/br-location-seed.json")
    private Resource seedResource;

    @Value("${app.seed.locations.enabled:true}")
    private boolean seedEnabled;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        if (!seedEnabled || stateRepository.count() > 0 || cityRepository.count() > 0) {
            return;
        }

        LocationSeed seed = readSeed();
        Map<Integer, State> statesByIbgeCode = saveStates(seed.states());
        saveCities(seed.cities(), statesByIbgeCode);
    }

    private LocationSeed readSeed() throws IOException {
        return objectMapper.readValue(seedResource.getInputStream(), LocationSeed.class);
    }

    private Map<Integer, State> saveStates(List<StateSeedItem> seedStates) {
        List<State> states = seedStates.stream()
                .map(item -> {
                    State state = new State();
                    state.setName(item.name());
                    state.setUf(item.uf());
                    state.setIbgeCode(item.ibgeCode());
                    return state;
                })
                .toList();

        List<State> savedStates = stateRepository.saveAll(states);
        Map<Integer, State> statesByIbgeCode = new HashMap<>();

        for (State state : savedStates) {
            statesByIbgeCode.put(state.getIbgeCode(), state);
        }

        return statesByIbgeCode;
    }

    private void saveCities(List<CitySeedItem> seedCities, Map<Integer, State> statesByIbgeCode) {
        List<City> batch = new ArrayList<>();

        for (CitySeedItem item : seedCities) {
            State state = statesByIbgeCode.get(item.stateIbgeCode());
            if (state == null) {
                continue;
            }

            City city = new City();
            city.setName(item.name());
            city.setIbgeCode(item.ibgeCode());
            city.setState(state);
            batch.add(city);

            if (batch.size() == 500) {
                cityRepository.saveAll(batch);
                batch.clear();
            }
        }

        if (!batch.isEmpty()) {
            cityRepository.saveAll(batch);
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record LocationSeed(List<StateSeedItem> states, List<CitySeedItem> cities) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record StateSeedItem(Integer ibgeCode, String name, String uf) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record CitySeedItem(Integer ibgeCode, String name, Integer stateIbgeCode) {
    }
}
