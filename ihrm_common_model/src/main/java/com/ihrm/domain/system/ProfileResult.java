package com.ihrm.domain.system;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <p></p>
 *
 * @author xiaodongsun
 * @date 2019/01/07
 */
@Setter
@Getter
public class ProfileResult {
    private String mobile;
    private String username;
    private String company;
    private Map<String, Object> roles;

    public ProfileResult(User user) {
        this.mobile = user.getMobile();
        this.username = user.getUsername();
        this.company = user.getCompanyName();
        Set<Role> roles = user.getRoles();
        Set<String> menus = new HashSet<>();
        Set<String> points = new HashSet<>();
        Set<String> apis = new HashSet<>();
        for (Role role : roles) {
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                String code = permission.getCode();
                Integer type = permission.getType();
                if (type == 1){
                    menus.add(code);
                }else if (type == 2){
                    points.add(code);
                }else {
                    apis.add(code);
                }
            }
        }
        this.roles.put("menus", menus);
        this.roles.put("points", points);
        this.roles.put("apis", apis);
    }
}
