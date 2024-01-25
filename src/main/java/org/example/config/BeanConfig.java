package org.example.config;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;

@Configuration
public class BeanConfig {
    @SneakyThrows
    @Bean
    public XML consumerXml() {
        return new XMLDocument(
                getClass()
                        .getResourceAsStream("/kafka/consumer.xml")
                        .readAllBytes()
        );
    }
}
