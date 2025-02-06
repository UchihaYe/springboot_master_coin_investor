package com.crypto.invest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CryptoInvestApplication {
    public static void main(String[] args) {
        SpringApplication.run(CryptoInvestApplication.class, args);
    }
} 