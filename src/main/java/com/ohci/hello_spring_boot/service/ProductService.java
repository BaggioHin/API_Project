package com.ohci.hello_spring_boot.service;

import com.ohci.hello_spring_boot.DTO.request.ProductRequest;
import com.ohci.hello_spring_boot.DTO.respone.AiRespone;
import com.ohci.hello_spring_boot.DTO.respone.ProductResponse;
import com.ohci.hello_spring_boot.repository.Entity.ProductsEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


public interface ProductService {
    ProductResponse getProduct(Long id);
    List<ProductResponse> getProductByName(String name);
    List<AiRespone> getProductName(String name);
    List<ProductResponse> getAllProductsByCategoryId(Long category_id);
    List<ProductResponse> getAllProducts();
    ProductResponse addProduct(ProductRequest productRequest);
    String deleteProduct(List<Long> ids);
    void deleteAllProducts();
    ProductResponse update(Long id,ProductRequest productRequest);
//    List<ProductRespone> getAllProductsPromotion();
//    List<ProductRespone> getProductsCard();
}
