package com.iteva.yygh.sms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.iteva.yygh.sms.service.SmsService;
import com.iteva.yygh.sms.utils.AliyunConstants;
import com.iteva.yygh.sms.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean send(String phone) {
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return true;
        }
        code = RandomUtil.getSixBitRandom();
        try {
            boolean isSuccess = sendSms(phone, code);
            if (isSuccess) {
                redisTemplate.opsForValue().set(phone, code, 2, TimeUnit.MINUTES);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean sendSms(String phone, String code) throws Exception {
        Client client = createClient();
        JSONObject jsonCode = new JSONObject();
        jsonCode.put("code", code);
        SendSmsRequest request = new SendSmsRequest()
                .setSignName("阿里云短信测试")
                .setTemplateCode("SMS_154950909")
                .setPhoneNumbers(phone)
                .setTemplateParam(jsonCode.toJSONString());
        SendSmsResponse response = client.sendSms(request);
        return "OK".equals(response.body.code);
    }

    private Client createClient() throws Exception {
        Config config = new Config()
                .setAccessKeyId(AliyunConstants.ACCESS_KEY_ID)
                .setAccessKeySecret(AliyunConstants.SECRET);

        config.endpoint = "dysmsapi.aliyuncs.com";
        return new Client(config);
    }
}
