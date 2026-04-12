package com.tcc.underapp_api.modules.location.controller;

import com.tcc.underapp_api.common.records.ApiResponse;
import com.tcc.underapp_api.modules.location.dto.response.CityResponse;
import com.tcc.underapp_api.modules.location.dto.response.StateResponse;
import com.tcc.underapp_api.modules.location.service.LocationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller exposing states and cities for selection fields.
 */
@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @GetMapping("/states")
    public ApiResponse<List<StateResponse>> listStates() {
        return ApiResponse.success(
                locationService.listStates()
                        .stream()
                        .map(StateResponse::fromEntity)
                        .toList()
        );
    }

    @GetMapping("/cities")
    public ApiResponse<List<CityResponse>> listCities(@RequestParam(required = false) Long stateId) {
        return ApiResponse.success(
                locationService.listCities(stateId)
                        .stream()
                        .map(CityResponse::fromEntity)
                        .toList()
        );
    }
}
