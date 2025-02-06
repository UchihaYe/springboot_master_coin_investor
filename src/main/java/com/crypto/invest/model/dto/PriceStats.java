package com.crypto.invest.model.dto;

import lombok.Data;

@Data
public class PriceStats {
    private double highPrice;      // 24小时最高价
    private double lowPrice;       // 24小时最低价
    private double volume;         // 24小时成交量
    private double priceChangePercent;  // 24小时价格变动百分比
} 