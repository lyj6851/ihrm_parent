package com.ihrm.company.controller;

import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.company.service.CompanyService;
import com.ihrm.domain.company.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p></p>
 *
 * @author xiaodongsun
 * @date 2019/01/02
 */
@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    /**
     * 保存企业
     */
    @PostMapping("")
    public Result save(@RequestBody Company company){
        companyService.add(company);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据id更新企业
     */
    @PutMapping("/{id}")
    public Result update(@PathVariable(name = "id") String id, @RequestBody Company company){
        company.setId(id);
        companyService.update(company);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据id删除企业
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable(name = "id") String id){
        companyService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据id查询企业
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable(name = "id")String id){
        Company data = companyService.findById(id);
        Result result = new Result(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }

    /**
     * 查询全部企业列表
     */
    @GetMapping("")
    public Result findAll(){
        List<Company> data = companyService.findAll();
        Result result = new Result(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }
}
