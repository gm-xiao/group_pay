package com.example.grouppay.util.wechat;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName WeChatOrderRequest
 * @Description TODO
 * @Author gm
 * @Date 2019/5/23 10:34
 * @Version 1.0
 **/
@Data
public class WeChatCouponReceiveRequest {

    /**
     * 商户ID
     */
    private String uid;

    /**
     * 微信用户唯一标识
     */
    private String openId;

    /**
     * 单据号
     */
    private String tradeNo;

}
