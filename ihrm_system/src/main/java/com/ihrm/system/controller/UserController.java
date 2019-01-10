package com.ihrm.system.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.utils.JwtUtils;
import com.ihrm.domain.system.ProfileResult;
import com.ihrm.domain.system.User;
import com.ihrm.domain.system.response.UserResult;
import com.ihrm.system.service.PermissionService;
import com.ihrm.system.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    private PermissionService permissionService;

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

    @RequiresPermissions("API-USER-DELETE")
    @DeleteMapping(value = "/user/{id}", name = "API-USER-DELETE")
    public Result deleteById(@PathVariable(name = "id")String id){
        userService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 用户登录
     */
    /*@PostMapping("/login")
    public Result login(@RequestBody Map<String, String> loginMap){
        String mobile = loginMap.get("mobile");
        String password = loginMap.get("password");
        password = new Md5Hash(password, mobile, 3).toString();
        User user = userService.findByMobile(mobile);
        if (user == null || !user.getPassword().equals(password)){
            return new Result(ResultCode.MOBILEORPASSWORDERROR);
        }else{
            //api权限字符串
            StringBuilder sb = new StringBuilder();
            for (Role role : user.getRoles()) {
                for (Permission permission : role.getPermissions()) {
                    if (permission.getType() == PermissionConstants.PY_API) {
                        sb.append(permission.getCode()).append(",");
                    }
                }
            }
            Map<String, Object> map = new HashMap<>();
            map.put("apis", sb.toString().substring(0, sb.length()-1));
            map.put("companyId", user.getCompanyId());
            map.put("companyName", user.getCompanyName());
            String token = jwtUtils.createJwt(user.getId(), user.getUsername(), map);
            return new Result(ResultCode.SUCCESS, token);
        }
    }*/

    /**
     * 用户登录成功后，获取用户信息
     */
    /*@PostMapping("/profile")
    public Result profile(HttpServletRequest request){
        String userId = claims.getId();
        User user = userService.findById(userId);
        ProfileResult result = null;
        //根据不同的用户级别获取用户权限
        if ("user".equals(user.getLevel())){
            result = new ProfileResult(user);
        }else{
            Map map = new HashMap();
            if ("coAdmin".equals(user.getLevel())) {
                map.put("enVisible", 1);
            }
            List<Permission> list = permissionService.findAll(map);
            result = new ProfileResult(user, list);
        }

        return new Result(ResultCode.SUCCESS, result);
    }*/


    @PostMapping("/login")
    public Result login(@RequestBody Map<String, String> loginMap){
        try {
            String mobile = loginMap.get("mobile");
            String password = loginMap.get("password");
            //密码加密
            password = new Md5Hash(password, mobile, 3).toString();
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(mobile, password);
            Subject subject = SecurityUtils.getSubject();
            subject.login(usernamePasswordToken);
            String sessionId = subject.getSession().getId().toString();
            return new Result(ResultCode.SUCCESS, sessionId);
        } catch (AuthenticationException e) {
            return new Result(ResultCode.MOBILEORPASSWORDERROR);
        }
    }

    @PostMapping("/profile")
    public Result profile(HttpServletRequest request){
        Subject subject = SecurityUtils.getSubject();
        PrincipalCollection principals = subject.getPrincipals();
        ProfileResult result = (ProfileResult) principals.getPrimaryPrincipal();
        return new Result(ResultCode.SUCCESS, result);
    }

}
