package com.ohci.hello_spring_boot.service.impl;

import com.ohci.hello_spring_boot.DTO.request.RoleRequest;
import com.ohci.hello_spring_boot.DTO.respone.RoleResponse;
import com.ohci.hello_spring_boot.Mapper.RoleMapper;
import com.ohci.hello_spring_boot.repository.Entity.RoleEntity;
import com.ohci.hello_spring_boot.repository.PermissionRepository;
import com.ohci.hello_spring_boot.repository.RoleRepository;
import com.ohci.hello_spring_boot.service.RoleService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepository;

    RoleMapper roleMapper;

    PermissionRepository permissionRepository;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public RoleResponse create(RoleRequest roleRequest) {
        RoleEntity roleEntity = roleMapper.toRole(roleRequest);

        var permissionEntity = permissionRepository.findAllById(roleRequest.getPermissions());
        roleEntity.setPermissions(new HashSet<>(permissionEntity));

        roleRepository.save(roleEntity);
        return roleMapper.toRoleResponse(roleEntity);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<RoleResponse> getAllRoles() {
        List<RoleEntity> roleEntities = roleRepository.findAll();
        List<RoleResponse> roleResponses = new ArrayList<>();
        for (RoleEntity roleEntity : roleEntities) {
            roleResponses.add(roleMapper.toRoleResponse(roleEntity));
        }
        return roleResponses;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(String name) {
        roleRepository.deleteByName(name);
    }
}
