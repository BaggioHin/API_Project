package com.ohci.hello_spring_boot.repository.custom.impl;

import com.ohci.hello_spring_boot.repository.custom.ProductRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

//    @Override
//    public List<ProductsEntity> findTopProductPromotion() {
//        String sql = "SELECT p FROM ProductsEntity p ORDER BY p.discountRate DESC, p.discount DESC";
////        List<ProductsEntity> productsPromotion();
//        TypedQuery<ProductsEntity> query = entityManager.createQuery(sql, ProductsEntity.class);
//        List<ProductsEntity> products= query.getResultList();
////        List<ProductRespone> result = new ArrayList<ProductRespone>();
//        return products;
////        return List.of();
//    }

//    @Override
//    public List<ProductsEntity> getAllProductsPromotion() {
//        return List.of();
//    }
}
