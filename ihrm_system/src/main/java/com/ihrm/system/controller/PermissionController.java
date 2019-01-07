package com.ihrm.system.controller;

import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.domain.system.Permission;
import com.ihrm.system.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p></p>
 *
 * @author xiaodongsun
 * @date 2019/01/07
 */
@RestController
@CrossOrigin
@RequestMapping("/sys")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @PostMapping("/permission")
    public Result save(@RequestBody Map<String, Object> map) throws Exception {
        permissionService.save(map);
        return new Result(ResultCode.SUCCESS);
    }

    @GetMapping("/permission/{id}")
    public Result findById(@PathVariable(name = "id") String id){
        Map map = permissionService.findById(id);
        return new Result(ResultCode.SUCCESS, map);
    }

    @GetMapping("/permission")
    public Result findAll(@RequestParam Map map){
        List<Permission> list = permissionService.findAll(map);
        return new Result(ResultCode.SUCCESS, list);
    }

    @PutMapping("/permission/{id}")
    public Result update(@PathVariable(name = "id")String id, @RequestBody Map<String, Object> map) throws Exception {
        map.put("id", id);
        permissionService.update(map);
        return new Result(ResultCode.SUCCESS);
    }

    @DeleteMapping("/permission/{id}")
    public Result deleteById(@PathVariable(name = "id")String id){
        permissionService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }
}
