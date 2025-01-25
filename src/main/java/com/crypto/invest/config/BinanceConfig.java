package com.crypto.invest.config;

import com.binance.connector.client.SpotClient;
import com.binance.connector.client.impl.SpotClientImpl;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "binance.api")
public class BinanceConfig {
    private String key;
    private String secret;
    private boolean useTestnet;
    private String baseUrl;
    private String wsBaseUrl;

    @Bean
    public SpotClient spotClient() {
        if (useTestnet) {
            return new SpotClientImpl(key, secret, baseUrl);
        }
        return new SpotClientImpl(key, secret);
    }
} 