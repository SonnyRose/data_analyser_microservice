package org.example.service.interfaces;

import reactor.kafka.receiver.ReceiverOptions;

import java.util.List;

public interface KafkaDataReceiver {
    void fetch();
}
