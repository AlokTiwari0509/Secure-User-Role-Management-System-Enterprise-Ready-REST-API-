package com.example.User.and.Role.Management.mapper;

import com.example.User.and.Role.Management.dto.request.RoleRequestDTO;
import com.example.User.and.Role.Management.dto.response.RoleResponseDTO;
import com.example.User.and.Role.Management.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleResponseDTO toDto(Role role);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Role toEntity(RoleRequestDTO dto);
}

