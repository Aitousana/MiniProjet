package com.example.miniproject.mapper;

import com.example.miniproject.DTO.UserDTO;
import com.example.miniproject.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "role", target = "role", qualifiedByName = "mapRoleToString")
    UserDTO toDTO(User user);

    @Mapping(source = "role", target = "role", qualifiedByName = "mapStringToRole")
    User toEntity(UserDTO userDTO);

    @org.mapstruct.Named("mapRoleToString")
    default String mapRoleToString(User.Role role) {
        return role != null ? role.name() : null;
    }

    @org.mapstruct.Named("mapStringToRole")
    default User.Role mapStringToRole(String role) {
        return role != null ? User.Role.valueOf(role) : null;
    }
}