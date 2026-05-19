package com.tcc.underapp_api.database.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * Entity representing a monitoring point persisted in the database.
 */
@Entity
@Table(name = "monitor_points")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE monitor_points SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class MonitorPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    /** Unit: N/m^2. */
    @Column(name = "cohesion", nullable = false, precision = 18, scale = 6)
    private BigDecimal cohesion;

    /** Unit: kg/m^3. */
    @Column(name = "wet_soil_density", nullable = false, precision = 18, scale = 6)
    private BigDecimal wetSoilDensity;

    /** Unit: m/s^2. */
    @Column(name = "gravity_acceleration", nullable = false, precision = 18, scale = 6)
    private BigDecimal gravityAcceleration;

    /** Unit: m. */
    @Column(name = "soil_depth", nullable = false, precision = 18, scale = 6)
    private BigDecimal soilDepth;

    /** Unit: degrees. */
    @Column(name = "slope_angle", nullable = false, precision = 18, scale = 6)
    private BigDecimal slopeAngle;

    /** Unit: kg/m^3. */
    @Column(name = "water_density", nullable = false, precision = 18, scale = 6)
    private BigDecimal waterDensity;

    /** Unit: degrees. */
    @Column(name = "internal_friction_angle", nullable = false, precision = 18, scale = 6)
    private BigDecimal internalFrictionAngle;

    /** Unit: dimensionless ratio. */
    @Column(name = "safety_factor_threshold_ratio", nullable = false, precision = 18, scale = 6)
    private BigDecimal safetyFactorThresholdRatio;

    /** Unit: m. */
    @Column(name = "sensor_depth", nullable = false, precision = 18, scale = 6)
    private BigDecimal sensorDepth;

    @OneToMany(mappedBy = "monitorPoint")
    private List<RiskAssessment> riskAssessments = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;
}
