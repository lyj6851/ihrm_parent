package com.ihrm.system.service;

import com.ihrm.common.service.BaseService;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.common.utils.PermissionConstants;
import com.ihrm.domain.system.Permission;
import com.ihrm.domain.system.Role;
import com.ihrm.system.dao.PermissionDao;
import com.ihrm.system.dao.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>role service</p>
 *
 * @author xiaodongsun
 * @date 2019/1/6
 */
@Service
public class RoleService extends BaseService<Role> {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private IdWorker idWorker;

    public void save(Role role){
        role.setId(idWorker.nextId() + "");
        roleDao.save(role);
    }

    public void update(Role role){
        Role target = roleDao.findById(role.getId()).get();
        target.setDescription(role.getDescription());
        target.setName(role.getName());
        roleDao.save(target);
    }

    public Role findById(String id){
        return roleDao.findById(id).get();
    }

    public void deleteById(String id){
        roleDao.deleteById(id);
    }

    public List<Role> findAll(String companyId) {
        return roleDao.findAll(super.getSpect(companyId));
    }

    public Page<Role> findSearch(String companyId, int page, int size){
        return roleDao.findAll(super.getSpect(companyId), PageRequest.of(page-1, size));
    }

    /**
     * 分配权限
     */
    public void assignPerms(String roleId, List<String> permIds) {
        Role role = roleDao.findById(roleId).get();
        Set<Permission> perms = new HashSet<Permission>();
        for(String permId:permIds){
            Permission permission = permissionDao.findById(permId).get();
            //需要根据父id和类型查询权限列表
            List<Permission> apiList = permissionDao.findByTypeAndPid(PermissionConstants.PY_API, permission.getPid());
            //自动赋予API权限
            perms.addAll(apiList);
            //当前菜单或按钮的权限
            perms.add(permission);
        }
        //设置角色和权限的关系
        role.setPermissions(perms);
        roleDao.save(role);
    }
}
