package com.trip.finalProject.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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
                    .antMatchers("/member/**").hasAnyAuthority("A1", "A4") // 일반회원, 권한대기중 회원만 접근 가능한 경로(마이페이지옹)
                    .antMatchers("/guide/**").hasAuthority("A2") // 가이드 회원만 접근가능한 경로(가이드 - 마이페이지용)
                    .antMatchers("/admin/**").hasAuthority("A3") // 관리자 페이지
                    .anyRequest().permitAll()
                    // .anyRequest().authenticated() // 나머지는 접근 제어
                .and()
                    .formLogin()
                    //.loginPage("/member/login") // 여기에 로그인 폼 설정
                    //.loginProcessingUrl("/loginProc")
//                    .usernameParameter("memberId")
//                    .passwordParameter("password")
                    .defaultSuccessUrl("/", true) // 성공 시 반환하는 페이지
                    .permitAll()
                .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logoutProc"));
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**","/css/**","/img/**","/fonts/**","/mainTheme/**");
    }
    
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService);
    }
}
