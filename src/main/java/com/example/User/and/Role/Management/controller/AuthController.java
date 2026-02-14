package com.example.User.and.Role.Management.controller;

import com.example.User.and.Role.Management.model.User;
import com.example.User.and.Role.Management.repository.UserRepo;
import com.example.User.and.Role.Management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.AuthenticationManager;
import com.example.User.and.Role.Management.service.JwtService;
import com.example.User.and.Role.Management.service.MyUserDetailsService;


import java.util.List;

@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtService jwtService;


    @Autowired
    UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody User user) {

        Authentication authentication = authenticationManager
                            .authenticate( new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));

        UserDetails userDetails =
                            myUserDetailsService.loadUserByUsername(user.getUsername());

        List<String> roles = userDetails
                            .getAuthorities()
                            .stream()
                            .map(a -> a.getAuthority())
                            .toList();

        return jwtService.generateToken(user.getUsername(), roles);
    }


//new comment
}
