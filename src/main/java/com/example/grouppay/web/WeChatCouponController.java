package com.example.grouppay.web;

import com.example.grouppay.domain.WeChatInfo;
import com.example.grouppay.service.WeChatInfoService;
import com.example.grouppay.util.ResponseBo;
import com.example.grouppay.util.wechat.PayCommonUtil;
import com.example.grouppay.util.wechat.WeChatCouponReceiveRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @ClassName WeChatCouponController
 * @Description TODO 微信代金券相关接口
 * @Author gm
 * @Date 2019/5/27 10:26
 * @Version 1.0
 **/
@RestController
@RequestMapping(value = "/wechat")
@Api(value = "微信代金券", tags = {"微信代金券"})
public class WeChatCouponController {

    @Autowired
    private WeChatInfoService weChatInfoService;

    @ApiOperation(value = "领取微信代金券")
    @RequestMapping(value = "/coupon/receive")
    public ResponseBo<?> receive(WeChatCouponReceiveRequest receiveRequest){

        if (StringUtils.isBlank(receiveRequest.getUid()) || StringUtils.isBlank(receiveRequest.getOpenId())){
            return ResponseBo.parameterWrong(false);
        }

        // 1.获取请求参数
        WeChatInfo weChatInfo = weChatInfoService.findByUid(receiveRequest.getUid());
        if (null == weChatInfo){
            return ResponseBo.parameterWrong(false);
        }

        // 2.构造请求体
        SortedMap<String, Object> parameter = new TreeMap<String, Object>();
        parameter.put("appid", weChatInfo.getAppId());
        parameter.put("coupon_stock_id", 0);
        parameter.put("mch_id", weChatInfo.getMchId());
        parameter.put("nonce_str", PayCommonUtil.getRandomString(32));
        parameter.put("openid", receiveRequest.getOpenId());
        parameter.put("openid_count", 1);
        parameter.put("partner_trade_no", receiveRequest.getTradeNo());
        String sign = PayCommonUtil.createSign("UTF-8", parameter);
        parameter.put("sign", sign);
        String requestXML = PayCommonUtil.getRequestXml(parameter);

        Map<String, String> map = null;
        // 3.请求接口
        try {
            String result = PayCommonUtil.PKCSRequest(
                    "https://api.mch.weixin.qq.com/mmpaymkttransfers/send_coupon",
                    requestXML,"D:/12346.pem");
            map = PayCommonUtil.doXMLParse(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseBo.ok(map);
    }


}
