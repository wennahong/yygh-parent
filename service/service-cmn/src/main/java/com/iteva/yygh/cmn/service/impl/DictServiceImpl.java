package com.iteva.yygh.cmn.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iteva.yygh.cmn.listener.DictListener;
import com.iteva.yygh.cmn.mapper.DictMapper;
import com.iteva.yygh.cmn.service.DictService;
import com.iteva.yygh.model.cmn.Dict;
import com.iteva.yygh.vo.cmn.DictEeVo;
import org.apache.commons.lang.text.StrBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict>
        implements DictService {

    @Override
    public void exportDictData(HttpServletResponse response) {
        //设置下载信息
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = "dict";
        response.setHeader("Content-disposition", "attachment;filename="+ fileName + ".xlsx");
        //查询数据库
        List<Dict> dictList = baseMapper.selectList(null);
        //Dict -- DictEeVo
        List<DictEeVo> dictVoList = new ArrayList<>();
        for(Dict dict:dictList) {
            DictEeVo dictEeVo = new DictEeVo();
            // dictEeVo.setId(dict.getId());
            BeanUtils.copyProperties(dict,dictEeVo);
            dictVoList.add(dictEeVo);
        }
        //调用方法进行写操作
        try {
            EasyExcel.write(response.getOutputStream(), DictEeVo.class).sheet("dict")
                    .doWrite(dictVoList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @CacheEvict(value = "dict", allEntries = true)
    public void importDictData(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), DictEeVo.class, new DictListener(this.baseMapper))
                    .sheet()
                    .doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getDictName(String dictCode, String value) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isEmpty(dictCode)) {
            queryWrapper.eq("value", value);
            Dict dict = this.getOne(queryWrapper);
            return dict.getName();
        } else {
            queryWrapper.eq("dict_code", dictCode);
            Dict dict = this.getOne(queryWrapper);
            Long id = dict.getId();
            return this.query()
                    .eq("parent_id", id)
                    .eq("value", value)
                    .one().getName();
        }
    }

    @Override
    public List<Dict> findByDictCode(String dictCode) {
        Long id = this.query().eq("dict_code", dictCode).one().getId();
        return this.findChildrenData(id);
    }

    @Override
    @Cacheable(value = "dict", keyGenerator = "keyGenerator")
    public List<Dict> findChildrenData(Long id) {
        List<Dict> list = this.query().eq("parent_id", id).list();
        for (Dict dict : list) {
            Long dictId = dict.getId();
            boolean flag = this.hasChildren(dictId);
            dict.setHasChildren(flag);
        }
        return list;
    }

    private boolean hasChildren(Long id) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", id);
        int count = this.count(queryWrapper);
        return count > 0;
    }
}
