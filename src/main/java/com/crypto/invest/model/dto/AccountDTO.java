package com.crypto.invest.model.dto;

import lombok.Data;
import java.util.List;

@Data
public class AccountDTO {
    // 手续费相关
    private Integer makerCommission;
    private Integer takerCommission;
    private Integer buyerCommission;
    private Integer sellerCommission;
    
    // 账户权限相关
    private Boolean canTrade;
    private Boolean canWithdraw;
    private Boolean canDeposit;
    private Boolean brokered;
    private Boolean requireSelfTradePrevention;
    private Boolean preventSor;
    
    // 账户信息
    private Long updateTime;
    private String accountType;
    
    // 手续费率
    private CommissionRates commissionRates;
    
    // 资产余额列表
    private List<Balance> balances;
    
    // 权限列表
    private List<String> permissions;
    
    // 用户ID
    private Long uid;
    
    @Data
    public static class CommissionRates {
        private String maker;
        private String taker;
        private String buyer;
        private String seller;
    }
    
    @Data
    public static class Balance {
        private String asset;  // 资产名称
        private String free;   // 可用余额
        private String locked; // 锁定余额
    }
} 