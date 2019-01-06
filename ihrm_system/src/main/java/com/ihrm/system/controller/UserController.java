package com.ihrm.system.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.domain.system.User;
import com.ihrm.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>user controller</p>
 *
 * @author xiaodongsun
 * @date 2019/1/6
 */
@CrossOrigin
@RestController
@RequestMapping("/sys")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public Result save(@RequestBody User user){
        user.setCompanyId(companyId);
        user.setCompanyName(companyName);
        userService.save(user);
        return new Result(ResultCode.SUCCESS);
    }

    @GetMapping("/user/{id}")
    public Result findById(@PathVariable(name = "id") String id){
        User user = userService.findById(id);
        return new Result(ResultCode.SUCCESS, user);
    }

    @GetMapping("/user")
    public Result findAll(int page, int size, @RequestParam Map map){
        map.put("companyId", companyId);
        Page pageUser = userService.findAll(map, page, size);
        PageResult<User> pageResult = new PageResult<User>(pageUser.getTotalElements(), pageUser.getContent());
        return new Result(ResultCode.SUCCESS, pageResult);
    }

    @PutMapping("/user/{id}")
    public Result update(@PathVariable(name = "id")String id, @RequestBody User user){
        user.setId(id);
        userService.update(user);
        return new Result(ResultCode.SUCCESS);
    }

    @DeleteMapping("/user/{id}")
    public Result deleteById(@PathVariable(name = "id")String id){
        userService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }
}
