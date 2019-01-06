package com.ihrm.system.service;

import com.ihrm.common.service.BaseService;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.domain.system.Role;
import com.ihrm.system.dao.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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

    public Page<Role> findSearch(String companyId, int page, int size){
        Specification<Role> specification = new Specification<Role>() {
            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("companyId").as(String.class), companyId);
            }
        };
        return roleDao.findAll(specification, PageRequest.of(page, size));
    }
}
