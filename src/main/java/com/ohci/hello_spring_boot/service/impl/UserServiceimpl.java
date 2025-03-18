package com.ohci.hello_spring_boot.service.impl;


import com.ohci.hello_spring_boot.DTO.request.UserRequest;
import com.ohci.hello_spring_boot.DTO.respone.RoleResponse;
import com.ohci.hello_spring_boot.DTO.respone.UserResponse;
import com.ohci.hello_spring_boot.Exception.AppException;
import com.ohci.hello_spring_boot.Exception.ErrorCode;
import com.ohci.hello_spring_boot.Mapper.RoleMapper;
import com.ohci.hello_spring_boot.Mapper.UserMapper;
import com.ohci.hello_spring_boot.repository.CartRepository;
import com.ohci.hello_spring_boot.repository.Entity.CartItemEntity;
import com.ohci.hello_spring_boot.repository.Entity.InfForML;
import com.ohci.hello_spring_boot.repository.Entity.RoleEntity;
import com.ohci.hello_spring_boot.repository.Entity.UserEntity;
import com.ohci.hello_spring_boot.repository.RoleRepository;
import com.ohci.hello_spring_boot.repository.UserRepository;
import com.ohci.hello_spring_boot.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceimpl implements UserService {
    RoleRepository roleRepository;

    UserRepository userRepository;

    UserMapper userMapper;

    PasswordEncoder passwordEncoder;
    private final RoleMapper roleMapper;
    private final CartRepository cartRepository;

//    @Autowired

    @Override
//    @PreAuthorize("isAuthenticated() and #id==authentication.principal.id")
    public UserResponse createUser(UserRequest request) throws AppException {
        if (userRepository.existsByUserName(request.getUserName()))
            throw new AppException(ErrorCode.USER_EXISTED);

        UserEntity userEntity = userMapper.toUserEntity(request);
        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));

        Set<RoleEntity> roles = new HashSet<>();
        roleRepository.findByName(request.getRoleName()).ifPresentOrElse(roles::add,
                () -> System.out.println("Không tìm thấy vai trò ADMIN"));

        userEntity.setRoles(roles);
        System.out.println(request.getRoleName());
        System.out.println(userEntity.getRoles().size());
        userRepository.save(userEntity);

        // Chuyển đổi roles sang RoleResponse
        Set<RoleResponse> roleResponse = roles.stream()
                .map(roleMapper::toRoleResponse)
                .collect(Collectors.toSet());

        // Chuyển đổi userEntity sang UserResponse và set roles
        UserResponse response = userMapper.toUserResponse(userEntity);
        response.setRoles(roleResponse);

        return response;
    }

//    @Override
//    public RoleResponse getRole(UserRequest request) {
//        RoleEntity roleEntity = roleRepository.findByName("ADMIN").orElse(null);
//        System.out.println(request.getRoleName());
//        return roleMapper.toRoleResponse(roleEntity);
//    }


    @Override
    @PreAuthorize("isAuthenticated() and #id==authentication.principal.id")
    public UserResponse update(Long id, UserRequest request) {
        Optional<UserEntity> user = userRepository.findById(id);
        UserEntity userEntity = user.get();
        userMapper.updateUserFromRequest(request,userEntity);
        return userMapper.toUserResponse(userRepository.save(userEntity));
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public UserResponse getUser(Long id) {
        UserEntity userEntity = userRepository.findById(id).get();
        return userMapper.toUserResponse(userEntity);
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<UserResponse> getAllUsers() {
        log.info("Susscesful");
        List<UserEntity> users = userRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<>();
        for(UserEntity user : users){
            userResponses.add(userMapper.toUserResponse(user));
        }
        return userResponses;
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void deleteUsers(@RequestBody List<Long> ids) {
//        userRepository.deleteAllById(ids);
        userRepository.deleteByIdIn(ids);
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    @PreAuthorize("isAuthenticated()")
    public UserResponse getMyInfo() {
//        Nếu như request gửi là hơ lệ thì thông tin của user đăng nhập nằm trong SecurityContextHolder
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        UserEntity userEntity = userRepository.findByUserName(name).orElseThrow(()
                -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(userEntity);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public InfForML getInfForML(String query) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        var UserId = userRepository.findByUserName(name).get().getId();
        List<CartItemEntity> cartItemEntityList = cartRepository.findByUserId(UserId);
        return InfForML.builder().query(query).cartItemEntityList(cartItemEntityList).build();
    }
}
