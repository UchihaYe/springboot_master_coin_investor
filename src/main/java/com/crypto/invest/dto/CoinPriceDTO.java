package com.crypto.invest.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
public class CoinPriceDTO {
    // 交易对
    private String symbol;
    
    // 价格变化
    private String priceChange;
    
    // 价格变化百分比
    private String priceChangePercent;
    
    // 加权平均价格
    private String weightedAvgPrice;
    
    // 开盘价
    private String openPrice;
    
    // 最高价
    private String highPrice;
    
    // 最低价
    private String lowPrice;
    
    // 最新价
    private String lastPrice;
    
    // 成交量
    private String volume;
    
    // 成交额
    private String quoteVolume;
    
    // 开盘时间
    private Long openTime;
    
    // 收盘时间
    private Long closeTime;
    
    // 第一笔成交ID
    private Long firstId;
    
    // 最后一笔成交ID
    private Long lastId;
    
    // 成交笔数
    private Long count;

    /**
     * 获取BigDecimal格式的最新价格
     */
    public BigDecimal getLastPriceDecimal() {
        return new BigDecimal(lastPrice);
    }
    
    /**
     * 获取BigDecimal格式的价格变化
     */
    public BigDecimal getPriceChangeDecimal() {
        return new BigDecimal(priceChange);
    }
    
    /**
     * 获取LocalDateTime格式的开盘时间
     */
    public LocalDateTime getOpenDateTime() {
        return LocalDateTime.ofInstant(
            Instant.ofEpochMilli(openTime), 
            ZoneId.systemDefault()
        );
    }
    
    /**
     * 获取LocalDateTime格式的收盘时间
     */
    public LocalDateTime getCloseDateTime() {
        return LocalDateTime.ofInstant(
            Instant.ofEpochMilli(closeTime), 
            ZoneId.systemDefault()
        );
    }
    
    /**
     * 获取格式化的价格变化百分比
     */
    public String getFormattedPriceChangePercent() {
        return priceChangePercent + "%";
    }
} 