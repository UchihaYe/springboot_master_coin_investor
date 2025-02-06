package com.crypto.invest.job;

import com.crypto.invest.model.dto.PriceStats;
import com.crypto.invest.service.BinanceService;
import com.crypto.invest.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class BtcPriceMonitorJob {

    @Autowired
    private BinanceService binanceService;

    @Autowired
    private EmailService emailService;

    @Value("${spring.mail.username}")
    private String toEmail;  // 使用配置文件中的邮箱作为接收邮箱

    private static final DecimalFormat PRICE_FORMAT = new DecimalFormat("#,##0.00");
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String SYMBOL = "BTCUSDT";

    /**
     * 每10分钟执行一次
     */
    @Scheduled(fixedRate = 600000)  // 600000毫秒 = 10分钟
    public void monitorBtcPrice() {
        try {
            // 获取BTC当前价格
            double currentPrice = binanceService.getLatestPrice(SYMBOL);
            
            // 获取24小时价格变动
            PriceStats priceStats = binanceService.get24HourStats(SYMBOL);
            
            // 构建HTML邮件内容
            String htmlContent = buildHtmlEmail(currentPrice, priceStats);
            
            // 发送邮件
            emailService.sendHtmlEmail(
                toEmail,
                "BTC价格监控报告 - " + LocalDateTime.now().format(DATE_FORMAT),
                htmlContent
            );
            
            log.info("BTC价格监控邮件发送成功，当前价格: {}", currentPrice);
        } catch (Exception e) {
            log.error("BTC价格监控任务执行失败", e);
        }
    }

    private String buildHtmlEmail(double currentPrice, PriceStats stats) {
        return String.format("""
            <html>
            <head>
                <style>
                    body { font-family: Arial, sans-serif; }
                    .container { padding: 20px; }
                    .price-card {
                        background-color: #f8f9fa;
                        border-radius: 5px;
                        padding: 15px;
                        margin-bottom: 10px;
                    }
                    .title { color: #333; font-size: 18px; font-weight: bold; }
                    .price { color: #28a745; font-size: 24px; font-weight: bold; }
                    .change-positive { color: #28a745; }
                    .change-negative { color: #dc3545; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="price-card">
                        <div class="title">BTC/USDT 实时价格监控</div>
                        <div class="price">$%s</div>
                        <p>监控时间: %s</p>
                    </div>
                    <div class="price-card">
                        <h3>24小时价格统计</h3>
                        <p>最高价: $%s</p>
                        <p>最低价: $%s</p>
                        <p>24h成交量: %s BTC</p>
                        <p>价格变动: <span class="%s">%s%%</span></p>
                    </div>
                </div>
            </body>
            </html>
            """,
            PRICE_FORMAT.format(currentPrice),
            LocalDateTime.now().format(DATE_FORMAT),
            PRICE_FORMAT.format(stats.getHighPrice()),
            PRICE_FORMAT.format(stats.getLowPrice()),
            PRICE_FORMAT.format(stats.getVolume()),
            stats.getPriceChangePercent() >= 0 ? "change-positive" : "change-negative",
            PRICE_FORMAT.format(stats.getPriceChangePercent())
        );
    }
} 