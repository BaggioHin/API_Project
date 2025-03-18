package com.ohci.hello_spring_boot.repository;

import com.ohci.hello_spring_boot.repository.Entity.CartItemEntity;
import com.ohci.hello_spring_boot.repository.Entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartItemEntity,Long> {
    void deleteByIdIn(List<Long> ids);

//    @Query("SELECT c FROM CartItemEntity c JOIN c.product p WHERE p.id = :id")
//    Optional<CartItemEntity> findByIdProducts( Long id);

    @Query("SELECT c FROM CartItemEntity c JOIN c.user u WHERE u.id = :id")
    List<CartItemEntity> findByUserId(@Param("id") Long id);

//    List<CartItemEntity> findByUserId(Long id);

    @Query("SELECT p FROM ProductsEntity p JOIN p.cartItemEntity c WHERE c.id=: id")
    Optional<ProductsEntity> findByCartId(Long id);
}
