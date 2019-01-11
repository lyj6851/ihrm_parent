package com.ihrm.system.service;

import com.ihrm.common.utils.IdWorker;
import com.ihrm.domain.company.Department;
import com.ihrm.domain.system.Role;
import com.ihrm.domain.system.User;
import com.ihrm.system.client.DepartmentClient;
import com.ihrm.system.dao.RoleDao;
import com.ihrm.system.dao.UserDao;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * <p>user service</p>
 *
 * @author xiaodongsun
 * @date 2019/1/6
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    DepartmentClient departmentClient;

    /**
     * 保存用户
     */
    public void save(User user){
        String id = idWorker.nextId() + "";
        user.setId(id);
        String password = "123";
        //加密
        password = new Md5Hash(password, user.getMobile(), 3).toString();
        user.setPassword(password);
        user.setLevel("user");
        user.setEnableState(1);
        userDao.save(user);
    }

    /**
     * 更新用户
     */
    public void update(User user){
        User target = userDao.findById(user.getId()).get();
        target.setUsername(user.getUsername());
        target.setPassword(user.getPassword());
        target.setDepartmentName(user.getDepartmentName());
        target.setDepartmentId(user.getDepartmentId());
        userDao.save(target);
    }

    public User findById(String id){
        return userDao.findById(id).get();
    }

    public Page<User> findAll(Map<String, Object> map, int page, int size){
        //查询条件
        Specification<User> spec = new Specification<User>() {
            /**
             * 动态拼接查询条件
             * @param root
             * @param criteriaQuery
             * @param criteriaBuilder
             * @return
             */
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();

                if (!StringUtils.isEmpty(map.get("companyId"))){
                    list.add(criteriaBuilder.equal(root.get("companyId").as(String.class), (String)map.get("companyId")));
                }
                //根据请求的部门id查询
                if (!StringUtils.isEmpty(map.get("departmentId"))){
                    list.add(criteriaBuilder.equal(root.get("departmentId").as(String.class), (String)map.get("departmentId")));
                }
                //根据请求的hasDept判断
                if(!StringUtils.isEmpty(map.get("hasDept")) ){
                    if ("0".equals((String)map.get("hasDept"))){
                        list.add(criteriaBuilder.isNull(root.get("departmentId")));
                    }else {
                        list.add(criteriaBuilder.isNull(root.get("departmentId")));
                    }
                }
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        //分页
        Page<User> pageUser = userDao.findAll(spec, new PageRequest(page-1, size));
        return pageUser;
    }

    public void deleteById(String id){
        userDao.deleteById(id);
    }

    public void assignRoles(String userId, List<String> roleIds) {
        User user = userDao.findById(userId).get();
        Set<Role> roles = new HashSet<Role>();
        for(String roleId:roleIds){
            Role role = roleDao.findById(roleId).get();
            roles.add(role);
        }
        user.setRoles(roles);
        userDao.save(user);
    }

    /**
     * 用户登录
     */
    public User findByMobile(String mobile){
        return userDao.findByMobile(mobile);
    }

    /**
     * 批量用户保存
     */
    @Transactional
    public void saveAll(List<User> list, String companyId, String companyName) {
        for (User user : list) {
            //默认密码
            String password = "123";
            //加密
            password = new Md5Hash(password, user.getMobile(), 3).toString();
            user.setPassword(password);
            //id
            user.setId(idWorker.nextId()+"");
            //基本属性
            user.setCompanyId(companyId);
            user.setCompanyName(companyName);
            user.setInServiceStatus(1);
            user.setEnableState(1);
            user.setLevel("user");
            Department dept = departmentClient.findByCode(user.getDepartmentId(), companyId);
            if (dept != null) {
                user.setDepartmentId(dept.getId());
                user.setDepartmentName(dept.getName());
            }
            userDao.save(user);
        }
    }
}
