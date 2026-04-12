package com.tcc.underapp_api.modules.location.dto.response;

import com.tcc.underapp_api.database.models.State;

/**
 * API response for states.
 *
 * @param id internal state identifier
 * @param name state name
 * @param uf state acronym
 * @param ibgeCode official IBGE code
 */
public record StateResponse(Long id, String name, String uf, Integer ibgeCode) {
    public static StateResponse fromEntity(State state) {
        return new StateResponse(state.getId(), state.getName(), state.getUf(), state.getIbgeCode());
    }
}
