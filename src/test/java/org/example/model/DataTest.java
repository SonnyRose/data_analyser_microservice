package org.example.model;

import org.example.model.measurementType.MeasurementType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class DataTest {
    @Test
    public void testDataInitialization() {
        Long id = 1L;
        Long sensorId = 2L;
        LocalDateTime timeStamp = LocalDateTime.now();
        double measurement = 25.0;
        MeasurementType measurementType = MeasurementType.TEMPERATURE;

        Data data = new Data();
        data.setId(id);
        data.setSensorId(sensorId);
        data.setTimeStamp(timeStamp);
        data.setMeasurement(measurement);
        data.setMeasurementType(measurementType);

        assertThat(data.getId()).isEqualTo(id);
        assertThat(data.getSensorId()).isEqualTo(sensorId);
        assertThat(data.getTimeStamp()).isEqualTo(timeStamp);
        assertThat(data.getMeasurement()).isEqualTo(measurement);
        assertThat(data.getMeasurementType()).isEqualTo(measurementType);
    }

    @Test
    public void testDataToString() {
        Data data = new Data();
        data.setId(1L);
        data.setSensorId(2L);
        data.setTimeStamp(LocalDateTime.now());
        data.setMeasurement(25.0);
        data.setMeasurementType(MeasurementType.TEMPERATURE);

        String toStringResult = data.toString();

        assertThat(toStringResult)
                .contains("id=1", "sensorId=2", "measurement=25.0", "measurementType=TEMPERATURE");
    }
}
