package com.crypto.invest.controller;

import com.crypto.invest.common.Result;
import com.crypto.invest.service.BinanceService;
import com.crypto.invest.dto.CoinPriceDTO;
import com.crypto.invest.dto.AccountDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/binance")
public class BinanceController {
    @Autowired
    private BinanceService binanceService;

    @GetMapping("/btc/price")
    public Result<CoinPriceDTO> getBTCPrice() {
        try {
            CoinPriceDTO price = binanceService.getBTCPrice();
            return Result.ok("获取BTC价格成功", price);
        } catch (Exception e) {
            log.error("获取BTC价格失败", e);
            return Result.fail("获取BTC价格失败: " + e.getMessage());
        }
    }

    @PostMapping("/btc/buy")
    public Result<String> buyBTC(@RequestParam String quantity, @RequestParam String price) {
        try {
            String orderResult = binanceService.buyBTC(quantity, price);
            return Result.ok("BTC买入订单提交成功", orderResult);
        } catch (Exception e) {
            log.error("BTC买入失败", e);
            return Result.fail("BTC买入失败: " + e.getMessage());
        }
    }

    @GetMapping("/account")
    public Result<AccountDTO> getAccountInfo() {
        try {
            AccountDTO accountInfo = binanceService.getAccountInfo();
            return Result.ok("获取账户信息成功", accountInfo);
        } catch (Exception e) {
            log.error("获取账户信息失败", e);
            return Result.fail("获取账户信息失败: " + e.getMessage());
        }
    }

    @GetMapping("/orders")
    public Result<String> getOrders() {
        try {
            String orders = binanceService.getOrders();
            return Result.ok("获取订单历史成功", orders);
        } catch (Exception e) {
            log.error("获取订单历史失败", e);
            return Result.fail("获取订单历史失败: " + e.getMessage());
        }
    }
} 