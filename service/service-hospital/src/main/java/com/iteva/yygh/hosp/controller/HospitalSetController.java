package com.iteva.yygh.hosp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iteva.yygh.common.result.Result;
import com.iteva.yygh.common.utils.MD5;
import com.iteva.yygh.hosp.service.HospitalSetService;
import com.iteva.yygh.model.hosp.HospitalSet;
import com.iteva.yygh.vo.hosp.HospitalSetQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@Api(tags = "医院设置管理")
@CrossOrigin // 跨域
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
public class HospitalSetController {

    @Autowired
    private HospitalSetService hospitalSetService;

    @ApiOperation("获取所有医院设置")
    @GetMapping("/findAll")
    public Result findAllHospitalSet() {
        List<HospitalSet> list = hospitalSetService.list();
        return Result.ok(list);
    }

    @ApiOperation("根据id逻辑删除医院设置")
    @DeleteMapping("/{id}")
    public Result removeHospitalSetById(@PathVariable Long id) {
        boolean flag = hospitalSetService.removeById(id);
        if (flag) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @ApiOperation("分页查询医院设置")
    @PostMapping("/findPageHospSet/{current}/{limit}")
    public Result findPageHospSet(@PathVariable long current,
                                  @PathVariable long limit,
                                  @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo) {

        Page<HospitalSet> page = new Page<>(current, limit);
        // 条件查询
        QueryWrapper<HospitalSet> queryWrapper = new QueryWrapper<>();
        String hosname = hospitalSetQueryVo.getHosname();
        String hoscode = hospitalSetQueryVo.getHoscode();
        queryWrapper.like(StringUtils.isNotBlank(hosname), "hosname", hosname);
        queryWrapper.eq(StringUtils.isNotBlank(hoscode), "hoscode", hoscode);
        Page<HospitalSet> hospitalSetPage = hospitalSetService.page(page, queryWrapper);
        return Result.ok(hospitalSetPage);
    }

    @ApiOperation("新增医院设置")
    @PostMapping("/saveHospitalSet")
    public Result saveHospitalSet(@RequestBody HospitalSet hospitalSet) {
        hospitalSet.setStatus(1);
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis() + "" + random.nextInt(1000)));
        boolean save = hospitalSetService.save(hospitalSet);
        if (save) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @ApiOperation("根据id获取医院设置")
    @GetMapping("/getHospSet/{id}")
    public Result getHospSetById(@PathVariable Long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.ok(hospitalSet);
    }

    @ApiOperation("修改医院设置")
    @PostMapping("/updateHospitalSet")
    public Result updateHospitalSet(@RequestBody HospitalSet hospitalSet) {
        boolean flag = hospitalSetService.updateById(hospitalSet);
        if (flag) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @ApiOperation("批量删除医院设置")
    @DeleteMapping("/batchRemove")
    public Result removeBatchHospitalSet(@RequestBody List<Long> idList) {
        boolean flag = hospitalSetService.removeByIds(idList);
        if (flag) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @ApiOperation("修改医院设置状态")
    @PutMapping("/lockHospitalSet/{id}/{status}")
    public Result lockHospitalSet(@PathVariable Long id, @PathVariable Integer status) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        hospitalSet.setStatus(status);
        hospitalSetService.updateById(hospitalSet);
        return Result.ok();
    }

    @ApiOperation("发送签名密钥")
    @PutMapping("/sendKey/{id}")
    public Result sendKey(@PathVariable Long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        String signKey = hospitalSet.getSignKey();
        String hoscode = hospitalSet.getHoscode();
        // TODO 发送短信
        return Result.ok();
    }
}
