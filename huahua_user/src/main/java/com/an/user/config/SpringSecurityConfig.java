package com.an.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//配置类的注解
//WebSecurityConfigurerAdapter 拦截所有请求 按照类中的方法进行处理
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * authorizeRequests  所有Security全注解配置实现的开始 表示开始说明需要的权限
     * antMatchers 拦截路径 permitAll 任何权限都可以访问 直接放行
     * anyRequest 所有的请求
     * and().csrf().disable() 固定写法 表示csrf拦截失效
     * @param http
     *
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

      http.authorizeRequests().antMatchers("/**").permitAll().anyRequest().authenticated().and().csrf().disable();

    }




}