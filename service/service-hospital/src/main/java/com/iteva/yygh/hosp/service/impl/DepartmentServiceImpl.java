package com.iteva.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.iteva.yygh.hosp.repository.DepartmentRepository;
import com.iteva.yygh.hosp.service.DepartmentService;
import com.iteva.yygh.model.hosp.Department;
import com.iteva.yygh.vo.hosp.DepartmentQueryVo;
import com.iteva.yygh.vo.hosp.DepartmentVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;


    @Override
    public void save(Map<String, Object> paramMap) {
        String jsonString = JSONObject.toJSONString(paramMap);
        Department department = JSONObject.parseObject(jsonString, Department.class);
        Department departmentExist = departmentRepository.
                getDepartmentByHoscodeAndDepcode(department.getHoscode(), department.getDepcode());
        // TODO 可能要修改一下
        if (departmentExist != null) {
            departmentExist.setUpdateTime(new Date());
            departmentExist.setIsDeleted(0);
            departmentRepository.save(departmentExist);
        } else {
            department.setCreateTime(new Date());
            department.setUpdateTime(new Date());
            department.setIsDeleted(0);
            departmentRepository.save(department);
        }

    }

    @Override
    public Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Department department = new Department();
        department.setIsDeleted(0);
        BeanUtils.copyProperties(departmentQueryVo, department);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);
        Example<Department> example = Example.of(department, matcher);
        Page<Department> departmentPage = departmentRepository.findAll(example, pageable);
        return departmentPage;
    }

    @Override
    public void remove(String hoscode, String depcode) {
        Department department = departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
        if (department != null) {
            departmentRepository.deleteById(department.getId());
        }
    }

    @Override
    public List<DepartmentVo> findDeptTree(String hoscode) {
        List<DepartmentVo> result = new ArrayList<>();
        Department department = new Department();
        Example<Department> example = Example.of(department);
        List<Department> departmentList = departmentRepository.findAll(example);
        Map<String, List<Department>> map =
                departmentList.stream().collect(Collectors.groupingBy(Department::getBigcode));

        for (Map.Entry<String, List<Department>> entry : map.entrySet()) {
            String bigcode = entry.getKey();
            List<Department> departments = entry.getValue();
            DepartmentVo departmentVo = new DepartmentVo();
            departmentVo.setDepcode(bigcode);
            departmentVo.setDepname(departments.get(0).getBigname());

            List<DepartmentVo> children = new ArrayList<>();
            for (Department dept : departments) {
                DepartmentVo deptVo = new DepartmentVo();
                deptVo.setDepcode(dept.getDepcode());
                deptVo.setDepname(dept.getDepname());
                children.add(deptVo);
            }
            departmentVo.setChildren(children);
            result.add(departmentVo);
        }

        return result;
    }

    @Override
    public String getDepName(String hoscode, String depcode) {
        Department department = departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
        if (department != null) {
            return department.getDepname();
        }
        return null;
    }
}
