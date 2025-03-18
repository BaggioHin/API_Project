package com.ohci.hello_spring_boot.repository;


import com.ohci.hello_spring_boot.repository.Entity.OrderItemEntity;
import com.ohci.hello_spring_boot.repository.Entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderItemEntity,Long> {
    @Query("SELECT o FROM OrderItemEntity o JOIN o.user u WHERE u.id =: id")
    List<OrderItemEntity> findByUserId(@Param("id") Long id);

    @Query("SELECT p FROM ProductsEntity p JOIN p.orderItemEntity o WHERE o.id =: id")
    Optional<ProductsEntity> findByOrderId(Long id);

    void deleteByIdIn(List<Long> ids);
}
