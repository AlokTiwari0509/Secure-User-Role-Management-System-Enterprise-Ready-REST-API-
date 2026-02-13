package com.example.User.and.Role.Management.mapper;

import com.example.User.and.Role.Management.dto.request.UserRequestDTO;
import com.example.User.and.Role.Management.dto.response.UserResponseDTO;
import com.example.User.and.Role.Management.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDTO toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toEntity(UserRequestDTO dto);
}