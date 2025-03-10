package com.ohci.hello_spring_boot.service.impl;

import com.ohci.hello_spring_boot.DTO.request.OrderRequest;
import com.ohci.hello_spring_boot.DTO.respone.OrderResponse;
import com.ohci.hello_spring_boot.DTO.respone.ProductResponse;
import com.ohci.hello_spring_boot.Mapper.OrderMapper;
import com.ohci.hello_spring_boot.Mapper.ProductsMapper;
import com.ohci.hello_spring_boot.repository.Entity.OrderItemEntity;
import com.ohci.hello_spring_boot.repository.Entity.ProductsEntity;
import com.ohci.hello_spring_boot.repository.Entity.UserEntity;
import com.ohci.hello_spring_boot.repository.OrderRepository;
import com.ohci.hello_spring_boot.repository.ProductRepository;
import com.ohci.hello_spring_boot.repository.UserRepository;
import com.ohci.hello_spring_boot.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;

    OrderMapper orderMapper;

    ProductRepository productRepository;

    UserRepository userRepository;

    ProductsMapper productsMapper;

    @Override
    @PreAuthorize("hasRole('USER') and #id == authentication.principal.id")
    public OrderResponse getProducts(Long id) {
        OrderItemEntity orderItemEntity = orderRepository.findByIdProducts(id);
        return orderMapper.toOrderResponse(orderItemEntity);
    }

    @Override
    @PreAuthorize("hasRole('USER') and #id == authentication.principal.id")
    public ProductResponse getProductsCart(Long id) {
        ProductsEntity productsEntity = orderRepository.findByOrderId(id).get();
        return productsMapper.toRespone(productsEntity);
    }

    @Override
    @PreAuthorize("hasRole('USER') or #userId==authentication.principal.id")
    public OrderResponse addProductsCart(Long id, Long userId, OrderRequest orderRequest) {
        ProductsEntity productsEntity = productRepository.findById(id).get();
        UserEntity userEntity = userRepository.findById(userId).get();
        OrderItemEntity orderItemEntity = null;
        orderItemEntity = OrderItemEntity.builder()
                .name(productsEntity.getName())
                .user(userEntity)
                .product(productsEntity)
                .build();
        orderMapper.toOderItemEntity(orderRequest,orderItemEntity);
        userEntity.getOrder().add(orderItemEntity);
        userRepository.save(userEntity);
        productsEntity.getOrderItemEntity().add(orderItemEntity);
        productRepository.save(productsEntity);
        return orderMapper.toOrderResponse(orderRepository.save(orderItemEntity));
    }

    @Override
    @PreAuthorize("hasRole('USER') and #userId == authentication.principal.id")
    public OrderResponse update(Long order_id,OrderRequest orderRequest,Long userId) {
        OrderItemEntity orderItemEntity = orderRepository.findById(order_id).get();
        orderMapper.toOderItemEntity(orderRequest,orderItemEntity);
        return orderMapper.toOrderResponse(orderRepository.save(orderItemEntity));
    }

    @PreAuthorize("hasRole('USER') and #id == authentication.principal.id")
    @Override
    public void deleteAll(Long id) {orderRepository.deleteAll();}

    @PreAuthorize("hasRole('USER') and #id == authentication.principal.id")
    @Override
    public void delete(List<Long> ids,Long userId) {
        orderRepository.deleteByIdIn(ids);
    }
}
