package com.example.User.and.Role.Management.controller;

import com.example.User.and.Role.Management.dto.request.UserRequestDTO;
import com.example.User.and.Role.Management.dto.request.UserUpdateRequestDTO;
import com.example.User.and.Role.Management.dto.response.UserResponseDTO;
import com.example.User.and.Role.Management.exception.UserNotFoundException;
import com.example.User.and.Role.Management.mapper.UserMapper;
import com.example.User.and.Role.Management.model.User;
import com.example.User.and.Role.Management.repository.UserRepo;
import com.example.User.and.Role.Management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public UserResponseDTO createUser(@RequestBody UserRequestDTO dto,Authentication authentication)
    {
        User admin = userRepo.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        User user = userMapper.toEntity(dto);

        return userMapper.toDto(userService.register(user, admin.getId()));
    }

    @GetMapping("/getAll")
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAll()
                .stream()
                .map(curr -> userMapper.toDto(curr))
                .toList();
    }

    // giving admin to access each and every data about the user
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // giving only the necessary data to the user
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/onlyUserAccess/{id}")
    public UserResponseDTO getById(@PathVariable Long id) {
        return userMapper.toDto(userService.getUserById(id));
    }


    @GetMapping("/username/{username}")
    public UserResponseDTO getByUsername(@PathVariable String username) {
        return userMapper.toDto(userService.getByUsername(username));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public UserResponseDTO deactivateUser(@PathVariable Long id, Authentication authentication){
        User admin = userRepo.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Couldn't find the entered Username in Database."));

        return userMapper.toDto(userService.deactivateUser(id, admin.getId()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public UserResponseDTO updateUser(@PathVariable Long id, @RequestBody UserUpdateRequestDTO dto, Authentication authentication)
    {
        User admin = userRepo.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        return userMapper.toDto(
                userService.updateUser(id, dto, admin.getId())
        );
    }

}
