package com.example.grouppay.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.grouppay.domain.User;
import com.example.grouppay.domain.WeChatInfo;
import com.example.grouppay.service.OrderService;
import com.example.grouppay.service.UserService;
import com.example.grouppay.service.WeChatInfoService;
import com.example.grouppay.util.wechat.*;
import lombok.extern.slf4j.Slf4j;
import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * @ClassName WeChatPayController
 * @Description TODO
 * @Author gm
 * @Date 2019/5/23 9:56
 * @Version 1.0
 **/
@Slf4j
@RestController
@RequestMapping("/wechat")
public class WeChatPayController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private WeChatInfoService weChatInfoService;

    @RequestMapping(value = "/app/pay", produces = MediaType.APPLICATION_JSON_VALUE)
    public WeChatOrderResponse pay(WeChatOrderRequest weChatOrderRequest, HttpServletRequest request){

        // 1.获取微信配置信息
        QueryWrapper<WeChatInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", weChatOrderRequest.getUid());
        WeChatInfo weChatInfo = weChatInfoService.getOne(queryWrapper);

        // 2.构造参数请求统一下单接口
        Map<String, String> map = weixinPrePay(weChatOrderRequest, weChatInfo, request);

        // 3.获取下单结果返回
        WeChatOrderResponse response = new WeChatOrderResponse();
        WeChatOrderResponse.ResponseBody responseBody = new WeChatOrderResponse.ResponseBody();
        log.info("WeChatPayController pay result = {}", map);
        if ("SUCCESS".equals(map.get("return_code"))){
            responseBody.setAppId(map.get("appid"));
            responseBody.setMchId(map.get("mch_id"));
            responseBody.setNonceStr(map.get("nonce_str"));
            responseBody.setPaySign(map.get("sign"));
            responseBody.setPrepayId(map.get("prepay_id"));
            responseBody.setSignType("MD5");
            Long time = (System.currentTimeMillis() / 1000);
            responseBody.setTimeStamp(time.toString());
            response.setCode("200");
            response.setData(responseBody);
            response.setMsg("微信加载成功");
        } else {
            response.setCode("500");
            response.setMsg(map.get("return_msg"));
        }
        return response;
    }

    /**
     * 统一下单
     * @param weChatOrderRequest
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String, String> weixinPrePay(WeChatOrderRequest weChatOrderRequest,  WeChatInfo weChatInfo, HttpServletRequest request) {

        String randomString = PayCommonUtil.getRandomString(32);

        SortedMap<String, Object> parameterMap = new TreeMap<String, Object>();
        parameterMap.put("appid", weChatInfo.getAppId());
        parameterMap.put("mch_id", weChatInfo.getMchId());
        parameterMap.put("nonce_str", randomString);
        parameterMap.put("body", weChatOrderRequest.getBody());
        parameterMap.put("goods_tag", weChatOrderRequest.getGoodsTag());
        parameterMap.put("out_trade_no", weChatOrderRequest.getOutTradeNo());
        parameterMap.put("fee_type", "CNY");
        BigDecimal total = weChatOrderRequest.getTotalFee().multiply(new BigDecimal(100));  //接口中参数支付金额单位为【分】，参数值不能带小数，所以乘以100
        java.text.DecimalFormat df=new java.text.DecimalFormat("0");
        parameterMap.put("total_fee", df.format(total));
        parameterMap.put("spbill_create_ip", PayCommonUtil.getRemoteHost(request));
        parameterMap.put("notify_url", "http://");
        parameterMap.put("trade_type", "APP");
        String sign = PayCommonUtil.createSign("UTF-8", parameterMap);
        parameterMap.put("sign", sign);
        String requestXML = PayCommonUtil.getRequestXml(parameterMap);
        System.out.println(requestXML);
        String result = PayCommonUtil.httpsRequest(
                "https://api.mch.weixin.qq.com/pay/unifiedorder", "POST",
                requestXML);
        System.out.println(result);
        Map<String, String> map = null;
        try {
            map = PayCommonUtil.doXMLParse(result);
        } catch (JDOMException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping(value = "/app/notify", produces = MediaType.APPLICATION_JSON_VALUE)
    public String notifyWeiXinPay(HttpServletRequest request, HttpServletResponse response) throws IOException, JDOMException {
        log.info("微信异步回调");
        InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        String resultxml = new String(outSteam.toByteArray(), "utf-8");
        Map<String, String> params = PayCommonUtil.doXMLParse(resultxml);
        outSteam.close();
        inStream.close();
        Map<String,String> return_data = new HashMap<String,String>();
        if (!PayCommonUtil.isTenpaySign(params)) {
            log.info("支付失败");
            return_data.put("return_code", "FAIL");
            return_data.put("return_msg", "return_code不正确");
            return StringUtil.GetMapToXML(return_data);
        } else {
            log.info("付款成功");

            String total_fee = params.get("total_fee");
            double v = Double.valueOf(total_fee) / 100;
            String out_trade_no = String.valueOf(Long.parseLong(params.get("out_trade_no").split("O")[0]));
            Date accountTime = DateUtil.stringtoDate(params.get("time_end"), "yyyyMMddHHmmss");
            String ordertime = DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
            String totalAmount = String.valueOf(v);
            String appId = params.get("appid");
            String tradeNo = params.get("transaction_id");

            return_data.put("return_code", "SUCCESS");
            return_data.put("return_msg", "OK");
            return StringUtil.GetMapToXML(return_data);
        }
    }

}
