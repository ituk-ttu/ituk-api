package ee.ituk.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.ituk.api.utils.ObjectMapperUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringObjectMapperConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        return ObjectMapperUtils.newInstance();
    }
}
