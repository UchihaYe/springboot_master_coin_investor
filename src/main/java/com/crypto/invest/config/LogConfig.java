package com.crypto.invest.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

@Configuration
public class LogConfig {
    
    private static final Logger log = LoggerFactory.getLogger(LogConfig.class);
    
    @Value("${logging.file.path}")
    private String logPath;
    
    @PostConstruct
    public void init() {
        File logDir = new File(logPath);
        if (!logDir.exists()) {
            boolean created = logDir.mkdirs();
            if (created) {
                log.info("Created log directory: {}", logDir.getAbsolutePath());
            } else {
                log.warn("Failed to create log directory: {}", logDir.getAbsolutePath());
            }
        }
    }
} 