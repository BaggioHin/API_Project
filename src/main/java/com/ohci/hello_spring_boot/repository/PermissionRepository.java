package com.ohci.hello_spring_boot.repository;

import com.ohci.hello_spring_boot.repository.Entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, String> {

}
