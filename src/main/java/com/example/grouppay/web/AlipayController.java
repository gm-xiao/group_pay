package com.example.grouppay.web;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.grouppay.domain.AlipayInfo;
import com.example.grouppay.domain.Order;
import com.example.grouppay.service.AlipayInfoService;
import com.example.grouppay.service.OrderService;
import com.example.grouppay.util.IdWorker;
import com.example.grouppay.util.alipay.AppAlipayRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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

    @Autowired
    private OrderService orderService;

    @Autowired
    private IdWorker idWorker;

    @GetMapping("/app/orderInfo")
    public String appOrderInfo(AppAlipayRequest appOrderInfoRequest){

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
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl("http://");
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
            orderInfo = response.getBody();

            Order order = new Order();
            order.setOutTradeNo(appOrderInfoRequest.getOutTradeNo());
            order.setAccount(new BigDecimal(appOrderInfoRequest.getTotalAmount()));
            order.setUid(appOrderInfoRequest.getUid());
            order.setId(idWorker.create());
            orderService.save(order);

        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return orderInfo ;
    }

    @RequestMapping(value = "/app/notify")
    public void appNotify(HttpServletRequest request, HttpServletResponse response) throws AlipayApiException {
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        QueryWrapper<Order> orderWrapper = new QueryWrapper<>();
        orderWrapper.eq("out_trade_no", params.get("out_trade_no"));
        Order order = orderService.getOne(orderWrapper);

        QueryWrapper<AlipayInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", order.getUid());
        AlipayInfo alipayInfo = alipayInfoService.getOne(queryWrapper);

        //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        boolean flag = AlipaySignature.rsaCheckV1(params, alipayInfo.getPublicKey(), "UTF-8","RSA2");
        if (flag){



        }
    }

}
