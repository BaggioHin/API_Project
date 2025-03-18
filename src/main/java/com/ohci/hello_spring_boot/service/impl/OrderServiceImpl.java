package com.ohci.hello_spring_boot.service.impl;

import com.ohci.hello_spring_boot.DTO.request.CartRequest;
import com.ohci.hello_spring_boot.DTO.request.OrderRequest;
import com.ohci.hello_spring_boot.DTO.respone.CartResponse;
import com.ohci.hello_spring_boot.DTO.respone.OrderResponse;
import com.ohci.hello_spring_boot.DTO.respone.ProductResponse;
import com.ohci.hello_spring_boot.Mapper.OrderMapper;
import com.ohci.hello_spring_boot.Mapper.ProductsMapper;
import com.ohci.hello_spring_boot.repository.Entity.CartItemEntity;
import com.ohci.hello_spring_boot.repository.Entity.OrderItemEntity;
import com.ohci.hello_spring_boot.repository.Entity.ProductsEntity;
import com.ohci.hello_spring_boot.repository.Entity.UserEntity;
import com.ohci.hello_spring_boot.repository.OrderRepository;
import com.ohci.hello_spring_boot.repository.ProductRepository;
import com.ohci.hello_spring_boot.repository.UserRepository;
import com.ohci.hello_spring_boot.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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
//    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @PreAuthorize("hasAuthority('SCOPE_USER') and isAuthenticated()")
    public List<OrderResponse> getProducts() {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        UserEntity user = userRepository.findByUserName(username).get();
//        UserEntity user = userRepository.findById(userId).get();
        List<OrderItemEntity> orderItemEntity = orderRepository.findByUserId(user.getId());
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (OrderItemEntity orderItem : orderItemEntity) {
            orderResponses.add(orderMapper.toOrderResponse(orderItem));
        }
        return orderResponses;
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_USER') and isAuthenticated()")
    public ProductResponse getProductsCart(Long id) {
        ProductsEntity productsEntity = orderRepository.findByOrderId(id).get();
        return productsMapper.toRespone(productsEntity);
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_USER') and isAuthenticated()")
    public OrderResponse addProductsCart(Long productId, OrderRequest orderRequest) {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        UserEntity userEntity = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProductsEntity productsEntity = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Tạo mới CartItemEntity
        OrderItemEntity orderItemEntity = OrderItemEntity.builder()
                .name(productsEntity.getName())
                .user(userEntity)
                .product(productsEntity)
                .quantity(orderRequest.getQuantity())
                .totalPrice(productsEntity.getPrice() * orderRequest.getQuantity()) // nếu có giá
                .createdAt(LocalDate.now())
                .build();

        // Lưu cart item vào database
        orderItemEntity = orderRepository.save(orderItemEntity);

        return orderMapper.toOrderResponse(orderItemEntity);
    }

//    @Override
//    @PreAuthorize("hasAuthority('SCOPE_USER') and #userId == authentication.principal.id")
//    public OrderResponse update(Long order_id,OrderRequest orderRequest,Long userId) {
//        OrderItemEntity orderItemEntity = orderRepository.findById(order_id).get();
//        orderMapper.toOderItemEntity(orderRequest,orderItemEntity);
//        return orderMapper.toOrderResponse(orderRepository.save(orderItemEntity));
//    }

    @PreAuthorize("hasAuthority('SCOPE_USER') and isAuthenticated()")
    @Override
    public void deleteAll(Long id) {orderRepository.deleteAll();}

    @PreAuthorize("hasAuthority('SCOPE_USER') and isAuthenticated()")
    @Override
    public void delete(List<Long> ids,Long userId) {
        orderRepository.deleteByIdIn(ids);
    }
}
