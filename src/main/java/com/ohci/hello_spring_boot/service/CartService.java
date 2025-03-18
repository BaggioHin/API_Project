package com.ohci.hello_spring_boot.service;


import com.ohci.hello_spring_boot.DTO.request.CartRequest;
import com.ohci.hello_spring_boot.DTO.respone.CartResponse;
import com.ohci.hello_spring_boot.DTO.respone.ProductResponse;
import com.ohci.hello_spring_boot.repository.Entity.CartItemEntity;
import com.ohci.hello_spring_boot.repository.Entity.CategoryEntity;
import com.ohci.hello_spring_boot.repository.Entity.ProductsEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public interface CartService {
    List<CartResponse> getProducts();
    ProductResponse getProductsCart(Long id);
    CartResponse addProductsCart(Long productId, CartRequest cartRequest);
    CartResponse update(Long cartId, CartRequest cartRequest);
    void updateFollowProgram(List<CartItemEntity> cartItemEntity,Double price);
    void deleteAll();
    void delete(Long cartId);
//    CartResponse removeProductsCard(Long id, int quantity, LocalDate created);
}
