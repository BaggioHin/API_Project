package com.ohci.hello_spring_boot.repository;

import com.ohci.hello_spring_boot.DTO.respone.ProductResponse;
import com.ohci.hello_spring_boot.repository.Entity.CategoryEntity;
import com.ohci.hello_spring_boot.repository.Entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRespository extends JpaRepository<CategoryEntity, Long> {
    @Query("select o from ProductsEntity o JOIN o.cartItemEntity c where c.id =:id")
    List<ProductsEntity> findByItemId(Long id);
}
