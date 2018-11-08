package com.anjuxing.platform.authority.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


/**
 * @author xiongt
 * @Description
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//            http.formLogin()   //spring security 登录页面
//                    .loginPage("")

        http.headers().frameOptions().disable();
        http.authorizeRequests().antMatchers("/user").hasRole("user");

                    http
//                    .and()
                    .authorizeRequests()  //定义哪些需要保护，哪些不需要保护
                    .antMatchers("/login","/signin","/admin/index","/sys/dept/*","/sys/acl/*","/sys/aclModule/*"
                    ,"/sys/log/*","/sys/role/*","/sys/user/*","/logouts","/remote/user/username").permitAll()
                    .anyRequest()         //任何请求登录都可以访问
                    .authenticated()  //认证
                    .and()
                    .csrf().disable();  //注销不需要认证
    }

    /**
     * 忽略js,css,images
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {

        web.ignoring().antMatchers("/js/**","/css/**","/images/**");
    }


}
