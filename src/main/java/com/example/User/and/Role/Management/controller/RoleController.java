package com.example.User.and.Role.Management.controller;

import com.example.User.and.Role.Management.dto.request.RoleRequestDTO;
import com.example.User.and.Role.Management.dto.response.RoleResponseDTO;
import com.example.User.and.Role.Management.mapper.RoleMapper;
import com.example.User.and.Role.Management.model.Role;
import com.example.User.and.Role.Management.model.User;
import com.example.User.and.Role.Management.model.UserRole;
import com.example.User.and.Role.Management.repository.RoleRepo;
import com.example.User.and.Role.Management.repository.UserRepo;
import com.example.User.and.Role.Management.repository.UserRoleRepo;
import com.example.User.and.Role.Management.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")

public class RoleController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserRoleRepo userRoleRepo;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleService roleService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public RoleResponseDTO createRole(@RequestBody RoleRequestDTO dto, Authentication authentication){
        User admin = userRepo.findByUsername(authentication.getName())
                .orElseThrow(()-> new UsernameNotFoundException("Username couldn't be found"));

        Role role = roleMapper.toEntity(dto);

        return roleMapper.toDto(
                roleService.create(role,admin.getId())
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{userId}/setRoleTo/{roleId}")
    public String assignRole(@PathVariable Long userId,@PathVariable Long roleId){
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Role role = roleRepo.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        return roleService.assignRole(user, role);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateRole/{role_id}")
    public RoleResponseDTO updateRole(@PathVariable("role_id") Long id, @RequestBody RoleRequestDTO dto, Authentication authentication){
        User admin = userRepo.findByUsername(authentication.getName())
                .orElseThrow(()-> new UsernameNotFoundException("Your given username couldn't be found in the database"));

        return roleMapper.toDto(
                roleService.updateRole(id, dto, admin.getId())
        );

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete")
    public RoleResponseDTO deleteRole(@RequestBody RoleRequestDTO dto, Authentication authentication){
        User admin = userRepo.findByUsername(authentication.getName())
                .orElseThrow(()-> new UsernameNotFoundException("Your given username couldn't be found in the database"));

        return roleMapper.toDto(
                roleService.deleteRole(dto,admin.getId())
        );
    }
}
