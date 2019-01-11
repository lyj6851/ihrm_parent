package com.ihrm.system.client;

import com.ihrm.common.entity.Result;
import com.ihrm.domain.company.Department;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>声明接口，通过feign调用其他微服务</p>
 *
 * @author xiaodongsun
 * @date 2019/01/11
 */
@FeignClient(value = "ihrm-company")
public interface DepartmentClient {

    @GetMapping("/company/department/{departmentId}")
    Result findById(@PathVariable(value = "departmentId") String departmentId);

    @GetMapping("/company/department/search")
    Department findByCode(@RequestParam(value = "code") String code, @RequestParam(value = "companyId")String companyId);
}
