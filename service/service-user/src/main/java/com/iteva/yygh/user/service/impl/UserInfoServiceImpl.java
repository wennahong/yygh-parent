package com.iteva.yygh.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iteva.yygh.common.exception.YyghException;
import com.iteva.yygh.common.helper.JwtHelper;
import com.iteva.yygh.common.result.ResultCodeEnum;
import com.iteva.yygh.model.user.UserInfo;
import com.iteva.yygh.user.UserServiceApplication;
import com.iteva.yygh.user.mapper.UserInfoMapper;
import com.iteva.yygh.user.service.UserInfoService;
import com.iteva.yygh.vo.user.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
        implements UserInfoService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public static final int USER_DISABLED = 0;
    public static final int USER_ENABLED = 1;

    @Override
    public Map<String, Object> loginUser(LoginVo loginVo) {

        String phone = loginVo.getPhone();
        String code = loginVo.getCode();
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)) {
            throw new YyghException(ResultCodeEnum.PARAM_ERROR);
        }

        // TODO 判断手机验证码和输入的验证码是否一致
        String cacheCode = redisTemplate.opsForValue().get(phone);
        if (cacheCode == null) {
            throw new YyghException(ResultCodeEnum.CODE_EXPIRED);
        }

        if (!code.equals(cacheCode)) {
            throw new YyghException(ResultCodeEnum.CODE_ERROR);
        }

        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        UserInfo userInfo = this.getOne(queryWrapper);
        if (userInfo == null) {
            userInfo = new UserInfo();
            userInfo.setName("");
            userInfo.setPhone(phone);
            userInfo.setStatus(USER_ENABLED);
            this.save(userInfo);
        }

        if (userInfo.getStatus() == USER_DISABLED) {
            throw new YyghException(ResultCodeEnum.LOGIN_DISABLED_ERROR);
        }

        Map<String, Object> map = new HashMap<>();
        String name = userInfo.getName();
        if (StringUtils.isEmpty(name)) {
            name = userInfo.getNickName();
        }
        if (StringUtils.isEmpty(name)) {
            name = userInfo.getPhone();
        }
        String token = JwtHelper.createToken(userInfo.getId(), name);
        map.put("name", name);
        // TODO 生成token
        map.put("token", token);
        return map;
    }
}
