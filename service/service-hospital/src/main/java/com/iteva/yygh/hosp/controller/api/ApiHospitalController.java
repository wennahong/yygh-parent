package com.iteva.yygh.hosp.controller.api;

import com.iteva.yygh.common.result.Result;
import com.iteva.yygh.hosp.service.DepartmentService;
import com.iteva.yygh.hosp.service.HospitalService;
import com.iteva.yygh.model.hosp.Hospital;
import com.iteva.yygh.vo.hosp.DepartmentVo;
import com.iteva.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/hospital")
public class ApiHospitalController {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/findHospList/{page}/{limit}")
    public Result listHospitalsByPage(@PathVariable Integer page,
                                      @PathVariable Integer limit,
                                      HospitalQueryVo hospitalQueryVo) {

        Page<Hospital> hospitals = hospitalService.selectHospPage(page, limit, hospitalQueryVo);
        return Result.ok(hospitals);
    }

    @GetMapping("/findByHosName/{hosname}")
    public Result searchHospitalsByHosName(@PathVariable String hosname) {
        List<Hospital> list = hospitalService.findByHosName(hosname);
        return Result.ok(list);
    }

    @GetMapping("/department/{hoscode}")
    public Result index(@PathVariable String hoscode) {
        List<DepartmentVo> list = departmentService.findDeptTree(hoscode);
        return Result.ok(list);
    }

    @GetMapping("/findHospDetail/{hoscode}")
    public Result item(@PathVariable String hoscode) {
        Map<String, Object> map = hospitalService.item(hoscode);
        return Result.ok(map);
    }
}
