package com.iteva.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.iteva.yygh.cmn.client.DictFeignClient;
import com.iteva.yygh.hosp.repository.HospitalRepository;
import com.iteva.yygh.hosp.service.HospitalService;
import com.iteva.yygh.model.hosp.Hospital;
import com.iteva.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private DictFeignClient dictFeignClient;

    @Override
    public void save(Map<String, Object> parameterMap) {
        String s = JSONObject.toJSONString(parameterMap);
        Hospital hospital = JSONObject.parseObject(s, Hospital.class);
        Hospital hospitalExist = hospitalRepository.getHospitalByHoscode(hospital.getHoscode());
        if (hospitalExist != null) {
            hospital.setStatus(hospitalExist.getStatus());
            hospital.setCreateTime(hospitalExist.getCreateTime());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospitalRepository.save(hospital);
        } else {
            hospital.setStatus(0);
            hospital.setCreateTime(new Date());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospitalRepository.save(hospital);
        }
    }

    @Override
    public Hospital getByHoscode(String hoscode) {
        Hospital hospital = hospitalRepository.getHospitalByHoscode(hoscode);
        return hospital;
    }

    @Override
    public Page<Hospital> selectHospPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);
        Hospital hospital = new Hospital();
        BeanUtils.copyProperties(hospitalQueryVo, hospital);
        Example<Hospital> example = Example.of(hospital, exampleMatcher);
        Page<Hospital> hospitalPage = hospitalRepository.findAll(example, pageable);
        hospitalPage.getContent().forEach(this::setHospitalHosType);
        return hospitalPage;
    }

    @Override
    public void updateStatus(String id, Integer status) {
        Hospital hospital = hospitalRepository.findById(id).get();
        hospital.setStatus(status);
        hospital.setUpdateTime(new Date());
        hospitalRepository.save(hospital);
    }

    @Override
    public Map<String, Object> getHospById(String id) {
        Map<String, Object> map = new HashMap<>();
        Hospital hospital = hospitalRepository.findById(id).get();
        this.setHospitalHosType(hospital);
        map.put("hospital", hospital);
        map.put("bookingRule", hospital.getBookingRule());
        hospital.setBookingRule(null);
        return map;
    }

    @Override
    public String getHospName(String hoscode) {
        Hospital hospital = hospitalRepository.getHospitalByHoscode(hoscode);
        if (hospital != null) {
            return hospital.getHosname();
        }
        return null;
    }

    @Override
    public List<Hospital> findByHosName(String hosname) {
        return hospitalRepository.findHospitalByHosnameLike(hosname);
    }

    @Override
    public Map<String, Object> item(String hoscode) {
        Map<String, Object> map = new HashMap<>();
        Hospital hospital = this.getByHoscode(hoscode);
        this.setHospitalHosType(hospital);
        map.put("hospital", hospital);
        map.put("bookingRule", hospital.getBookingRule());
        hospital.setBookingRule(null);
        return map;
    }

    private void setHospitalHosType(Hospital hospital) {
        String hostypeString = dictFeignClient.getNameByDictCodeAndValue("Hostype", hospital.getHostype());
        String province = dictFeignClient.getNameByValue(hospital.getProvinceCode());
        String city = dictFeignClient.getNameByValue(hospital.getCityCode());
        String district = dictFeignClient.getNameByValue(hospital.getDistrictCode());
        hospital.getParam().put("fullAddress", province + city + district);
        hospital.getParam().put("hostypeString", hostypeString);
    }
}
