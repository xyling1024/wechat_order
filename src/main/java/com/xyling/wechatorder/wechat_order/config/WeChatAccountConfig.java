package com.xyling.wechatorder.wechat_order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by: xyling
 * 2018-03-07 20:50
 */
@Data
@ConfigurationProperties(prefix = "wechat")
@Component
public class WeChatAccountConfig {

        private String mpAppId;

        private String mpAppSecret;

        private String mchId;

        private String mchKey;

        private String keyPath;

        private String notifyUrl;

}
