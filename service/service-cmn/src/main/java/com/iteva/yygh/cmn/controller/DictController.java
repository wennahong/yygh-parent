package com.iteva.yygh.cmn.controller;

import com.iteva.yygh.cmn.service.DictService;
import com.iteva.yygh.common.result.Result;
import com.iteva.yygh.model.cmn.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api("数据字典接口")
@RestController
@RequestMapping("/admin/cmn/dict")
//@CrossOrigin
public class DictController {

    @Autowired
    private DictService dictService;

    @GetMapping("findByDictCode/{dictCode}")
    public Result findByDictCode(@PathVariable String dictCode) {
        List<Dict> list = dictService.findByDictCode(dictCode);
        return Result.ok(list);
    }

    @GetMapping("/getName/{dictCode}/{value}")
    public String getNameByDictCodeAndValue(@PathVariable String dictCode,
                                            @PathVariable String value) {
        return dictService.getDictName(dictCode, value);
    }

    @GetMapping("/getName/{value}")
    public String getNameByValue(@PathVariable String value) {
        return dictService.getDictName("", value);
    }

    @ApiOperation("根据数据id查询子数据列表")
    @GetMapping("/findChildrenData/{id}")
    public Result findChildrenData(@PathVariable Long id) {
        List<Dict> list = dictService.findChildrenData(id);
        return Result.ok(list);
    }

    @GetMapping("/exportData")
    public void exportDict(HttpServletResponse response) {
        dictService.exportDictData(response);
    }

    @PostMapping("/importData")
    public Result importDict(MultipartFile file) {
        dictService.importDictData(file);
        return Result.ok();
    }
}
