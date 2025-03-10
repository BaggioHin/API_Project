package com.ohci.hello_spring_boot.service;


import com.ohci.hello_spring_boot.DTO.request.CartRequest;
import com.ohci.hello_spring_boot.DTO.respone.CartResponse;
import com.ohci.hello_spring_boot.DTO.respone.ProductResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public interface CartService {
    CartResponse getProduct(Long productId);
    ProductResponse getProductsCart(Long id);
    CartResponse addProductsCart(Long id, Long userId, CartRequest cartRequest);
    CartResponse update(Long cartId, CartRequest cartRequest);
    CartResponse updateFollowProgram(int id);
    void deleteAll();
    void delete(List<Long> cartId);
//    CartResponse removeProductsCard(Long id, int quantity, LocalDate created);
}
