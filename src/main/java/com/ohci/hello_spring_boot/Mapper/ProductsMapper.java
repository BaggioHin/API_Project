package com.ohci.hello_spring_boot.Mapper;

import com.ohci.hello_spring_boot.DTO.request.ProductRequest;
import com.ohci.hello_spring_boot.DTO.request.UserRequest;
import com.ohci.hello_spring_boot.DTO.respone.ProductResponse;
import com.ohci.hello_spring_boot.repository.Entity.ProductsEntity;
import com.ohci.hello_spring_boot.repository.Entity.UserEntity;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface ProductsMapper {
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "categoryId", ignore = true)
//    @Mapping(target = "orderItemId", ignore = true)
    @Mapping(source = "thumbnailUrl", target = "thumbnailUrl")
    ProductResponse toRespone(ProductsEntity productsEntity);
    @Mapping(source = "id",target="id")
    ProductsEntity toProductsEntity(ProductRequest productRequest);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProductFromRequest(ProductRequest request, @MappingTarget ProductsEntity product);
    //    List<ProductRespone> toResponeList(List<ProductsEntity> productsEntities);
//    List<ProductsEntity> toEntityList(List<ProductRespone> productResponeList);
}

