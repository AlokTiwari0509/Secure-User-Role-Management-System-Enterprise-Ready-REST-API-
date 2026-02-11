package com.example.User.and.Role.Management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PermissionResponseDTO {
    private Long id;
    private String name;
    private String status;
}
