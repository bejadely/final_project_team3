package com.trip.finalProject.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.trip.finalProject.security.service.Impl.CustomUserDetailService;


@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	CustomUserDetailService customUserDetailService;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                	// 개발을 편하게 하기 위해 임시 주석처리
//                    .antMatchers("/common/**").hasAnyRole("A1", "A2", "A4") // 일반회원, 권한대기중 회원만 접근 가능한 경로(마이페이지용, 가이드도 사용가능한 마이페이지 포함)
//                    .antMatchers("/guide/**").hasRole("A2") // 가이드 회원만 접근가능한 경로(가이드 - 마이페이지용)
//                    .antMatchers("/admin/**").hasRole("A3") // 관리자 페이지
                    .antMatchers("admin/js/**","admin/css/**","admin/img/**","admin/fonts/**","admin/mainTheme/**", "admin/ckeditor5-build-classic/**").permitAll()
                    .anyRequest().permitAll()
                .and()
                    .formLogin()
                    .loginPage("/member/login")
                    .loginProcessingUrl("/loginProc")
                    .usernameParameter("memberId")
                    .passwordParameter("password")
                    .successHandler(loginSuccessHandler())
                .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logoutProc"))
                .and()
               		.csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**","/css/**","/img/**","/fonts/**","/mainTheme/**", "/ckeditor5-build-classic/**");
    }
    
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService);
    }
    
    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
    	return new CustomLoginSuccessHandler();
    }
}
