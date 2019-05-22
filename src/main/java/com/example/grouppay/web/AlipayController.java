package com.example.grouppay.web;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.SignTask;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.grouppay.domain.AlipayInfo;
import com.example.grouppay.service.AlipayInfoService;
import com.example.grouppay.util.AppOrderInfoRequest;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.alipay.api.AlipayConstants.APP_ID;

/**
 * @ClassName AlipayController
 * @Description TODO
 * @Author gm
 * @Date 2019/5/22 11:20
 * @Version 1.0
 **/
@RestController
@RequestMapping("/alipay")
public class AlipayController {

    @Autowired
    private AlipayInfoService alipayInfoService;

    @GetMapping("/app/orderInfo")
    public String appOrderInfo(AppOrderInfoRequest appOrderInfoRequest){

        String orderInfo = "";

        QueryWrapper<AlipayInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", appOrderInfoRequest.getUid());
        AlipayInfo alipayInfo = alipayInfoService.getOne(queryWrapper);

        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do", alipayInfo.getAppId(), alipayInfo.getPrivateKey(), "json", "UTF-8", alipayInfo.getPublicKey(), "RSA2");
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(appOrderInfoRequest.getBody());
        model.setSubject(appOrderInfoRequest.getSubject());
        model.setOutTradeNo(appOrderInfoRequest.getOutTradeNo());
        model.setTimeoutExpress(appOrderInfoRequest.getTimeoutExpress());
        model.setTotalAmount(appOrderInfoRequest.getTotalAmount());
        model.setProductCode(appOrderInfoRequest.getProductCode());
        request.setBizModel(model);
        request.setNotifyUrl(alipayInfo.getNotifyUrl());
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
            orderInfo = response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return orderInfo ;
    }

}
