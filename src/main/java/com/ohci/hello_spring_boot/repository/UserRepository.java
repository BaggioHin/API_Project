package com.ohci.hello_spring_boot.repository;

import com.ohci.hello_spring_boot.repository.Entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUserName(String username);
    void deleteByIdIn(List<Long> Ids);
    Optional<UserEntity> findByUserName(String username);
//    Optional<UserEntity> findByUsername(String username);
}
