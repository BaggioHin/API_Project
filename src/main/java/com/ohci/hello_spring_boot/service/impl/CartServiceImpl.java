package com.ohci.hello_spring_boot.service.impl;

import com.ohci.hello_spring_boot.DTO.request.CartRequest;
import com.ohci.hello_spring_boot.DTO.respone.CartResponse;
import com.ohci.hello_spring_boot.DTO.respone.ProductResponse;
import com.ohci.hello_spring_boot.Mapper.CartMapper;
import com.ohci.hello_spring_boot.Mapper.ProductsMapper;
import com.ohci.hello_spring_boot.repository.CartRepository;
import com.ohci.hello_spring_boot.repository.Entity.CartItemEntity;
import com.ohci.hello_spring_boot.repository.Entity.ProductsEntity;
import com.ohci.hello_spring_boot.repository.Entity.UserEntity;
import com.ohci.hello_spring_boot.repository.ProductRepository;
import com.ohci.hello_spring_boot.repository.UserRepository;
import com.ohci.hello_spring_boot.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductsMapper productsMapper ;

    @Override
    @PreAuthorize("hasAuthority('SCOPE_USER') and isAuthenticated()")
    public List<CartResponse> getProducts() {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        UserEntity user = userRepository.findByUserName(username).get();
//        UserEntity user = userRepository.findById(userId).get();
        List<CartItemEntity> cartItemEntity = cartRepository.findByUserId(user.getId());
        List<CartResponse> cartResponses = new ArrayList<>();
        for (CartItemEntity cartItem : cartItemEntity) {
            cartResponses.add(cartMapper.toCart(cartItem));
        }
        return cartResponses;
    }
// Khong can thiet
    @Override
    @PreAuthorize("hasAuthority('SCOPE_USER') and isAuthenticated()")
    public ProductResponse getProductsCart(Long id) {
        Optional<ProductsEntity> cartItemEntity = cartRepository.findByCartId(id);
        ProductsEntity item = cartItemEntity.get();
        return productsMapper.toRespone(item);
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_USER') and isAuthenticated()")
    public CartResponse addProductsCart(Long productId, CartRequest cartRequest) {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        UserEntity userEntity = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProductsEntity productsEntity = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Tạo mới CartItemEntity
        CartItemEntity cartItemEntity = CartItemEntity.builder()
                .name(productsEntity.getName())
                .user(userEntity)
                .product(productsEntity)
                .quantity(cartRequest.getQuantity())
                .totalPrice(productsEntity.getPrice() * cartRequest.getQuantity()) // nếu có giá
                .createdAt(LocalDate.now())
                .thumbnailUrl(productsEntity.getThumbnailUrl())
                .build();

        // Lưu cart item vào database
        cartItemEntity = cartRepository.save(cartItemEntity);

        return cartMapper.toCart(cartItemEntity);
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_USER') and isAuthenticated()")
    public CartResponse update(Long cartId, CartRequest cartRequest) {
        CartItemEntity cartItemEntity = cartRepository.findById(cartId).get();
        cartMapper.toCartItemEntity(cartRequest, cartItemEntity);
        return cartMapper.toCart(cartRepository.save(cartItemEntity));
    }

    @Override
    public void updateFollowProgram(List<CartItemEntity> cartItemEntity,Double price) {
        for(CartItemEntity cartItem : cartItemEntity){
            var quantity = cartItem.getQuantity();
            cartItem.setTotalPrice(price*quantity);
            cartRepository.save(cartItem);
        }
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_USER') and isAuthenticated()")
    public void deleteAll() {
        cartRepository.deleteAll();
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_USER') and isAuthenticated()")
    public void delete(Long cartId) {
//        cartRepository.deleteAllById(cartId);
        cartRepository.deleteById(cartId);
    }
}
