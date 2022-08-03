package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private DataSource dataSource;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/h2/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery(
                        "SELECT username, password, enabled from customer where username = ?")
                .authoritiesByUsernameQuery(
                        "SELECT u.username, a.authority " +
                                "FROM authority a, customer u " +
                                "WHERE u.username = ? " +
                                "AND u.id = a.user_id"
                );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/product/**", "/orders/**,","/customer/**").permitAll().anyRequest().authenticated()
                .and().formLogin().permitAll().and().logout().permitAll().and().httpBasic();
        http.cors().disable().csrf().disable();
    }
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.formLogin();
//        http.authorizeRequests().antMatchers("/product").permitAll()
//                .antMatchers("/admin").hasAuthority("ADMIN")
//                .antMatchers("/products").hasAnyAuthority("USER", "ADMIN")
//                .anyRequest().authenticated();
//    }
}
