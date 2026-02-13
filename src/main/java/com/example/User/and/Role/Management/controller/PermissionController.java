package com.example.User.and.Role.Management.controller;

import com.example.User.and.Role.Management.dto.request.PermissionRequestDTO;
import com.example.User.and.Role.Management.dto.response.PermissionResponseDTO;
import com.example.User.and.Role.Management.mapper.PermissionMapper;
import com.example.User.and.Role.Management.model.Permission;
import com.example.User.and.Role.Management.model.User;
import com.example.User.and.Role.Management.repository.UserRepo;
import com.example.User.and.Role.Management.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/permissions")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserRepo userRepo;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public PermissionResponseDTO createPermission(@RequestBody PermissionRequestDTO dto, Authentication authentication
    ){
        User admin = userRepo.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Username could not be found"));

        Permission permission = permissionMapper.toEntity(dto);

        return permissionMapper.toDto(
                permissionService.create(permission, admin.getId())
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateStatus")
    public PermissionResponseDTO updateStatus(@RequestBody PermissionRequestDTO  dto, Authentication authentication)
    {
        User admin = userRepo.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Username could not be found"));

        return permissionMapper.toDto(
                permissionService.updateStatus(dto,admin)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<PermissionResponseDTO> getAllPermissions() {
        return permissionService.getAll()
                .stream()
                .map(permissionMapper::toDto)
                .toList();
    }

}
