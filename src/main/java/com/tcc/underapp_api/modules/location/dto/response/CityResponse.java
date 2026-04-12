package com.tcc.underapp_api.modules.location.dto.response;

import com.tcc.underapp_api.database.models.City;

/**
 * API response for cities.
 *
 * @param id internal city identifier
 * @param name city name
 * @param ibgeCode official IBGE code
 * @param stateId state identifier
 * @param stateName state name
 * @param stateUf state acronym
 */
public record CityResponse(
        Long id,
        String name,
        Integer ibgeCode,
        Long stateId,
        String stateName,
        String stateUf
) {
    public static CityResponse fromEntity(City city) {
        return new CityResponse(
                city.getId(),
                city.getName(),
                city.getIbgeCode(),
                city.getState().getId(),
                city.getState().getName(),
                city.getState().getUf()
        );
    }
}
