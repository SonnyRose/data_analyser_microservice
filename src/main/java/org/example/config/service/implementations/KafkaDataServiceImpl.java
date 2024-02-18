package org.example.config.service.implementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.config.service.interfaces.KafkaDataService;
import org.example.model.Data;
import org.example.repository.DataRepository;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaDataServiceImpl implements KafkaDataService {
    private final DataRepository dataRepository;
    @Override
    public void handle(Data data) {
        try {
            log.info("Handling data: {}", data);
            dataRepository.save(data);
            log.info("Data saved: {}", data);
        } catch (Exception e) {
            log.error("Error handling data: {}", data, e);
        }
    }
}
