package org.example.config;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KafkaConfigTest {
    @Test
    public void testReceiverProperties() {
        XML settings = new XMLDocument("<xml><groupId>testGroup</groupId><keyDeserializer>...</keyDeserializer>...</xml>");
        Map<String, Object> properties = new KafkaConfig(settings).receiverProperties();

        assertEquals("testGroup", properties.get(ConsumerConfig.GROUP_ID_CONFIG));
        // ... (assert other properties)
    }
}
