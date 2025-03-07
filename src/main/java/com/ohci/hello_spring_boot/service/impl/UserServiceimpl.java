package com.ohci.hello_spring_boot.service.impl;

import com.ohci.hello_spring_boot.DTO.request.UserRequest;
import com.ohci.hello_spring_boot.DTO.respone.UserResponse;
import com.ohci.hello_spring_boot.Exception.AppException;
import com.ohci.hello_spring_boot.Exception.ErrorCode;
import com.ohci.hello_spring_boot.Mapper.UserMapper;
import com.ohci.hello_spring_boot.repository.Entity.UserEntity;
import com.ohci.hello_spring_boot.repository.UserRepository;
import com.ohci.hello_spring_boot.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceimpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

//    @Autowired

    @Override
    @Transactional
    public UserResponse createUser(UserRequest request) throws AppException {
        if(userRepository.existsByUserName(request.getUserName()))
            throw new AppException(ErrorCode.USER_EXISTED);

        UserEntity userEntity = userMapper.toUserEntity(request);
//        RoleUserEntity roleUserEntity = userMapper.toRoleUserEntity(request);

        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(userEntity);

        return userMapper.toUserResponse(userEntity);
    }

    @Override
    public UserResponse update(Long id, UserRequest request) {
        Optional<UserEntity> user = userRepository.findById(id);
        UserEntity userEntity = user.get();
        userMapper.updateUserFromRequest(request,userEntity);
        return userMapper.toUserResponse(userRepository.save(userEntity));
    }

    @Override
    public UserResponse getUser(Long id) {
        UserEntity userEntity = userRepository.findById(id).get();
        return userMapper.toUserResponse(userEntity);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<>();
        for(UserEntity user : users){
            userResponses.add(userMapper.toUserResponse(user));
        }
        return userResponses;
    }

    @Override
    public void deleteUsers(List<Long> ids) {
//        userRepository.deleteAllById(ids);
        userRepository.deleteByIdIn(ids);
    }

    @Override
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    public UserResponse getMyInfo() {
//        Nếu như request gửi là hơ lệ thì thông tin của user đăng nhập nằm trong SecurityContextHolder
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        UserEntity userEntity = userRepository.findByUserName(name).orElseThrow(()
                -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(userEntity);
    }
}
