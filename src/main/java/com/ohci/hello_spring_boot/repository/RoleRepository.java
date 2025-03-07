package com.ohci.hello_spring_boot.repository;

import com.ohci.hello_spring_boot.repository.Entity.RoleEntity;
import com.ohci.hello_spring_boot.repository.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,Long> {
    void deleteByName(String name);
//    UserEntity findByUserName(String userName);
//    Optional<RoleUserEntity> findByUserName(String username) ;
//    boolean existsByUserName(String username);
//    List<RoleUserEntity> findAllByUserName(String name);
//    RoleUserEntity save(RoleUserEntity roleUser);
}
