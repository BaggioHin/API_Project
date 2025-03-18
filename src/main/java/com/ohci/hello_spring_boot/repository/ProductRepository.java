package com.ohci.hello_spring_boot.repository;

import com.ohci.hello_spring_boot.repository.Entity.ProductsEntity;
import com.ohci.hello_spring_boot.repository.custom.ProductRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
//@Component
public interface ProductRepository extends JpaRepository<ProductsEntity, Long>{
    Optional<ProductsEntity> findById(Long id);

    @Query(value = "SELECT p.* FROM products p JOIN category c ON p.category_id = c.id WHERE p.category_id = :categoryId",
            nativeQuery = true)
    List<ProductsEntity> findAllByCategoryId(@Param("categoryId") Long categoryId);

    List<ProductsEntity> findAll();

    List<ProductsEntity> deleteByIdIn(List<Long> ids);

    @Query("SELECT p FROM ProductsEntity p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<ProductsEntity> findByName(@Param("name") String name);

//    List<ProductsEntity> findTopProductPromotion();
}
