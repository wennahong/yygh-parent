package com.iteva.yygh.sms.controller;

import com.iteva.yygh.common.result.Result;
import com.iteva.yygh.sms.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sms")
public class SmsApiController {

    @Autowired
    private SmsService smsService;

    @GetMapping("/send/{phone}")
    public Result sendCode(@PathVariable String phone) {
        boolean isSend = smsService.send(phone);
        if (isSend) {
            return Result.ok();
        } else {
            return Result.fail().message("发送短信失败");
        }
    }
}
