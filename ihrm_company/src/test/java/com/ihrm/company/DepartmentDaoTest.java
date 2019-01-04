package com.ihrm.company;

import com.ihrm.company.dao.DepartmentDao;
import com.ihrm.domain.company.Department;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <p></p>
 *
 * @author xiaodongsun
 * @date 2019/01/02
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class DepartmentDaoTest {

    @Autowired
    private DepartmentDao departmentDao;

    @Test
    public void test(){
        Department department = departmentDao.findById("1080750802043351040").get();
        System.out.println(department);
    }
}
