package org.example.config.service.interfaces;

import org.example.model.Data;


public interface KafkaDataService {
    void handle(Data data);
}
