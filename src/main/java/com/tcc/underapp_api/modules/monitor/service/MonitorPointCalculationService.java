package com.tcc.underapp_api.modules.monitor.service;

import com.tcc.underapp_api.common.errors.ClientException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;

/**
 * Calculates the critical sensor depth using the safety factor equation.
 */
@Service
public class MonitorPointCalculationService {
    private static final int SCALE = 6;

    /**
     * Calculates h from the safety factor equation.
     *
     * @param cohesion unit: N/m^2
     * @param wetSoilDensity unit: kg/m^3
     * @param gravityAcceleration unit: m/s^2
     * @param soilDepth unit: m
     * @param slopeAngle unit: degrees
     * @param waterDensity unit: kg/m^3
     * @param internalFrictionAngle unit: degrees
     * @param safetyFactorThresholdRatio unit: dimensionless ratio
     * @return sensor depth, unit: m
     */
    public BigDecimal calculateSensorDepth(
            BigDecimal cohesion,
            BigDecimal wetSoilDensity,
            BigDecimal gravityAcceleration,
            BigDecimal soilDepth,
            BigDecimal slopeAngle,
            BigDecimal waterDensity,
            BigDecimal internalFrictionAngle,
            BigDecimal safetyFactorThresholdRatio
    ) {
        double c = cohesion.doubleValue();
        double rhoS = wetSoilDensity.doubleValue();
        double g = gravityAcceleration.doubleValue();
        double z = soilDepth.doubleValue();
        double theta = Math.toRadians(slopeAngle.doubleValue());
        double rhoW = waterDensity.doubleValue();
        double phi = Math.toRadians(internalFrictionAngle.doubleValue());
        double threshold = safetyFactorThresholdRatio.doubleValue();

        double cosTheta = Math.cos(theta);
        double sinTheta = Math.sin(theta);
        double cosThetaSquared = cosTheta * cosTheta;
        double tanPhi = Math.tan(phi);

        double denominator = rhoW * g * cosThetaSquared * tanPhi;

        if (!Double.isFinite(denominator) || Math.abs(denominator) < 1e-12) {
            throw new ClientException(
                    "Invalid safety factor equation denominator",
                    "Nao foi possivel calcular a profundidade do sensor com os parametros informados."
            );
        }

        double numerator = c
                + (rhoS * g * z * cosThetaSquared * tanPhi)
                - (threshold * rhoS * g * z * cosTheta * sinTheta);

        double sensorDepth = numerator / denominator;

        if (!Double.isFinite(sensorDepth)) {
            throw new ClientException(
                    "Invalid sensor depth calculation result",
                    "Nao foi possivel calcular a profundidade do sensor com os parametros informados."
            );
        }

        BigDecimal result = BigDecimal.valueOf(sensorDepth).setScale(SCALE, RoundingMode.HALF_UP);

        if (result.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ClientException(
                    "Calculated sensor depth must be greater than zero",
                    "A profundidade calculada do sensor deve ser maior que zero."
            );
        }

        if (result.compareTo(soilDepth) > 0) {
            throw new ClientException(
                    "Calculated sensor depth is deeper than soil depth",
                    "A profundidade calculada do sensor nao pode ser maior que a profundidade do solo."
            );
        }

        return result;
    }
}
