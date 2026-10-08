package cl.duoc.ms_ordenes.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${app.inventario-url:http://localhost:8083}")
    private String inventarioUrl;

    @Bean
    public WebClient inventarioWebClient() {
        return WebClient.builder()
                .baseUrl(inventarioUrl)
                .build();
    }
}


