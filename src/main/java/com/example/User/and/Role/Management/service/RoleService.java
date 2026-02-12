package com.example.User.and.Role.Management.service;

import com.example.User.and.Role.Management.dto.request.RoleRequestDTO;
import com.example.User.and.Role.Management.model.Role;
import com.example.User.and.Role.Management.model.User;
import com.example.User.and.Role.Management.model.UserRole;
import com.example.User.and.Role.Management.repository.RoleRepo;
import com.example.User.and.Role.Management.repository.UserRoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserRoleRepo userRoleRepo;

    public Role create(Role role, Long adminId) {
        role.setCreatedBy(adminId);
        return roleRepo.save(role);
    }

    public Role getById(Long id) {
        return roleRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }

    public String assignRole(User user, Role role) {
        UserRole ur = new UserRole();
        ur.setUser(user);
        ur.setRole(role);
        ur.setStatus("ACTIVE");
        userRoleRepo.save(ur);
        return "Role assigned successfully";
    }

    public Role updateRole(Long id, RoleRequestDTO dto, Long adminId) {
        Role role = roleRepo.findById(id)
                .orElseThrow(()->new RuntimeException("Role not found"));

        role.setName(dto.getName());
        role.setStatus(dto.getStatus());
        role.setUpdatedBy(adminId);

        return roleRepo.save(role);
    }

    public Role deleteRole(RoleRequestDTO dto, Long id) {
        Role role = roleRepo.findByName(dto.getName())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        role.setStatus("INACTIVE");
        role.setUpdatedBy(id);
        return roleRepo.save(role);
    }
}
