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
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;


@Testcontainers
@EmbeddedKafka(partitions = 1, topics = {"data-temperature", "data-power", "data-voltage"})
public class KafkaDataServiceImplTest {
    @Mock
    private DataRepository dataRepository;
    @Mock
    private Logger logger;
    @Container
    private static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:latest");
    @Container
    private static final KafkaContainer kafka =
            new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));
    @InjectMocks
    private KafkaDataServiceImpl service;
    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        kafka.start();
        postgres.start();
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
    @Test
    void handle_exceptionThrown_shouldLogError() {
        doThrow(new RuntimeException("Database error")).when(dataRepository).save(any());

        Data data = createData();

        service.handle(data);

        verify(logger).error(eq("Error handling data: {}"), eq(data), any());
    }
    @Test
    void handle_nullData_shouldNotThrowException() {
        service.handle(null);

        verifyNoInteractions(dataRepository, logger);
    }
    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext>{
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgres.getJdbcUrl(),
                    "spring.datasource.username=" + postgres.getUsername(),
                    "spring.datasource.password=" + postgres.getPassword(),
                    "spring.kafka.bootstrap-servers=" + kafka.getBootstrapServers()
            ).applyTo(applicationContext.getEnvironment());
        }
    }
}
