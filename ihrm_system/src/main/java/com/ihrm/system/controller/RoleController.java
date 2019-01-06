package com.ihrm.system.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.domain.system.Role;
import com.ihrm.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * <p>role controller</p>
 *
 * @author xiaodongsun
 * @date 2019/1/6
 */
@CrossOrigin
@RestController
@RequestMapping("/sys")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/role")
    public Result add(@RequestBody Role role){
        role.setCompanyId(companyId);
        roleService.save(role);
        return new Result(ResultCode.SUCCESS);
    }

    @PutMapping("/role/{id}")
    public Result update(@PathVariable(name = "id")String id, @RequestBody Role role){
        role.setId(id);
        roleService.update(role);
        return new Result(ResultCode.SUCCESS);
    }

    @DeleteMapping("/role/{id}")
    public Result delete(@PathVariable(name = "id")String id){
        roleService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

    @GetMapping("/role/{id}")
    public Result findById(@PathVariable(name = "id")String id){
        Role role = roleService.findById(id);
        return new Result(ResultCode.SUCCESS, role);
    }

    @GetMapping("/role")
    public Result findByPage(int page, int size, Role role){
        Page<Role> rolePage = roleService.findSearch(companyId, page, size);
        PageResult<Role> pageResult = new PageResult<Role>(rolePage.getTotalElements(), rolePage.getContent());
        return new Result(ResultCode.SUCCESS, pageResult);
    }

}