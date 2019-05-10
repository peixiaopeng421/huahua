package com.an.sms;


import com.an.sms.send.SmsSendCode;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "sms")
public class SmsSend {

    @Value("${api.send.appCode}")
    public String appCode;


    /**
     * 发送短信
     * @param map
     */

    @RabbitHandler
    public void sendSms(Map<String,String> map){

        System.out.println("手机号;"+map.get("mobile"));
        System.out.println("验证码;"+map.get("code"));
        //System.out.println("模拟 +  验证码发送成功");

        SmsSendCode.sendCode(appCode,map.get("mobile"),map.get("code"));

    }
}