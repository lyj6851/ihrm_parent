package com.ihrm.company.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.company.service.CompanyService;
import com.ihrm.company.service.DepartmentService;
import com.ihrm.domain.company.Company;
import com.ihrm.domain.company.Department;
import com.ihrm.domain.company.response.DeptListResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *     1.解决跨域
 *     2.声明RestController
 *     3.设置父路径
 * </p>
 *
 * @author xiaodongsun
 * @date 2019/01/03
 */
@CrossOrigin
@RestController
@RequestMapping("/company")
public class DepartmentController extends BaseController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CompanyService companyService;

    /**
     * 保存
     */
    @PostMapping("/department")
    public Result save(@RequestBody Department department){
        //企业id
        department.setCompanyId(companyId);
        departmentService.save(department);
        return new Result(ResultCode.SUCCESS);
    }

    @GetMapping("/department")
    public Result findAll(){
        Company company = companyService.findById(companyId);
        List<Department> depts = departmentService.findAll(companyId);

        DeptListResult data = new DeptListResult(company, depts);
        return new Result(ResultCode.SUCCESS, data);
    }

    @GetMapping("/department/{departmentId}")
    public Result findById(@PathVariable(value = "departmentId") String departmentId){
        Department department = departmentService.findById(departmentId);
        return new Result(ResultCode.SUCCESS, department);
    }

    @PutMapping("/department/{departmentId}")
    public Result update(@PathVariable(value = "departmentId") String departmentId, @RequestBody Department department) {
        department.setId(departmentId);
        departmentService.update(department);
        return new Result(ResultCode.SUCCESS);
    }

    @DeleteMapping("/department/{departmentId}")
    public Result delete(@PathVariable(value = "departmentId") String departmentId){
        departmentService.deleteById(departmentId);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     *
     */
    @GetMapping("/department/search")
    public Department findByCode(@RequestParam(value = "code") String code, @RequestParam(value = "companyId")String companyId){
        return departmentService.findByCode(code, companyId);
    }

}
