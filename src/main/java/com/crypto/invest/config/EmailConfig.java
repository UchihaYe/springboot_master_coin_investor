package com.crypto.invest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync  // 启用异步支持，使邮件发送不阻塞主线程
public class EmailConfig {
    // 如果需要自定义邮件配置，可以在这里添加
} 