package com.ohci.hello_spring_boot.Mapper;

import com.ohci.hello_spring_boot.DTO.request.ProductRequest;
import com.ohci.hello_spring_boot.DTO.respone.ProductResponse;
import com.ohci.hello_spring_boot.repository.Entity.ProductsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface ProductsMapper {
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "categoryId", ignore = true)
//    @Mapping(target = "orderItemId", ignore = true)
    @Mapping(source = "thumbnailUrl", target = "thumbnailUrl")
    ProductResponse toRespone(ProductsEntity productsEntity);
    ProductsEntity toProductsEntity(ProductRequest productRequest);
    //    List<ProductRespone> toResponeList(List<ProductsEntity> productsEntities);
//    List<ProductsEntity> toEntityList(List<ProductRespone> productResponeList);
}

