package com.proyectofinal.autenticacion.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class InMemoryUserDetailsService implements UserDetailsService {

    @Value("${app.security.user.name}")
    private String userName;

    @Value("${app.security.user.password}")
    private String userPassword;

    @Value("${app.security.user.roles}")
    private String userRoles;

    @Value("${app.security.user2.name}")
    private String user2Name;

    @Value("${app.security.user2.password}")
    private String user2Password;

    @Value("${app.security.user2.roles}")
    private String user2Roles;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.equals(userName)) {
            return new User(userName, userPassword, getAuthorities(userRoles));
        } else if (username.equals(user2Name)) {
            return new User(user2Name, user2Password, getAuthorities(user2Roles));
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String roles) {
        return AuthorityUtils.createAuthorityList(roles.split(","));
    }
}
