package com.ihrm.company.service;

import com.ihrm.common.utils.IdWorker;
import com.ihrm.company.dao.CompanyDao;
import com.ihrm.domain.company.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p></p>
 *
 * @author xiaodongsun
 * @date 2019/01/02
 */
@Service
public class CompanyService {

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 保存企业
     */
    public void add(Company company){
        //基本属性的设置
        String id = idWorker.nextId() + "";
        company.setId(id);
        //默认的状态
        company.setAuditState("1");
        company.setState(1);// 0.未激活 1.已激活
        companyDao.save(company);
    }
    
    /**
     * 更新企业
     */
    public void update(Company company){
        //查询是否存在
        Company temp = companyDao.findById(company.getId()).get();
        temp.setName(company.getName());
        temp.setCompanyPhone(company.getCompanyPhone());
        //更新
        companyDao.save(company);
    }
    
    /**
     * 删除企业
     */
    public void deleteById(String id){
        companyDao.deleteById(id);
    }
    
    /**
     * 根据id查询企业
     */
    public Company findById(String id){
        return companyDao.findById(id).get();
    }

    /**
     * 查询企业列表
     */
    public List<Company> findAll(){
        return companyDao.findAll();
    }

}
