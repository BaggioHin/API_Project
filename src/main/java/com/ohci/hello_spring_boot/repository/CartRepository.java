package com.ohci.hello_spring_boot.repository;

import com.ohci.hello_spring_boot.repository.Entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartItemEntity,Long> {
    void deleteByIdIn(List<Long> ids);

    @Query("SELECT c FROM CartItemEntity c JOIN c.product p WHERE p.Id = :id")
    Optional<CartItemEntity> findByIdProducts( int id);

    @Query("SELECT c FROM CartItemEntity c JOIN c.user u WHERE u.id =: id")
    Optional<CartItemEntity> findByUserId( Long id);
}
