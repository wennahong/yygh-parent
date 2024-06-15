package com.iteva.yygh.hosp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iteva.yygh.hosp.mapper.HospitalSetMapper;
import com.iteva.yygh.hosp.service.HospitalSetService;
import com.iteva.yygh.model.hosp.HospitalSet;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class HospitalSetServiceImpl extends ServiceImpl<HospitalSetMapper, HospitalSet>
        implements HospitalSetService {

    @Override
    public String getSignKey(String hoscode) {
        QueryWrapper<HospitalSet> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("hoscode", hoscode);
        HospitalSet hospitalSet = this.getOne(queryWrapper);
        return hospitalSet.getSignKey();
    }
}
