package com.iteva.yygh.user.service;

import com.iteva.yygh.vo.user.LoginVo;

import java.util.Map;

public interface UserInfoService {

    Map<String, Object> loginUser(LoginVo loginVo);
}
