package com.ihrm.domain.system.response;

import com.ihrm.domain.system.Permission;
import com.ihrm.domain.system.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p></p>
 *
 * @author xiaodongsun
 * @date 2019/01/07
 */
@Getter
@Setter
public class RoleResult implements Serializable {
    private static final long serialVersionUID = -9119310421885412560L;

    private String id;
    /**
     * 角色名
     */
    private String name;
    /**
     * 说明
     */
    private String description;
    /**
     * 企业id
     */
    private String companyId;

    private List<String> permIds = new ArrayList<String>();

    public RoleResult(Role role){
        BeanUtils.copyProperties(role, this);
        for (Permission permission : role.getPermissions()) {
            this.permIds.add(permission.getId());
        }
    }
}
