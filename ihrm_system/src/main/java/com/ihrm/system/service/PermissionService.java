package com.ihrm.system.service;

import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.exception.CommonException;
import com.ihrm.common.utils.BeanMapUtils;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.common.utils.PermissionConstants;
import com.ihrm.domain.system.Permission;
import com.ihrm.domain.system.PermissionApi;
import com.ihrm.domain.system.PermissionMenu;
import com.ihrm.domain.system.PermissionPoint;
import com.ihrm.system.dao.PermissionApiDao;
import com.ihrm.system.dao.PermissionDao;
import com.ihrm.system.dao.PermissionMenuDao;
import com.ihrm.system.dao.PermissionPointDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>permission service</p>
 *
 * @author xiaodongsun
 * @date 2019/01/07
 */
@Service
@Transactional
public class PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private PermissionMenuDao permissionMenuDao;

    @Autowired
    private PermissionPointDao permissionPointDao;

    @Autowired
    private PermissionApiDao permissionApiDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 保存用户
     */
    public void save(Map<String, Object> map) throws Exception {
        String id = idWorker.nextId() + "";
        //通过map构造permission对象
        Permission permission = BeanMapUtils.mapToBean(map, Permission.class);
        permission.setId(id);
        //根据不同类型构造不同的资源对象(菜单，按钮，api)
        int type = permission.getType();
        switch (type){
            case PermissionConstants.PY_MENU:
                PermissionMenu menu = BeanMapUtils.mapToBean(map, PermissionMenu.class);
                menu.setId(id);
                permissionMenuDao.save(menu);
                break;
            case PermissionConstants.PY_POINT:
                PermissionPoint point = BeanMapUtils.mapToBean(map, PermissionPoint.class);
                point.setId(id);
                permissionPointDao.save(point);
                break;
            case PermissionConstants.PY_API:
                PermissionApi api = BeanMapUtils.mapToBean(map, PermissionApi.class);
                api.setId(id);
                permissionApiDao.save(api);
                break;
            default:
                throw new CommonException(ResultCode.FAIL);
        }
        permissionDao.save(permission);
    }

    /**
     * 更新用户
     */
    public void update(Map<String, Object> map) throws Exception {
        Permission perm = BeanMapUtils.mapToBean(map, Permission.class);
        //通过传递的权限id查询权限
        Permission permission = permissionDao.findById(perm.getId()).get();
        permission.setDescription(perm.getDescription());
        permission.setName(perm.getName());
        permission.setEnVisible(perm.getEnVisible());
        permission.setCode(perm.getCode());
        //根据类型构造不同的资源
        int type = permission.getType();
        switch (type){
            case PermissionConstants.PY_MENU:
                PermissionMenu menu = BeanMapUtils.mapToBean(map, PermissionMenu.class);
                menu.setId(permission.getId());
                permissionMenuDao.save(menu);
                break;
            case PermissionConstants.PY_POINT:
                PermissionPoint point = BeanMapUtils.mapToBean(map, PermissionPoint.class);
                point.setId(permission.getId());
                permissionPointDao.save(point);
                break;
            case PermissionConstants.PY_API:
                PermissionApi api = BeanMapUtils.mapToBean(map, PermissionApi.class);
                api.setId(permission.getId());
                permissionApiDao.save(api);
                break;
            default:
                throw new CommonException(ResultCode.FAIL);
        }
        //查询不同的资源设置修改的属性
        permissionDao.save(permission);
    }

    /**
     * 根据id查询
     */
    public Map findById(String id){
        Permission permission = permissionDao.findById(id).get();
        int type = permission.getType();
        Object obj = null;
        if (type == PermissionConstants.PY_MENU){
            obj = permissionMenuDao.findById(id).get();
        }else if (type == PermissionConstants.PY_POINT){
            obj = permissionPointDao.findById(id).get();
        }else if (type == PermissionConstants.PY_API){
            obj = permissionApiDao.findById(id).get();
        }else {
            throw new CommonException(ResultCode.FAIL);
        }
        Map<String, Object> map = BeanMapUtils.beanToMap(obj);
        map.put("name", permission.getName());
        map.put("type", permission.getType());
        map.put("code", permission.getCode());
        map.put("description", permission.getDescription());
        map.put("pid", permission.getPid());
        map.put("enVisible", permission.getEnVisible());
        return map;
    }

    /**
     * 查询列表
     */
    public List<Permission> findAll(Map<String, Object> map){
        //查询条件
        Specification<Permission> spec = new Specification<Permission>() {
            /**
             * 动态拼接查询条件
             * @param root
             * @param criteriaQuery
             * @param criteriaBuilder
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                //根据父id查询
                if (!StringUtils.isEmpty(map.get("pid"))){
                    list.add(criteriaBuilder.equal(root.get("pid").as(String.class), (String)map.get("pid")));
                }
                //根据父enVisible查询
                if (!StringUtils.isEmpty(map.get("enVisible"))){
                    list.add(criteriaBuilder.equal(root.get("enVisible").as(String.class), (String)map.get("enVisible")));
                }
                String type = (String) map.get("type");
                //根据父type查询
                if (!StringUtils.isEmpty(type)){
                    CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("type"));
                    if ("0".equals(type)){
                        in.value(1).value(2);
                    }else {
                        in.value(Integer.parseInt(type));
                    }
                }
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        return permissionDao.findAll(spec);
    }

    public void deleteById(String id){
        //通过传递的权限id查询权限
        Permission permission = permissionDao.findById(id).get();
        permissionDao.delete(permission);
        //根据类型构造不同的资源
        int type = permission.getType();
        switch (type){
            case PermissionConstants.PY_MENU:
                permissionMenuDao.deleteById(id);
                break;
            case PermissionConstants.PY_POINT:
                permissionPointDao.deleteById(id);
                break;
            case PermissionConstants.PY_API:
                permissionApiDao.deleteById(id);
                break;
            default:
                throw new CommonException(ResultCode.FAIL);
        }

    }
}
