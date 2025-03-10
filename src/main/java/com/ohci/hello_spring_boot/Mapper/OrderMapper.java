package com.ohci.hello_spring_boot.Mapper;

import com.ohci.hello_spring_boot.DTO.request.OrderRequest;
import com.ohci.hello_spring_boot.DTO.respone.OrderResponse;
import com.ohci.hello_spring_boot.repository.Entity.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderResponse toOrderResponse(OrderItemEntity order);

    OrderItemEntity toOderItemEntity(OrderRequest request,@MappingTarget OrderItemEntity order);
}
