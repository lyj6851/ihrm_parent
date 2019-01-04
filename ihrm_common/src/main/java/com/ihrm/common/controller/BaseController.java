package com.ihrm.common.controller;

import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>基础Controller</p>
 *
 * @author xiaodongsun
 * @date 2019/01/03
 */
public class BaseController {

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected String companyId;
    protected String companyName;

    /**
     * 执行请求方法之前执行 @ModelAttribute 注解方法
     * @param request
     * @param response
     */
    @ModelAttribute
    public void setResAndReq(HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response = response;
        this.companyId = "1080398901191782400";
        this.companyName = "Top";
    }

}
