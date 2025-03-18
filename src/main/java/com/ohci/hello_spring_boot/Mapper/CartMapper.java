package com.ohci.hello_spring_boot.Mapper;

import com.ohci.hello_spring_boot.DTO.request.CartRequest;
import com.ohci.hello_spring_boot.DTO.respone.CartResponse;
import com.ohci.hello_spring_boot.repository.Entity.CartItemEntity;
import com.ohci.hello_spring_boot.repository.Entity.ProductsEntity;
import org.mapstruct.*;

import javax.xml.transform.Source;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "id",target="id")
    @Mapping(source = "thumbnailUrl", target = "thumbnailUrl")
    CartResponse toCart(CartItemEntity cartItemEntity);
    CartItemEntity toCartItemEntity(CartRequest cartRequest,@MappingTarget CartItemEntity cartItemEntity);
}
