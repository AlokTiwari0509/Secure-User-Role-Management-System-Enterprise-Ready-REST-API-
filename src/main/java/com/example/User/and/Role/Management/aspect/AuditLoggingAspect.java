package com.example.User.and.Role.Management.aspect;

import com.example.User.and.Role.Management.model.AuditLog;
import com.example.User.and.Role.Management.model.User;
import com.example.User.and.Role.Management.repository.AuditLogRepo;
import com.example.User.and.Role.Management.repository.UserRepo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditLoggingAspect {

    @Autowired
    private AuditLogRepo auditLogRepo;

    @Autowired
    private UserRepo userRepo;

    // intercept all service layer methods
    @Pointcut("execution(* com.example.User.and.Role.Management.service..*(..))")
    public void serviceLayer() {}

    @AfterReturning("serviceLayer()")
    public void logAudit(JoinPoint joinPoint) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()
                || "anonymousUser".equals(auth.getPrincipal())) {
            return;
        }

        String username = auth.getName();

        User user = userRepo.findByUsername(username).orElse(null);

        AuditLog log = new AuditLog();
        log.setUser(user);
        log.setCreatedBy(user != null ? user.getId() : null);
        log.setUpdatedBy(user != null ? user.getId() : null);

        log.setAction(resolveAction(joinPoint.getSignature().getName()));
        log.setEndpoint(joinPoint.getTarget().getClass().getSimpleName());
        log.setMethod(joinPoint.getSignature().getName());

        auditLogRepo.save(log);
    }

    private String resolveAction(String methodName) {
        if (methodName.startsWith("create")) return "CREATE";
        if (methodName.startsWith("update")) return "UPDATE";
        if (methodName.startsWith("delete")) return "DELETE";
        if (methodName.contains("assign")) return "ASSIGN";
        return "ACTION";
    }
}
