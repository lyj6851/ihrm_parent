package com.ihrm.company.service;

import com.ihrm.common.service.BaseService;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.company.dao.DepartmentDao;
import com.ihrm.domain.company.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p></p>
 *
 * @author xiaodongsun
 * @date 2019/01/03
 */
@Service
public class DepartmentService extends BaseService {

    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private IdWorker idWorker;

    /**
     * 保存部门
     */
    public void save(Department department){
        String id = idWorker.nextId() + "";
        department.setId(id);
        departmentDao.save(department);
    }

    /**
     * 更新部门
     */
    public void update(Department department){
        //根据id查询
        Department dept = departmentDao.findById(department.getId()).get();
        //设置部门修改的属性
        dept.setCode(department.getCode());
        dept.setIntroduce(department.getIntroduce());
        dept.setName(department.getName());
        //更新部门
        departmentDao.save(dept);
    }

    /**
     * 根据id查询部门
     */
    public Department findById(String id){
        return departmentDao.findById(id).get();
    }

    /**
     * 查询全部部门列表
     */
    public List<Department> findAll(String companyId){
        Specification<Department> specification = super.getSpect(companyId);
        return departmentDao.findAll(specification);
    }

    /**
     * 根据id删除部门
     */
    public void deleteById(String id){
        departmentDao.deleteById(id);
    }
}
