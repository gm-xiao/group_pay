package com.example.grouppay.util;

import lombok.Data;

/**
 * @ClassName AppOrderInfoRequest
 * @Description TODO
 * @Author gm
 * @Date 2019/5/22 17:54
 * @Version 1.0
 **/
@Data
public class AppOrderInfoRequest {

    /**
     * 商户ID
     */
    private String uid;

    /**
     *  内容
     */
    private String body;

    /**
     * 主题
     */
    private String subject;

    /**
     * 订单号
     */
    private String outTradeNo;

    /**
     * 超时时间
     */
    private String timeoutExpress;

    /**
     * 金额
     */
    private String totalAmount;

    /**
     * 商品编号
     */
    private String productCode;
}
