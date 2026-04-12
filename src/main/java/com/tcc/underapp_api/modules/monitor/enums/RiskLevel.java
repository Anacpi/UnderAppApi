package com.tcc.underapp_api.modules.monitor.enums;

/**
 * Ordered risk levels for a monitoring point.
 */
public enum RiskLevel {
    NORMAL(1),
    MODERATE(2),
    HIGH(3),
    VERY_HIGH(4),
    CRITICAL(5);

    private final int severity;

    RiskLevel(int severity) {
        this.severity = severity;
    }

    public int getSeverity() {
        return severity;
    }
}
