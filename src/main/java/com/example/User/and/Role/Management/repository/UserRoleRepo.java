package com.example.User.and.Role.Management.repository;

import com.example.User.and.Role.Management.model.Role;
import com.example.User.and.Role.Management.model.User;
import com.example.User.and.Role.Management.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepo extends JpaRepository<UserRole, Long> {
    List<UserRole> findByUser(User user);
}
