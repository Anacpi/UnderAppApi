package com.tcc.underapp_api.database.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

/**
 * Entity representing a Brazilian state.
 */
@Entity
@Table(
        name = "states",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_states_ibge_code", columnNames = "ibge_code"),
                @UniqueConstraint(name = "uk_states_uf", columnNames = "uf")
        },
        indexes = @Index(name = "idx_states_name", columnList = "name")
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE states SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "uf", nullable = false, length = 2)
    private String uf;

    @Column(name = "ibge_code", nullable = false)
    private Integer ibgeCode;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;
}
