package com.ihrm.common.controller;

import com.ihrm.domain.system.ProfileResult;
import io.jsonwebtoken.Claims;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
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
    protected String userId;
    protected String companyName;
    protected Claims claims;

    /**
     * JWT方式获取
     * 执行请求方法之前执行 @ModelAttribute 注解方法
     * @param request
     * @param response
     */
    /*@ModelAttribute
    public void setResAndReq(HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response = response;

        Object obj = request.getAttribute("user_claims");
        if (obj != null){
            this.claims = (Claims)obj;
            this.companyId = (String) claims.get("companyId");
            this.companyName = (String) claims.get("companyName");
        }
    }*/

    /**
     * shiro方式获取
     * @param request
     * @param response
     */
    @ModelAttribute
    public void setResAndReq(HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response = response;
        Subject subject = SecurityUtils.getSubject();
        PrincipalCollection principals = subject.getPrincipals();
        if (principals != null && !principals.isEmpty()){
            ProfileResult result = (ProfileResult) principals.getPrimaryPrincipal();
            this.companyId = result.getCompanyId();
            this.userId = result.getUserId();
            this.companyName = result.getCompany();
        }
    }

}
