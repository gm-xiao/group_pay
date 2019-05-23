package com.example.grouppay.util.wechat;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName WeChatOrderResponse
 * @Description TODO
 * @Author gm
 * @Date 2019/5/23 10:34
 * @Version 1.0
 **/
@Data
public class WeChatOrderResponse {

    private String code;

    private ResponseBody data;

    private String msg;

    @Data
    public static class ResponseBody{

        private String appId;

        private String mchId;

        private String timeStamp;

        private String nonceStr;

        private String prepayId;

        private String signType;

        private String paySign;
    }

}
