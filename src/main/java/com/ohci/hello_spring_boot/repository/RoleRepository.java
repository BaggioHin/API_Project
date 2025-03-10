package com.ohci.hello_spring_boot.repository;

import com.ohci.hello_spring_boot.repository.Entity.RoleEntity;
import com.ohci.hello_spring_boot.repository.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,String> {
    void deleteByName(String name);
    Optional<RoleEntity> findByName(String name);
    //    UserEntity findByUserName(String userName);
//    Optional<RoleUserEntity> findByUserName(String username) ;
//    boolean existsByUserName(String username);
//    List<RoleUserEntity> findAllByUserName(String name);
//    RoleUserEntity save(RoleUserEntity roleUser);
}
