package com.ohci.hello_spring_boot.Controller;


import com.ohci.hello_spring_boot.DTO.request.CategoryRequest;
import com.ohci.hello_spring_boot.DTO.request.ProductRequest;
import com.ohci.hello_spring_boot.DTO.respone.ApiResponse;
import com.ohci.hello_spring_boot.DTO.respone.CategoryResponse;
import com.ohci.hello_spring_boot.DTO.respone.ProductResponse;
import com.ohci.hello_spring_boot.service.CategorySevice;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategorySevice categorySevice;

    @GetMapping("/{category_id}")
    public ApiResponse<List<ProductResponse>> getProducts(@PathVariable Long category_id) {
        return ApiResponse.<List<ProductResponse>>builder()
                .result(categorySevice.getProducts(category_id))
                .build();
    }

    @PutMapping("/{category_id}")
    public ApiResponse<CategoryResponse> updateProduct(@PathVariable Long category_id
            , @RequestBody CategoryRequest categoryRequest) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categorySevice.updateCategory(category_id,categoryRequest))
                .build();
    }

    @PostMapping
    public ApiResponse<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categorySevice.addCategory(categoryRequest))
                .build();
    }

    @DeleteMapping("/{category_id}")
    public void deleteCategory(@PathVariable Long category_id) {
        categorySevice.deleteCategory(category_id);
    }
}
