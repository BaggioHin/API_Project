package com.ohci.hello_spring_boot.service;

import com.ohci.hello_spring_boot.DTO.request.RoleRequest;
import com.ohci.hello_spring_boot.DTO.respone.RoleResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    RoleResponse create(RoleRequest roleRequest);
    List<RoleResponse> getAllRoles();
    void delete(String id);
}
