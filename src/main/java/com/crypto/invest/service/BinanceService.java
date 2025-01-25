package com.crypto.invest.service;

import com.binance.connector.client.SpotClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson2.JSON;
import com.crypto.invest.dto.CoinPriceDTO;
import com.crypto.invest.dto.AccountDTO;

import java.util.LinkedHashMap;

@Slf4j
@Service
public class BinanceService {
    @Autowired
    private SpotClient spotClient;

    /**
     * 获取BTC当前价格
     */
    public CoinPriceDTO getBTCPrice() {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", "BTCUSDT");
        String result = spotClient.createMarket().ticker(parameters);
        log.info("BTC价格查询结果: {}", result);
        return JSON.parseObject(result, CoinPriceDTO.class);
    }

    /**
     * 下单买入BTC
     */
    public String buyBTC(String quantity, String price) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", "BTCUSDT");
        parameters.put("side", "BUY");
        parameters.put("type", "LIMIT");
        parameters.put("timeInForce", "GTC");
        parameters.put("quantity", quantity);
        parameters.put("price", price);

        String result = spotClient.createTrade().newOrder(parameters);
        log.info("BTC买入订单结果: {}", result);
        return result;
    }

    /**
     * 获取账户信息
     */
    public AccountDTO getAccountInfo() {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        String result = spotClient.createTrade().account(parameters);
        log.info("账户信息: {}", result);
        return JSON.parseObject(result, AccountDTO.class);
    }

    /**
     * 获取历史订单
     */
    public String getOrders() {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", "BTCUSDT");
        
        String result = spotClient.createTrade().getOrders(parameters);
        log.info("订单历史: {}", result);
        return result;
    }
} 