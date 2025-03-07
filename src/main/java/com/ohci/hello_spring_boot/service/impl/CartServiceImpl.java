package com.ohci.hello_spring_boot.service.impl;

import com.ohci.hello_spring_boot.DTO.request.CartRequest;
import com.ohci.hello_spring_boot.DTO.respone.CartResponse;
import com.ohci.hello_spring_boot.Mapper.CartMapper;
import com.ohci.hello_spring_boot.repository.CartRepository;
import com.ohci.hello_spring_boot.repository.Entity.CartItemEntity;
import com.ohci.hello_spring_boot.repository.Entity.ProductsEntity;
import com.ohci.hello_spring_boot.repository.ProductRepository;
import com.ohci.hello_spring_boot.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartMapper cartMapper;

    @Override
    public CartResponse getProduct(Long userId) {
//        UserEntity user = userRepository.findById(userId).get();
        CartItemEntity cartItemEntity = cartRepository.findByUserId(userId).get();
        return cartMapper.toCart(cartItemEntity);
    }

    @Override
    public CartResponse getAllProductsCard(Long id) {
        Optional<CartItemEntity> cartItemEntity = cartRepository.findById(id);
        CartItemEntity item = cartItemEntity.get();
        return cartMapper.toCart(item);
    }

    @Override
    public CartResponse addProductsCard(Long id, int quantity, LocalDate created) {
        ProductsEntity productsEntity = productRepository.findById(id).get();
        CartItemEntity cartItemEntity = CartItemEntity.builder()
                .name(productsEntity.getName())
                .quantity(quantity)
                .price(productsEntity.getPrice())
                .createdAt(created)
                .build();
        return cartMapper.toCart(cartRepository.saveAndFlush(cartItemEntity));
    }

    @Override
    public CartResponse update(Long cartId, CartRequest cartRequest) {
        CartItemEntity cartItemEntity = cartRepository.findById(cartId).get();
        cartMapper.toCartItemEntity(cartRequest, cartItemEntity);
        return cartMapper.toCart(cartRepository.saveAndFlush(cartItemEntity));
    }

    @Override
    public CartResponse updateFollowProgram(int id) {
        CartItemEntity cartItemEntity = cartRepository.findByIdProducts(id).get();
        return cartMapper.toCart(cartRepository.saveAndFlush(cartItemEntity));
    }

    @Override
    public void deleteAll() {
        cartRepository.deleteAll();
    }

    @Override
    public void delete(List<Long> cartId) {
//        cartRepository.deleteAllById(cartId);
        cartRepository.deleteByIdIn(cartId);
    }
}
