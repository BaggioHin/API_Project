package com.ohci.hello_spring_boot.Controller;

import com.ohci.hello_spring_boot.DTO.request.RoleRequest;
import com.ohci.hello_spring_boot.DTO.respone.ApiResponse;
import com.ohci.hello_spring_boot.DTO.respone.RoleResponse;
import com.ohci.hello_spring_boot.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleController {
   @Autowired
   RoleService roleService;

//    @PostMapping
//    ApiResponse<RoleResponse>
    @PostMapping
    ApiResponse<RoleResponse> createRole(@RequestBody RoleRequest roleRequest) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.create(roleRequest))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> findAllRoles() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAllRoles())
                .build();
    }

    @DeleteMapping
    ApiResponse<Void>  deleteRole(@PathVariable String nameRole) {
        roleService.delete(nameRole);
        return ApiResponse.<Void>builder().build();

    }
}
