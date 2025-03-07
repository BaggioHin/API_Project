package com.ohci.hello_spring_boot.service;

import com.ohci.hello_spring_boot.DTO.request.ProductRequest;
import com.ohci.hello_spring_boot.DTO.respone.ProductResponse;
import com.ohci.hello_spring_boot.repository.Entity.ProductsEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


public interface ProductService {
    ProductResponse getProduct(Long id);
    List<ProductResponse> getAllProductsByCategoryId(Long category_id);
    List<ProductResponse> getAllProducts();
    void deleteProduct(List<Long> ids);
    void deleteAllProducts();
    ProductResponse update(Long id, @RequestBody ProductRequest productRequest);
//    List<ProductRespone> getAllProductsPromotion();
//    List<ProductRespone> getProductsCard();
}
