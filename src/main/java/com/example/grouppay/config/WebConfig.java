package com.example.grouppay.config;


import com.example.grouppay.util.IdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 注册ID生成工具
     * @return
     */
    @Bean
    public IdWorker getIdWorker(){
        return new IdWorker(0,0);
    }

}
