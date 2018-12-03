package com.doan.dormmanagement.config;

import com.doan.dormmanagement.common.Actions;
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
                .antMatchers("/admin/auth/**", "/admin/login**").permitAll()
                .antMatchers("/admin/index").hasAuthority(Actions.VIEW_HOME)
                .antMatchers("/admin/room/add").hasAuthority(Actions.ADD_ROOM)
                .antMatchers("/admin/room/edit").hasAuthority(Actions.EIDT_ROOM)
                .antMatchers("/admin/room/change-status").hasAuthority(Actions.CHANGE_STT_ROOM)
                .antMatchers("/admin/room**").hasAuthority(Actions.VIEW_ROOMS)
                .antMatchers("/admin/area/add").hasAuthority(Actions.ADD_AREA)
                .antMatchers("/admin/area/edit").hasAuthority(Actions.EDIT_AREA)
                .antMatchers("/admin/area/change-status").hasAuthority(Actions.CHANGE_STT_AREA)
                .antMatchers("/admin/area**").hasAuthority(Actions.VIEW_AREAS)
                .antMatchers("/admin/floor/add").hasAuthority(Actions.ADD_FLOOR)
                .antMatchers("/admin/floor/change-status").hasAuthority(Actions.CHANGE_STT_FLOOR)
                .antMatchers("/admin/floor**").hasAuthority(Actions.VIEW_AREAS)
                .antMatchers("/admin/subsistence/add").hasAuthority(Actions.ADD_SUBSISTENCE)
                .antMatchers("/admin/subsistence/edit").hasAuthority(Actions.EDIT_SUBSISTENCE)
                .antMatchers("/admin/subsistence/paid").hasAuthority(Actions.CHANGE_STT_SUBSISTENCE)
                .antMatchers("/admin/subsistence**").hasAuthority(Actions.VIEW_SUBSISTENCE)
                .antMatchers("/admin/register-room/accept-all").hasAuthority(Actions.ACCEPT_REGISTER)
                .antMatchers("/admin/register-room**").hasAuthority(Actions.VIEW_REGISTERS)
                .antMatchers("/admin/user/add").hasAuthority(Actions.ADD_USER)
                .antMatchers("/admin/user/delete").hasAuthority(Actions.DEL_USER)
                .antMatchers("/admin/user/change-status").hasAuthority(Actions.CHANGE_STT_USER)
                .antMatchers("/admin/user/change-group").hasAuthority(Actions.UPDATE_GROUP_USER)
                .antMatchers("/admin/user/reset-password").hasAuthority(Actions.RESET_PASS_USER)
                .antMatchers("/admin/user/*/update-permission").hasAuthority(Actions.UPDATE_PERMISSION_USER)
                .antMatchers("/admin/user/**").hasAuthority(Actions.VIEW_USERS)
                .antMatchers("/admin/group/detail","/admin/group/detail**").hasAuthority(Actions.VIEW_DETAIL_GROUP)
                .antMatchers("/admin/group/detail/add-action").hasAuthority(Actions.ADD_ACTION_GROUP)
                .antMatchers("/admin/group/detail/del-action").hasAuthority(Actions.DEL_ACTION_GROUP)
                .antMatchers("/admin/group/add").hasAuthority(Actions.ADD_GROUP)
                .antMatchers("/admin/group/edit").hasAuthority(Actions.EDIT_GROUP)
                .antMatchers("/admin/group/delete").hasAuthority(Actions.DEL_GROUP)
                .antMatchers("/admin/group**").hasAuthority(Actions.VIEW_GROUPS)
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
                .invalidateHttpSession(false)
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
