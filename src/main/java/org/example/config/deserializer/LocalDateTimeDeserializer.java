package org.example.config.deserializer;

import com.google.gson.*;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {
    private static final Logger log = LoggerFactory.getLogger(LocalDateTimeDeserializer.class);
    @Override
    public LocalDateTime deserialize(
            JsonElement json,
            Type typeOfT,
            JsonDeserializationContext context
    ) {
        try {
            if (json.isJsonArray()) {
                JsonArray jsonArray = json.getAsJsonArray();
                int year = jsonArray.get(0).getAsInt();
                int month = jsonArray.get(1).getAsInt();
                int day = jsonArray.get(2).getAsInt();
                int hour = jsonArray.get(3).getAsInt();
                int minute = jsonArray.get(4).getAsInt();
                int second = jsonArray.get(5).getAsInt();
                return LocalDateTime.of(year, month, day, hour, minute, second);
            } else if (json.isJsonPrimitive() && json.getAsJsonPrimitive().isString()) {
                return LocalDateTime.parse(json.getAsJsonPrimitive().getAsString());
            } else {
                log.warn("Invalid JSON format for LocalDateTime: {}", json);
                throw new JsonParseException("Invalid JSON format for LocalDateTime");
            }
        } catch (Exception e) {
            log.error("Error during LocalDateTime deserialization", e);
            throw new JsonParseException("Error during LocalDateTime deserialization", e);
        }
    }
        //цей метод для того, що LocalDateTime вертається як масив, а треба як числа

}
