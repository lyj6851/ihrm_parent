package com.ihrm.company.dao;

import com.ihrm.domain.company.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * <p>部门dao接口</p>
 *
 * @author xiaodongsun
 * @date 2019/01/03
 */
public interface DepartmentDao extends JpaRepository<Department, String>, JpaSpecificationExecutor<Department> {

    Department findByCodeAndCompanyId(String code, String companyId);
}
