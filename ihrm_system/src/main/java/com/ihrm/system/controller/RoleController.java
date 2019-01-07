package com.ihrm.system.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.domain.system.Role;
import com.ihrm.domain.system.response.RoleResult;
import com.ihrm.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PutMapping("/role/assignPrem")
    public Result save(@RequestBody Map<String, Object> map){
        String roleId = (String) map.get("id");
        List<String> permIds = (List<String>) map.get("permIds");
        roleService.assignPerms(roleId, permIds);
        return new Result(ResultCode.SUCCESS);
    }

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
        RoleResult roleResult = new RoleResult(role);
        return new Result(ResultCode.SUCCESS, roleResult);
    }

    /**
     * 分页查询角色
     */
    @RequestMapping(value = "/role", method = RequestMethod.GET)
    public Result findByPage(int page,int pagesize,Role role){
        Page<Role> searchPage = roleService.findSearch(companyId, page, pagesize);
        PageResult<Role> pr = new PageResult(searchPage.getTotalElements(),searchPage.getContent());
        return new Result(ResultCode.SUCCESS,pr);
    }

    @RequestMapping(value="/role/list" ,method=RequestMethod.GET)
    public Result findAll() {
        List<Role> roleList = roleService.findAll(companyId);
        return new Result(ResultCode.SUCCESS,roleList);
    }

}