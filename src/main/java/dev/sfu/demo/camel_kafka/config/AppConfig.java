package dev.sfu.demo.camel_kafka.config;

import dev.sfu.demo.camel_kafka.model.WordCountResult;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public WordCountResult initWordCountResult() {
        return new WordCountResult();
    }

}
