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
public class WeChatOrderRequest {

    /**
     * 商户ID
     */
    private String uid;

    /**
     *  对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。
     */
    private String body;

    /**
     * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*且在同一个商户号下唯一。详见商户订单号
     */
    private String outTradeNo;

    /**
     * 订单总金额，单位为分，详见支付金额
     */
    private BigDecimal totalFee;

    /**
     * 订单优惠标记，使用代金券
     */
    private String goodsTag;


}
