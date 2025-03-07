package com.ohci.hello_spring_boot.Controller;

import com.ohci.hello_spring_boot.DTO.request.UserRequest;
import com.ohci.hello_spring_boot.DTO.respone.ApiResponse;
import com.ohci.hello_spring_boot.DTO.respone.UserResponse;
import com.ohci.hello_spring_boot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/{userId}")
    public ApiResponse<UserResponse> getUser(@PathVariable("userId") Long userId) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(userId))
                .build();
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAllUsers())
                .build();
    }

    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }

    @PutMapping("/{userId}")
    public ApiResponse<UserResponse> updateUser(@PathVariable Long userId, @RequestBody @Valid UserRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.update(userId,request))
                .build();
    }

    @DeleteMapping("/user")
    public void deleteUser(@RequestParam List<Long> Ids) {
        userService.deleteUsers(Ids);
    }

    @DeleteMapping
    public void deleteAllUsers() {
        userService.deleteAllUsers();
    }

    @GetMapping("/my-info")
    ApiResponse<UserResponse> getMyInfo(){
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }
}
