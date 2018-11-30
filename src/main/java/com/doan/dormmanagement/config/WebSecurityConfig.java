package com.doan.dormmanagement.config;

import com.doan.dormmanagement.utility.Md5PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
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
                .antMatchers("/admin/auth/**", "/admin/login**").permitAll()
                .antMatchers("/admin/group").hasAuthority("xem") // test granted authorities
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/admin/login")
                .permitAll()
                .usernameParameter("userName")
                .passwordParameter("password")
                .defaultSuccessUrl("/admin/index")
                .failureHandler(authenticationFailureHandler)
                .and()
                .logout()
                .logoutUrl("/admin/logout")
                .logoutSuccessUrl("/admin/login?message=logout")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/403");

        http.sessionManagement().sessionFixation().newSession()
                .invalidSessionUrl("/admin/login?message=timeout")
                .maximumSessions(1)
                .expiredUrl("/admin/login?message=max_session")
                .maxSessionsPreventsLogin(true);

        http.csrf().disable();
    }
}
