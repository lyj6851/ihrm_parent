package com.ihrm.common.interceptor;

import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.exception.CommonException;
import com.ihrm.common.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p></p>
 *
 * @author xiaodongsun
 * @date 2019/01/08
 */
@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 进入控制器方法执行之前
     * @param request
     * @param response
     * @param handler
     * @return true：可以继续执行， false：拦截
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 拦截器获取到token数据
         * 简化获取token数据的代码编写 统一的用户权限校验（是否登录）
         * 判断用户是否具有当前访问接口的权限
         */
        //通过request获取请求token信息
        String autorization = request.getHeader("Authorization");
        //判断请求头信息是否为空，或者是否以 Bearer 开头
        if (!StringUtils.isEmpty(autorization) && autorization.startsWith("Bearer")){
            String token = autorization.replace("Bearer ", "");
            Claims claims = jwtUtils.parseJwt(token);
            if (claims != null){
                //当前用户的可访问的api权限字符串
                String apis = (String) claims.get("apis");
                HandlerMethod h = (HandlerMethod) handler;
                RequestMapping annotation = h.getMethodAnnotation(RequestMapping.class);
                String name = annotation.name();
                if (apis.contains(name)){
                    request.setAttribute("user_claims", claims);
                    return true;
                }else {
                    throw new CommonException(ResultCode.UNAUTHORISE);
                }
            }
        }
        throw new CommonException(ResultCode.UNAUTHENTICATED);
    }

}
