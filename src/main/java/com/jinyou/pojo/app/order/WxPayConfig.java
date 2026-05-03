package com.jinyou.pojo.app.order;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "wx.pay") // 对应 application.yml 里的配置前缀
public class WxPayConfig {
    // 小程序appId
    private String appId;
    // 商户号
    private String mchId;
    // 商户密钥
    private String mchKey;
    // 支付回调地址
    private String notifyUrl;
    // 证书路径（可选，退款时需要）
    private String keyPath;
}