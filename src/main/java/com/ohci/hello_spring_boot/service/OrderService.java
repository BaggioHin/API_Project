package com.ohci.hello_spring_boot.service;

import com.ohci.hello_spring_boot.DTO.request.OrderRequest;
import com.ohci.hello_spring_boot.DTO.respone.OrderResponse;
import com.ohci.hello_spring_boot.DTO.respone.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    OrderResponse getProducts(Long id);
    ProductResponse getProductsCart(Long id);
    OrderResponse addProductsCart(Long id, Long userId, OrderRequest orderRequest);
    OrderResponse update(Long order_id,OrderRequest orderRequest,Long userId);
    void deleteAll(Long id);
    void delete(List<Long> ids,Long userId);
}
