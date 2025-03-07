package com.ohci.hello_spring_boot.Controller;


//import org.springframework.stereotype.Controller;
import com.ohci.hello_spring_boot.DTO.request.ProductRequest;
import com.ohci.hello_spring_boot.DTO.respone.ProductResponse;
import com.ohci.hello_spring_boot.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
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

//   Sau khi click vào sản phẩm thì nó sẽ hiện thông tin cụ thể hơn về sản phẩm
    @GetMapping(value = "{id}")
    public ProductResponse getProduct(@PathVariable("id") Long product_id) {
        ProductResponse productRespone = productService.getProduct(product_id);
        return productRespone;
    }

//  Sau khi click trên giao diện phần thư mục thì nó sẽ hiện lên các sản phẩm trên
    @GetMapping(value = "category/{category_id}")
    public List<ProductResponse> getAllProducts(@PathVariable("category_id") Long category_id) {
        List<ProductResponse> products = productService.getAllProductsByCategoryId(category_id);
        return products;
    }

//    Lấy hết tất cả sản phâmr
    @GetMapping
    public List<ProductResponse> getAllProducts() {
        List<ProductResponse> products = productService.getAllProducts();
        return products;
    }

    @PutMapping(value = "/{id}")
    public ProductResponse updateProduct(@PathVariable("id") Long id, @RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = productService.update(id,productRequest);
        return productResponse;
    }

    @DeleteMapping(value = "/products")
    public void deleteProduct(@RequestParam List<Long> ids) {
        productService.deleteProduct(ids);
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
