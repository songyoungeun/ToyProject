package com.joins.helloworld.security;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Log
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    ZerockUserService zerockUserService;
    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("security config........");
        http.authorizeRequests()
            .antMatchers("/guest/**").permitAll()
            .antMatchers("/manager/**").hasRole("MANAGER")
            .antMatchers("/admin/**").hasRole("ADMIN")
            .and()
            .formLogin().loginPage("/login")
                .and()
                .exceptionHandling().accessDeniedPage("/accessDenied")
                .and().logout().logoutUrl("/logout").invalidateHttpSession(true);
        http.userDetailsService(zerockUserService);
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        log.info("build auth global...................");

        String query1 = "SELECT uid username, upw password, true enabled FROM tbl_members WHERE uid = ?";
        String query2 = "SELECT member uid, role_name role FROM tbl_member_roles WHERE member = ?";
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(query1)
                .rolePrefix("ROLE_")
                .authoritiesByUsernameQuery(query2);
//        auth.inMemoryAuthentication()
//            .withUser("song")
//            .password("{noop}5481")
//            .roles("MANAGER");
    }
//        auth.inMemoryAuthentication()
//                .withUser("foo").password("{noop}bar").roles("USER");
}
