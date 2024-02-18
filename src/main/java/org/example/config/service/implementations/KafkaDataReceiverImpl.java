package org.example.config.service.implementations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.config.deserializer.LocalDateTimeDeserializer;
import org.example.config.service.interfaces.KafkaDataReceiver;
import org.example.config.service.interfaces.KafkaDataService;
import org.example.model.Data;
import org.springframework.stereotype.Service;
import reactor.kafka.receiver.KafkaReceiver;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaDataReceiverImpl implements KafkaDataReceiver {
    private final KafkaReceiver<String, Object> receiver;
    private final LocalDateTimeDeserializer localDateTimeDeserializer;
    private final KafkaDataService kafkaDataService;
    @PostConstruct
    private void init() {
        fetch();
    }
    @Override
    public void fetch() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, localDateTimeDeserializer)
                .create();
        receiver.receive()
                .subscribe(r -> {
                    Data data = gson.fromJson(r.value().toString(), Data.class);
                    log.info("Deserialized Data: {}", data);
                    kafkaDataService.handle(data);

                    if (r.receiverOffset() != null) {
                        r.receiverOffset().acknowledge();
                        log.info("Data handling complete for: {}", data);
                    } else {
                        log.warn("Receiver offset is null for Data: {}", data);
                    }
                });
    }
}
