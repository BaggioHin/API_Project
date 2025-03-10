package com.ohci.hello_spring_boot.Mapper;

import com.ohci.hello_spring_boot.DTO.request.UserRequest;
import com.ohci.hello_spring_boot.DTO.respone.UserResponse;
import com.ohci.hello_spring_boot.repository.Entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toUserEntity(UserRequest request);
    void updateUserFromRequest(UserRequest request, @MappingTarget UserEntity user);

    //    RoleUserEntity toRoleUserEntity(UserRequest request);
    @Mapping(target = "id", source = "id")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "roles",ignore = true)
    UserResponse toUserResponse(UserEntity entity);
}
