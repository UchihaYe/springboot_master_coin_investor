package com.crypto.invest.service;

import com.binance.connector.client.SpotClient;
import com.crypto.invest.model.dto.PriceStats;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson2.JSON;
import com.crypto.invest.model.dto.CoinPriceDTO;
import com.crypto.invest.model.dto.AccountDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.alibaba.fastjson2.JSONObject;

import java.util.LinkedHashMap;

@Slf4j
@Service
public class BinanceService {
    @Autowired
    private SpotClient spotClient;

    @Value("${binance.api.baseUrl}")
    private String baseUrl;

    private final OkHttpClient client = new OkHttpClient();

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

    /**
     * 获取最新价格
     */
    public double getLatestPrice(String symbol) {
        try {
            String url = baseUrl + "/api/v3/ticker/price?symbol=" + symbol;
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) throw new RuntimeException("请求失败");
                
                String body = response.body().string();
                JSONObject json = JSON.parseObject(body);
                return json.getDoubleValue("price");
            }
        } catch (Exception e) {
            log.error("获取{}价格失败", symbol, e);
            throw new RuntimeException("获取价格失败", e);
        }
    }

    /**
     * 获取24小时价格统计
     */
    public PriceStats get24HourStats(String symbol) {
        try {
            String url = baseUrl + "/api/v3/ticker/24hr?symbol=" + symbol;
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) throw new RuntimeException("请求失败");
                
                String body = response.body().string();
                log.info("获取{}24小时统计原始数据: {}", symbol, body);
                
                JSONObject json = JSON.parseObject(body);
                
                PriceStats stats = new PriceStats();
                stats.setHighPrice(json.getDoubleValue("highPrice"));
                stats.setLowPrice(json.getDoubleValue("lowPrice"));
                stats.setVolume(json.getDoubleValue("volume"));
                stats.setPriceChangePercent(json.getDoubleValue("priceChangePercent"));
                
                if (stats.getLowPrice() < 1000 || stats.getHighPrice() > 1000000) {
                    log.error("价格数据异常 - 最高价: {}, 最低价: {}", stats.getHighPrice(), stats.getLowPrice());
                    throw new RuntimeException("价格数据异常，请检查API返回值");
                }
                
                return stats;
            }
        } catch (Exception e) {
            log.error("获取{}24小时统计失败", symbol, e);
            throw new RuntimeException("获取24小时统计失败", e);
        }
    }
} 