package com.example.User.and.Role.Management.service;

import com.example.User.and.Role.Management.model.User;
import com.example.User.and.Role.Management.model.UserRole;
import com.example.User.and.Role.Management.model.Principal.UserPrincipal;
import com.example.User.and.Role.Management.repository.UserRepo;
import com.example.User.and.Role.Management.repository.UserRoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private UserRoleRepo userRoleRepo;

    @Override
    public UserDetails loadUserByUsername(String username) {

        User user = repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        List<GrantedAuthority> auths =
                userRoleRepo.findByUser(user)
                        .stream()
                        .map(ur -> new SimpleGrantedAuthority(
                                "ROLE_" + ur.getRole().getName()
                        ))
                        .collect(Collectors.toList());

        return new UserPrincipal(user, auths);
    }

}
