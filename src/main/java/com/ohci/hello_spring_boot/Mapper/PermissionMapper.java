package com.ohci.hello_spring_boot.Mapper;

import com.ohci.hello_spring_boot.DTO.request.PermissionRequest;
import com.ohci.hello_spring_boot.DTO.respone.PermissionResponse;
import com.ohci.hello_spring_boot.repository.Entity.PermissionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionEntity toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(PermissionEntity permission);
}
