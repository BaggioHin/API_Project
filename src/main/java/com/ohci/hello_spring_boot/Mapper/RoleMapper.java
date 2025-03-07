package com.ohci.hello_spring_boot.Mapper;


import com.ohci.hello_spring_boot.DTO.request.RoleRequest;
import com.ohci.hello_spring_boot.DTO.respone.RoleResponse;
import com.ohci.hello_spring_boot.repository.Entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    RoleEntity toRole(RoleRequest request);

    RoleResponse toRoleResponse(RoleEntity role);
}
