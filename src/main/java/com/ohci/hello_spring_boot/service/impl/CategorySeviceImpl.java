package com.ohci.hello_spring_boot.service.impl;

import com.ohci.hello_spring_boot.DTO.request.CategoryRequest;
import com.ohci.hello_spring_boot.DTO.respone.CategoryResponse;
import com.ohci.hello_spring_boot.DTO.respone.ProductResponse;
import com.ohci.hello_spring_boot.Mapper.CategoryMapper;
import com.ohci.hello_spring_boot.Mapper.ProductsMapper;
import com.ohci.hello_spring_boot.repository.CategoryRespository;
import com.ohci.hello_spring_boot.repository.Entity.CategoryEntity;
import com.ohci.hello_spring_boot.repository.Entity.ProductsEntity;
import com.ohci.hello_spring_boot.service.CategorySevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategorySeviceImpl implements CategorySevice {

    @Autowired
    CategoryRespository categoryRespository;
    @Autowired
    ProductsMapper productsConverter;
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<ProductResponse> getProducts(Long id) {
        List<ProductsEntity> productsEntities = categoryRespository.findByItemId(id);
        List<ProductResponse> productResponses = new ArrayList<ProductResponse>();
        for (ProductsEntity productsEntity : productsEntities) {
            productResponses.add(productsConverter.toRespone(productsEntity));
        }
        return productResponses;
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest) {
        Optional<CategoryEntity> category = categoryRespository.findById(id);
        CategoryEntity categoryItem = category.get();
        categoryMapper.updateUserFromRequest(categoryRequest, categoryItem);
        return categoryMapper.toCategoryResponse(categoryItem);
    }

    @Override
    public CategoryResponse addCategory(CategoryRequest category) {
        CategoryEntity categoryEntity = categoryMapper.createCategoryFromRequest(category);
        return categoryMapper.toCategoryResponse(categoryRespository.save(categoryEntity));
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRespository.deleteById(id);
    }
}
