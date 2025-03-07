package com.ohci.hello_spring_boot.repository;

import com.ohci.hello_spring_boot.repository.Entity.InvalidationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidationTokenRepository extends JpaRepository<InvalidationTokenEntity,String> {

}
