package com.ohci.hello_spring_boot.Mapper;

import com.ohci.hello_spring_boot.DTO.request.CategoryRequest;
import com.ohci.hello_spring_boot.DTO.respone.CategoryResponse;
import com.ohci.hello_spring_boot.repository.Entity.CategoryEntity;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    void updateUserFromRequest(CategoryRequest request, @MappingTarget CategoryEntity categoryRequest);
    CategoryEntity createCategoryFromRequest(CategoryRequest request);
    CategoryResponse toCategoryResponse(CategoryEntity categoryItem);
}
