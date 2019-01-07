package com.ihrm.system;

import com.ihrm.common.utils.IdWorker;
import com.ihrm.common.utils.JwtUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

/**
 * <p>启动类</p>
 *
 * @author xiaodongsun
 * @date 2019/1/6
 */
@SpringBootApplication
@EntityScan(basePackages = "com.ihrm.domain.system")
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker();
    }

    @Bean
    public JwtUtils jwtUtils(){
        return new JwtUtils();
    }
}
