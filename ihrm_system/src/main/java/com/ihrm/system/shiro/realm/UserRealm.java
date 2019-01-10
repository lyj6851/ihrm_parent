package com.ihrm.system.shiro.realm;

import com.ihrm.common.shiro.realm.IhrmRealm;
import com.ihrm.domain.system.Permission;
import com.ihrm.domain.system.ProfileResult;
import com.ihrm.domain.system.User;
import com.ihrm.system.service.PermissionService;
import com.ihrm.system.service.UserService;
import org.apache.shiro.authc.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p></p>
 *
 * @author xiaodongsun
 * @date 2019/01/10
 */
public class UserRealm extends IhrmRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    /**
     * 认证方法
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取用户手机号和密码
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String mobile = usernamePasswordToken.getUsername();
        String password = new String(usernamePasswordToken.getPassword());
        //根据手机号查询用户
        User user = userService.findByMobile(mobile);
        //判断用户是否存在，用户密码是否和输入的密码一致
        if (user != null && password.equals(user.getPassword())){
            //构造安全数据返回(安全数据：user基本数据，权限信息 使用ProfileResult)
            ProfileResult result = null;
            if ("user".equals(user.getLevel())){
                result = new ProfileResult(user);
            }else{
                Map map = new HashMap();
                if ("coAdmin".equals(user.getLevel())){
                    map.put("enVisible", "1");
                }
                List<Permission> list = permissionService.findAll(map);
                result = new ProfileResult(user, list);
            }
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(result, password, getName());
            return authenticationInfo;
        }
        //抛出异常，表示用户名和密码不匹配
        return null;
    }
}
