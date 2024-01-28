package org.example.config.deserializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalDataTimeDeserializerTest {
    @Test
    public void successfulDeserializationTest() {
        JsonElement jsonElement = JsonParser.parseString("[2024, 2, 1, 20, 59, 0]");
        LocalDateTimeDeserializer deserializer = new LocalDateTimeDeserializer();
        LocalDateTime localDateTime = deserializer
                .deserialize(jsonElement,
                        LocalDateTime.class,
                        null);
        assertThat(localDateTime)
                .isEqualTo(LocalDateTime.of(2024, 2, 1, 20, 59, 0));
    }
}
