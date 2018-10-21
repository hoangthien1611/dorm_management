package com.doan.dormmanagement.config;

import com.doan.dormmanagement.utility.Md5PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CustomAuthenticationFailureHandler authenticationFailureHandler;

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return new Md5PasswordEncoder().encodePassword(rawPassword.toString(), 16);

            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return new Md5PasswordEncoder().encodePassword(rawPassword.toString(), 16)
                        .equals(encodedPassword);
            }
        };
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/public/**", "/dorm", "/dorm/login/", "/dorm/register-acc", "/dorm/area").permitAll()
//                .antMatchers("/dorm/register-room/list-room")
//                .antMatchers("/dorm/register-room/add")
//                .antMatchers("/dorm/register-room**")
//                .antMatchers("/dorm/room**")
//                .antMatchers("/dorm/notification**")
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/dorm/login")
                .permitAll()
                .usernameParameter("userName")
                .passwordParameter("password")
                .defaultSuccessUrl("/dorm")
                .failureHandler(authenticationFailureHandler)
                .and()
                .logout()
                .logoutUrl("/dorm/logout")
                .logoutSuccessUrl("/dorm/login?message=logout")
                .invalidateHttpSession(false)
                .and()
                .exceptionHandling()
                .accessDeniedPage("/403");

        http.sessionManagement().sessionFixation().newSession()
                .invalidSessionUrl("/dorm/login?message=timeout")
                .maximumSessions(1)
                .expiredUrl("/dorm/login?message=max_session")
                .maxSessionsPreventsLogin(true);

        http.csrf().disable();
    }
}
