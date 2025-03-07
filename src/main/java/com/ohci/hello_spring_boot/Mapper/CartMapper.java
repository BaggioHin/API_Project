package com.ohci.hello_spring_boot.Mapper;

import com.ohci.hello_spring_boot.DTO.request.CartRequest;
import com.ohci.hello_spring_boot.DTO.respone.CartResponse;
import com.ohci.hello_spring_boot.repository.Entity.CartItemEntity;
import com.ohci.hello_spring_boot.repository.Entity.ProductsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartResponse toCart(CartItemEntity cartItemEntity);
    CartItemEntity toCartItemEntity(CartRequest cartRequest,@MappingTarget CartItemEntity cartItemEntity);
}
