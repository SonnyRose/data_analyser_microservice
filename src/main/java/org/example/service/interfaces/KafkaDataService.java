package org.example.service.interfaces;

import org.example.model.Data;


public interface KafkaDataService {
    void handle(Data data);
}
