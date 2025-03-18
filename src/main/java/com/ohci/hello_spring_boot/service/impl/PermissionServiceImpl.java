package com.ohci.hello_spring_boot.service.impl;

import com.ohci.hello_spring_boot.DTO.request.PermissionRequest;
import com.ohci.hello_spring_boot.DTO.respone.PermissionResponse;
import com.ohci.hello_spring_boot.Mapper.PermissionMapper;
import com.ohci.hello_spring_boot.repository.Entity.PermissionEntity;
import com.ohci.hello_spring_boot.repository.PermissionRepository;
import com.ohci.hello_spring_boot.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    PermissionRepository permissionRepository;
    @Autowired
    PermissionMapper permissionMapper;

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public PermissionResponse create(PermissionRequest request) {
        PermissionEntity permission = permissionMapper.toPermission(request);
        return permissionMapper.toPermissionResponse(permissionRepository.save(permission));
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<PermissionResponse> getAll() {
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void delete(String permission) {
        permissionRepository.deleteById(permission);
    }
}
