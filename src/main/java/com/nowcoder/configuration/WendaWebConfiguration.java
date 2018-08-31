package com.nowcoder.configuration;

import com.nowcoder.interceptor.LoginRequiredInterceptor;
import com.nowcoder.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Date: 18-8-31
 * @version： V1.0
 * @Author: Chandler
 * @Description: ${todo}
 */
public class WendaWebConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    LoginRequiredInterceptor loginRequiredInterceptor;

    @Autowired
    PassportInterceptor passportInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginRequiredInterceptor);
        registry.addInterceptor(passportInterceptor);
        super.addInterceptors(registry);
    }
}
