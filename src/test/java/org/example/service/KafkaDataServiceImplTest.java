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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.LocalDateTime;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@Testcontainers
@AutoConfigureMockMvc
@EmbeddedKafka(partitions = 1, topics = {"data-temperature", "data-power", "data-voltage"})
public class KafkaDataServiceImplTest {
    @Mock
    private DataRepository dataRepository;
    @Mock
    private Logger logger;
    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:latest");
    @Container
    @ServiceConnection
    private static final KafkaContainer kafka =
            new KafkaContainer(
                    DockerImageName
                            .parse("confluentinc/cp-kafka:latest"));
    @InjectMocks
    private KafkaDataServiceImpl service;

    @DynamicPropertySource
    private static void configureProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url=", postgres::getJdbcUrl);
        registry.add("spring.datasource.username=", postgres::getUsername);
        registry.add("spring.datasource.password=", postgres::getPassword);
        registry.add("spring.kafka.bootstrap-servers=", kafka::getBootstrapServers);
        registry.add("spring.jpa.generate-ddl", () -> true);
    }
    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        kafka.start();
        postgres.start();
    }
    @Test
    public void handle_validData_shouldSaveToRepository() {
        Data data = createData();
        service.handle(data);
        verify(dataRepository).save(data);
        verifyNoMoreInteractions(logger);
    }
    @Test
    public void handle_exceptionThrown_shouldLogError() {
        doThrow(new RuntimeException("Database error")).when(dataRepository).save(any());
        Data data = createData();
        service.handle(data);
        verify(logger).error(eq("Error handling data: {}"), eq(data), any());
    }
    @Test
    public void handle_nullData_shouldNotThrowException() {
        service.handle(null);
        verifyNoInteractions(dataRepository, logger);
    }
    @Test
    public void postgresIsCreatedAndStarted(){
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();
    }
    @Test
    public void kafkaIsCreatedAndStarted(){
        assertThat(kafka.isCreated()).isTrue();
        assertThat(kafka.isRunning()).isTrue();
    }
    @Test
    public void postgresSettingsMatches(){
        assertThat(postgres.getJdbcUrl())
                .isEqualTo("jdbc:postgresql://postgres:5432data_analyzer_microservice");

        assertThat(postgres.getPassword())
                .isEqualTo("1505Duma");

        assertThat(postgres.getUsername())
                .isEqualTo("postgres");

        assertThat(postgres.getDatabaseName())
                .isEqualTo("data_analyzer_microservice");
    }
    @Test
    public void kafkaSettingsMatches(){
        assertThat(kafka.getBootstrapServers())
                .isEqualTo("localhost:9092");
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
