package org.example.service;

import org.example.model.Data;
import org.example.model.measurementType.MeasurementType;
import org.example.repository.DataRepository;
import org.example.service.implementations.KafkaDataServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;


public class KafkaDataServiceImplTest {
    @Mock
    private DataRepository dataRepository;
    @Mock
    private Logger logger;
    @InjectMocks
    private KafkaDataServiceImpl service;
    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void handle_validData_shouldSaveToRepository() {
        Data data = createData();
        service.handle(data);
        verify(dataRepository).save(data);
        verifyNoMoreInteractions(logger);
    }
    private Data createData(){
        Data data = new Data();
        data.setSensorId(1L);
        data.setTimeStamp(LocalDateTime.now());
        data.setMeasurement(25.0);
        data.setMeasurementType(MeasurementType.TEMPERATURE);
        return data;
    }
}
