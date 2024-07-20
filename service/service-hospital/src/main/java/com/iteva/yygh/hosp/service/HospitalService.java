package com.iteva.yygh.hosp.service;

import com.iteva.yygh.model.hosp.Hospital;
import com.iteva.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface HospitalService {

    void save(Map<String, Object> parameterMap);

    Hospital getByHoscode(String hoscode);

    Page<Hospital> selectHospPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo);

    void updateStatus(String id, Integer status);

    Map<String, Object> getHospById(String id);

    String getHospName(String hoscode);

    List<Hospital> findByHosName(String hosname);

    Map<String, Object> item(String hoscode);
}
