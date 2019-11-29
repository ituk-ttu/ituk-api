package ee.ituk.api.config;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.httpclient.LogbookHttpRequestInterceptor;
import org.zalando.logbook.httpclient.LogbookHttpResponseInterceptor;

@Configuration
public class LoggingConfiguration {

    @Bean
    public CloseableHttpClient httpClient(
            LogbookHttpRequestInterceptor requestInterceptor,
            LogbookHttpResponseInterceptor responseInterceptor) {
        return HttpClientBuilder.create()
                .addInterceptorFirst(requestInterceptor)
                .addInterceptorFirst(responseInterceptor)
                .useSystemProperties()
                .build();
    }
}
