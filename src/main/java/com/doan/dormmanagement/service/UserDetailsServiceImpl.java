package com.doan.dormmanagement.service;

import com.doan.dormmanagement.model.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Autowired
    private ActionService actionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.doan.dormmanagement.model.User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        List<Action> actions = actionService.getAllActionsByUserName(username);
        actions = getListActionsActive(actions);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (actions != null && actions.size() > 0) {
            for (Action action: actions) {
                GrantedAuthority authority = new SimpleGrantedAuthority(action.getName());
                grantedAuthorities.add(authority);
            }
        }

        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuthorities);
    }

    private List<Action> getListActionsActive(List<Action> list) {
        return  list.stream()
                .filter(action -> action.getStatus() == 1)
                .collect(Collectors.toList());
    }
}
