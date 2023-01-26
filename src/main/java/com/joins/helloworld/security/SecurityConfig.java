package com.joins.helloworld.security;

import com.joins.helloworld.domain.PersistentLogins;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Log
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    ZerockUserService zerockUserService;
    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("security config........");
        http.csrf().requireCsrfProtectionMatcher(new AntPathRequestMatcher("*/h2-console/**"))
                .and().headers().addHeaderWriter(new StaticHeadersWriter("X-Content-Security-Policy","script-src 'self'")).frameOptions().disable();
        http.authorizeRequests()
//            .antMatchers("/h2-console/**").permitAll()
            .antMatchers("/guest/**").permitAll()
            .antMatchers("/manager/**").hasRole("MANAGER")
            .antMatchers("/admin/**").hasRole("ADMIN");
//        .antMatchers("/board/register").hasAnyRole("BASIC","MANAGER","ADMIN"); //여러개 롤 설정 가능
        http.formLogin().loginPage("/login").successHandler(new LoginSuccessHandler())
            .and().logout().logoutUrl("/logout").invalidateHttpSession(true);
        http.exceptionHandling()
            .accessDeniedPage("/accessDenied");

        http.rememberMe()
                .key("zerock")
                .userDetailsService(zerockUserService)//userDetailsService: 인증하는데 필요한 걸 만들어 넣어줌
                .tokenRepository(getJDBCRepository()) //100
                .tokenValiditySeconds(60*60*24); //쿠키의 유효기간 설정 24시간 유지
        //remember에서는 쿠키의 값으로 암호화된 값을 전달하므로, 암호키를 지정하여 사용
        //hash-based(default) VS persistent token : username + 쿠키의 만료시간 + 패스워드를 base-64 인코딩
        //해당 쿠키의 유효기간은 로그인시간 + 2주, 쿠키는 유효기간이 설정되면 브라우저 내부에 저장, 로그아웃 시 자동 삭제
        //따라서 사용자가 브라우저를 종료하고 다시 서버상의 경로에 접근하면 자동으로 생성된 jsessionid 쿠키는 없지만 보관된 remember-me쿠키는 그대로 가지고 서버에 접근함
    }
    private PersistentTokenRepository getJDBCRepository(){
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        log.info("build Auth global.....");
        auth.userDetailsService(zerockUserService).passwordEncoder(passwordEncoder());
    }
}
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{ //인증진행
//        log.info("build auth global...................");
//
//        String query1 = "SELECT uid username, upw password, true enabled" + "FROM tbl_members" + "WHERE uid = ?";
//        String query2 = "SELECT member uid, role_name role" + "FROM tbl_member_roles" + "WHERE member = ?";
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery(query1)
//                .rolePrefix("ROLE_")
//                .authoritiesByUsernameQuery(query2);
////        auth.inMemoryAuthentication()
////            .withUser("song")
////            .password("{noop}5481")
////            .roles("MANAGER");
//    }
////        auth.inMemoryAuthentication()
////                .withUser("foo").password("{noop}bar").roles("USER");
