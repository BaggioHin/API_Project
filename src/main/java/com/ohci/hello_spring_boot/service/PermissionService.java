package com.ohci.hello_spring_boot.service;


import com.ohci.hello_spring_boot.DTO.request.PermissionRequest;
import com.ohci.hello_spring_boot.DTO.respone.PermissionResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PermissionService {
    PermissionResponse create(PermissionRequest request);
    List<PermissionResponse> getAll();
    void delete(String permission);
}
