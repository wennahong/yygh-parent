package com.iteva.yygh.hosp.controller;

import com.iteva.yygh.common.result.Result;
import com.iteva.yygh.hosp.service.HospitalService;
import com.iteva.yygh.model.hosp.Hospital;
import com.iteva.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/hosp/hospital")
//@CrossOrigin
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @GetMapping("/list/{page}/{limit}")
    public Result list(@PathVariable Integer page,
                       @PathVariable Integer limit,
                       HospitalQueryVo hospitalQueryVo) {

        Page<Hospital> pageObj = hospitalService.selectHospPage(page, limit, hospitalQueryVo);
        return Result.ok(pageObj);
    }

    @GetMapping("updateHospStatus/{id}/{status}")
    public Result updateHospStatus(@PathVariable String id, @PathVariable Integer status) {
        hospitalService.updateStatus(id, status);
        return Result.ok();
    }

    @GetMapping("showHospDetail/{id}")
    public Result showHospDetail(@PathVariable String id) {
        Map<String, Object> map = hospitalService.getHospById(id);
        return Result.ok(map);
    }
}
