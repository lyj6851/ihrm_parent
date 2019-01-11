package com.ihrm.common.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * <p></p>
 *
 * @author xiaodongsun
 * @date 2019/01/11
 */
@Configuration
public class FeignConfiguration {

    /**
     * 配置feign拦截器，解决请求头问题
     */
    @Bean
    public RequestInterceptor requestInterceptor(){
        return new RequestInterceptor() {
            /**
             * 获取所有浏览器发送的请求属性，请求头赋值到feign
             */
            @Override
            public void apply(RequestTemplate requestTemplate) {
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if (attributes != null){
                    //第一次发送的请求
                    HttpServletRequest request = attributes.getRequest();
                    Enumeration<String> headerNames = request.getHeaderNames();
                    if (headerNames != null){
                        while (headerNames.hasMoreElements()){
                            //请求头名称
                            String name = headerNames.nextElement();
                            //请求头数据
                            String value = request.getHeader(name);
                            //第二次发送的请求
                            requestTemplate.header(name, value);
                        }
                    }
                }
            }
        };
    }
}
