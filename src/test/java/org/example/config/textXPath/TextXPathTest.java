package org.example.config.textXPath;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TextXPathTest {
    @Test
    public void shouldReturnNullIfXmlDocumentDoesNotContainNodeMatchingXPathExpression() {
        XML xml =
                new XMLDocument("<consumer><groupId>1</groupId><keyDeserializer>org.apache.kafka.common.serialization.StringDeserializer</keyDeserializer><valueDeserializer>org.apache.kafka.common.serialization.StringDeserializer</valueDeserializer><trustedPackages>*</trustedPackages></consumer>");
        String node = "/consumer/nonExistentNode";

        TextXPath textXPath = new TextXPath(xml, node);
        String actualText = textXPath.toString();

        assertNull(actualText);
    }
        @Test
        public void shouldReturnCorrectTextContentIfXmlDocumentContainsNodeMatchingXPathExpression() {
            XML xml =
                    new XMLDocument("<producer><keySerializer>org.apache.kafka.common.serialization.StringSerializer</keySerializer><valueSerializer>org.springframework.kafka.support.serializer.JsonSerializer</valueSerializer></producer>");
            String node = "/producer/keySerializer";

            TextXPath textXPath = new TextXPath(xml, node);
            String actualText = textXPath.toString();

            assertEquals(
                    "org.apache.kafka.common.serialization.StringSerializer",
                    actualText);
        }
        @Test
        public void shouldCreateTextXPathWithValidParameters() {
            XML xml =
                    new XMLDocument("<producer><keySerializer>org.apache.kafka.common.serialization.StringSerializer</keySerializer><valueSerializer>org.springframework.kafka.support.serializer.JsonSerializer</valueSerializer></producer>");
            String node = "/producer/keySerializer";

            TextXPath textXPath = new TextXPath(xml, node);

            assertNotNull(textXPath);
        }
    }


