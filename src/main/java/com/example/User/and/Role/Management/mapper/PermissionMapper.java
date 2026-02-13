package com.example.User.and.Role.Management.mapper;

import com.example.User.and.Role.Management.dto.response.PermissionResponseDTO;
import com.example.User.and.Role.Management.dto.request.PermissionRequestDTO;
import com.example.User.and.Role.Management.model.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface PermissionMapper {
//    toEntity() → converts incoming client data (DTO) into a database object (Entity)
//    toDto() → converts database object (Entity) into a safe response object (DTO)

    PermissionResponseDTO toDto(Permission permission);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Permission toEntity(PermissionRequestDTO dto);

}
