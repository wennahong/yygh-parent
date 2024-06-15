package com.iteva.yygh.hosp.interceptor;

import com.iteva.yygh.common.exception.YyghException;
import com.iteva.yygh.common.helper.HttpRequestHelper;
import com.iteva.yygh.common.result.ResultCodeEnum;
import com.iteva.yygh.common.utils.MD5;
import com.iteva.yygh.hosp.service.HospitalSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class ValidateSignKeyInterceptor implements HandlerInterceptor {

    @Autowired
    private HospitalSetService hospitalSetService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
        String sign = (String) paramMap.get("sign");
        String hoscode = (String) paramMap.get("hoscode");

        String signKey = hospitalSetService.getSignKey(hoscode);
        String signKeyMD5 = MD5.encrypt(signKey);
        if (!sign.equals(signKeyMD5)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
        return true;
    }
}
