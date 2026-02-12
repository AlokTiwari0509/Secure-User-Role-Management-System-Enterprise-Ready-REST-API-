package com.example.User.and.Role.Management.service;

//import com.example.User.and.Role.Management..repository.PermissionRepo;
import com.example.User.and.Role.Management.dto.request.PermissionRequestDTO;
import com.example.User.and.Role.Management.model.Permission;
import com.example.User.and.Role.Management.model.User;
import com.example.User.and.Role.Management.repository.PermissionRepo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class PermissionService {
    @Autowired
    private PermissionRepo permissionRepo;

    public Permission create(Permission p, Long adminId) {
        p.setCreatedBy(adminId);
        return permissionRepo.save(p);
    }

    public List<Permission> getAll() {
        return permissionRepo.findAll();
    }

    public Permission updateStatus(PermissionRequestDTO dto, User admin) {
        Permission p = permissionRepo.findByName(dto.getName())
                .orElseThrow(() -> new RuntimeException("Permission not found"));

        p.setStatus(dto.getStatus());
        p.setUpdatedBy(admin.getId());
        return permissionRepo.save(p);
    }
}
