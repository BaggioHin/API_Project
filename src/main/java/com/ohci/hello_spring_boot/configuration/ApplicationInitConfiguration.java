package com.ohci.hello_spring_boot.configuration;

import com.ohci.hello_spring_boot.Constant.PredefinedRole;
import com.ohci.hello_spring_boot.repository.Entity.RoleEntity;
import com.ohci.hello_spring_boot.repository.Entity.UserEntity;
import com.ohci.hello_spring_boot.repository.RoleRepository;
import com.ohci.hello_spring_boot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@RequiredArgsConstructor
@Configuration
@Slf4j
public class ApplicationInitConfiguration {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @NonFinal
    static final String ADMIN_USER_NAME = "admin";

    @NonFinal
    static final String ADMIN_PASSWORD = "admin";

    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driverClassName",
            havingValue = "com.mysql.cj.jdbc.Driver")
    ApplicationRunner applicationRunner() {
        return args -> {
            if(userRepository.findByUserName(ADMIN_USER_NAME).isEmpty()) {
                roleRepository.save(RoleEntity.builder()
                        .name(PredefinedRole.USER_ROLE)
                        .description("User role")
                        .build());

                RoleEntity adminRole = roleRepository.save(RoleEntity.builder()
                        .name(PredefinedRole.ADMIN_ROLE)
                        .description("Admin role")
                        .build());

                var roles = new HashSet<RoleEntity>();
                roles.add(adminRole);

                UserEntity user = UserEntity.builder()
                        .userName(ADMIN_USER_NAME)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .roles(roles)
                        .build();

                userRepository.save(user);
                log.warn("admin user has been created with default password: admin, please change it");
            }
            log.info("Application initialization completed .....");
        };
    }
}
