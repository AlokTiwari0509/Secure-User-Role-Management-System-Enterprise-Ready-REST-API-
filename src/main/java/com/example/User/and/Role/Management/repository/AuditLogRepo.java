package com.example.User.and.Role.Management.repository;

import com.example.User.and.Role.Management.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepo extends JpaRepository<AuditLog, Long> {
}
