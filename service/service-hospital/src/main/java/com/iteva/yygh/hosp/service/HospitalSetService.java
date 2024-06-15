package com.iteva.yygh.hosp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iteva.yygh.model.hosp.HospitalSet;

public interface HospitalSetService extends IService<HospitalSet> {

    String getSignKey(String hoscode);
}
