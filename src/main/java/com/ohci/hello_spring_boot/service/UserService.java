package com.ohci.hello_spring_boot.service;

import com.ohci.hello_spring_boot.DTO.request.UserRequest;
import com.ohci.hello_spring_boot.DTO.respone.UserResponse;
import com.ohci.hello_spring_boot.Exception.AppException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface UserService {
    UserResponse getUser(Long id);
    List<UserResponse> getAllUsers();
    UserResponse createUser(UserRequest request) throws AppException;
    UserResponse update(Long id,UserRequest request);
    void deleteUsers(List<Long> ids);
    void deleteAllUsers();
    UserResponse getMyInfo();
}
