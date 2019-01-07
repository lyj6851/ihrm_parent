package com.ihrm.system.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.utils.JwtUtils;
import com.ihrm.domain.system.User;
import com.ihrm.domain.system.response.UserResult;
import com.ihrm.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private JwtUtils jwtUtils;

    @PutMapping("/user/assignRoles")
    public Result save(@RequestBody Map<String, Object> map){
        String userId = (String) map.get("id");
        List<String> roleIds = (List<String>) map.get("roleIds");
        userService.assignRoles(userId, roleIds);
        return new Result(ResultCode.SUCCESS);
    }

    @PostMapping("/user")
    public Result save(@RequestBody User user){
        user.setCompanyId(companyId);
        user.setCompanyName(companyName);
        userService.save(user);
        return new Result(ResultCode.SUCCESS);
    }

    @GetMapping("/user/{id}")
    public Result findById(@PathVariable(name = "id") String id){
        //添加roleIds(用户已经具有的角色id数组)
        User user = userService.findById(id);
        UserResult userResult = new UserResult(user);
        return new Result(ResultCode.SUCCESS, userResult);
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

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result login(@RequestBody Map<String, String> loginMap){
        String mobile = loginMap.get("mobile");
        String password = loginMap.get("password");
        User user = userService.findByMobile(mobile);
        if (user == null || !user.getPassword().equals(password)){
            return new Result(ResultCode.MOBILEORPASSWORDERROR);
        }else{
            Map<String, Object> map = new HashMap<>();
            map.put("companyId", user.getCompanyId());
            map.put("companyName", user.getCompanyName());
            String token = jwtUtils.createJwt(user.getId(), user.getUsername(), map);
            return new Result(ResultCode.SUCCESS, token);
        }
    }
}
