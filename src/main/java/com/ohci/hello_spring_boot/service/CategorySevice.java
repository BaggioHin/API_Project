package com.ohci.hello_spring_boot.service;

import com.ohci.hello_spring_boot.DTO.request.CategoryRequest;
import com.ohci.hello_spring_boot.DTO.respone.CategoryResponse;
import com.ohci.hello_spring_boot.DTO.respone.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategorySevice {
    List<ProductResponse> getProducts(Long id);
    CategoryResponse updateCategory(Long id, CategoryRequest category);
    CategoryResponse addCategory(CategoryRequest category);
    void deleteCategory(Long id);
}
