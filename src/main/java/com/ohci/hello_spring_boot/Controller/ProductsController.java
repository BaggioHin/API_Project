package com.ohci.hello_spring_boot.Controller;

import com.ohci.hello_spring_boot.DTO.request.ProductRequest;
import com.ohci.hello_spring_boot.DTO.respone.AiRespone;
import com.ohci.hello_spring_boot.DTO.respone.ApiResponse;
import com.ohci.hello_spring_boot.DTO.respone.ProductResponse;
import com.ohci.hello_spring_boot.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3001")
public class ProductsController {
    @Autowired
    ProductService productService;

    @Autowired
    private DataSource dataSource;


    @GetMapping("/check")
    public String checkDatabaseConnection() {
        try (Connection conn = dataSource.getConnection()) {
            return "✅ Đã kết nối thành công với database!";
        } catch (SQLException e) {
            return "❌ Lỗi kết nối database: " + e.getMessage();
        }
    }

    @GetMapping("/name/{name}")
    public ApiResponse<List<ProductResponse>> getProductByName(@PathVariable("name") String name) {
        List<ProductResponse> productResponses = productService.getProductByName(name);
        return ApiResponse.<List<ProductResponse>>builder().result(productResponses).build();
    }


    @PostMapping("/recommendation/{query}")
    public ApiResponse<List<AiRespone>> getProductByRecommendation(@PathVariable("query") String name) {
        List<AiRespone> nameProducts = productService.getProductName(name);
        return ApiResponse.<List<AiRespone>>builder().result(nameProducts).build();
    }

//   Sau khi click vào sản phẩm thì nó sẽ hiện thông tin cụ thể hơn về sản phẩm
    @GetMapping(value = "/{id}")
    public ApiResponse<ProductResponse> getProduct(@PathVariable("id") Long product_id) {
        ProductResponse productRespone = productService.getProduct(product_id);
        return ApiResponse.<ProductResponse>builder()
                .result(productRespone)
                .build();
    }

//  Sau khi click trên giao diện phần thư mục thì nó sẽ hiện lên các sản phẩm trên
    @GetMapping(value = "/category/{category_id}")
    public ApiResponse<List<ProductResponse>> getAllProducts(@PathVariable("category_id") Long category_id) {
        List<ProductResponse> products = productService.getAllProductsByCategoryId(category_id);
        return ApiResponse.<List<ProductResponse>>builder().result(products).build();
    }

//    Lấy hết tất cả sản phâmr
    @GetMapping("/allProducts")
    public ApiResponse<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> products = productService.getAllProducts();
        return ApiResponse.<List<ProductResponse>>builder().result(products).build();
    }

    @PostMapping
    public ApiResponse<ProductResponse> addProduct(@RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = productService.addProduct(productRequest);
        return ApiResponse.<ProductResponse>builder()
                .result(productResponse)
                .build();
    }

    @PutMapping(value = "/{id}")
    public ApiResponse<ProductResponse> updateProduct(@PathVariable("id") Long id, @RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = productService.update(id,productRequest);
        return ApiResponse.<ProductResponse>builder()
                .result(productResponse)
                .build();
    }

    @DeleteMapping("/delete")
    public  ApiResponse<String> deleteProduct(@RequestBody List<Long> ids) {
        String result = productService.deleteProduct(ids);
        return ApiResponse.<String>builder()
                .result(result)
                .build();
    }

    @DeleteMapping
    public void deleteAllProduct(){
        productService.deleteAllProducts();
    }

////  Phần thông tin hiện thị các sản phẩm ở mục các sản phẩm được khuyến mãi
//    @GetMapping(value="promotion")
//    public List<ProductRespone> topPromotionProduct() {
//        List<ProductRespone> products = productService.getAllProductsPromotion();
//        return products;
//    }

//  Phần thông tin hiện thị các sản phẩm ở mục giỏ hàng
}
