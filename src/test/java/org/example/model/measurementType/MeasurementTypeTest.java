package org.example.model.measurementType;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MeasurementTypeTest {
    @Test
    public void testEnumValues() {
        MeasurementType[] expectedValues = MeasurementType.values();
        assertThat(expectedValues)
                .containsExactly(MeasurementType.TEMPERATURE,
                        MeasurementType.VOLTAGE,
                        MeasurementType.POWER);
    }
    @Test
    public void testEnumToString() {
        MeasurementType measurementType = MeasurementType.TEMPERATURE;
        String toStringResult = measurementType.toString();
        assertThat(toStringResult).isEqualTo("TEMPERATURE");
    }
    @Test
    void testEnumValueOf() {
        String stringValue = "VOLTAGE";
        MeasurementType measurementType = MeasurementType.valueOf(stringValue);
        assertThat(measurementType).isEqualTo(MeasurementType.VOLTAGE);
    }
    @Test
    void testEnumOrdinal() {
        MeasurementType measurementType = MeasurementType.POWER;
        int ordinalValue = measurementType.ordinal();
        assertThat(ordinalValue).isEqualTo(2);
    }
}
