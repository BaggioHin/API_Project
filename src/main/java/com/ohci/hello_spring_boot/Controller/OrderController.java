package com.ohci.hello_spring_boot.Controller;

import com.ohci.hello_spring_boot.DTO.request.OrderRequest;
import com.ohci.hello_spring_boot.DTO.respone.ApiResponse;
import com.ohci.hello_spring_boot.DTO.respone.OrderResponse;
import com.ohci.hello_spring_boot.DTO.respone.ProductResponse;
import com.ohci.hello_spring_boot.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("user/{user_id}")
    public ApiResponse<OrderResponse> getCart(@PathVariable Long user_id) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.getProducts(user_id))
                .build();
    }

    @GetMapping(value = "/{order_id}")
    public ApiResponse<ProductResponse> GetProductCard(@PathVariable Long user_id) {
        ProductResponse getProductsCard = orderService.getProductsCart(user_id);
        return ApiResponse.<ProductResponse>builder()
                .result(getProductsCard)
                .build();
    }
    //    Thêm các sản phẩm trong giỏ hangf
    @PostMapping(value = "/{user_id}/{product_id}")
    public ApiResponse<OrderResponse> AddProductCard(@PathVariable("product_id") Long id,
                                                    @PathVariable("user_id") Long user_id,
                                                    OrderRequest request) {
        OrderResponse addProductOrder = orderService.addProductsCart(id,user_id,request);
        return ApiResponse.<OrderResponse>builder()
                .result(addProductOrder)
                .build();
    }
    //    Cập nhật các sản phẩm trong giỏ hàng
    @PutMapping(value = "/{user_id}/{order_id}")
    public ApiResponse<OrderResponse> updateCart(@PathVariable("order_id") Long order_id
            ,@RequestBody OrderRequest orderRequest,@PathVariable Long user_id) {
        OrderResponse  orderResponse = orderService.update(order_id,orderRequest,user_id);
        return ApiResponse.<OrderResponse>builder()
                .result(orderResponse)
                .build();
    }
    //    Xóa toàn bộ cac sản phẩm trong giỏ hàng
    @DeleteMapping(value = "/{user_id}")
    public void deleteCart(@PathVariable Long user_id) {
        orderService.deleteAll(user_id);
    }
    //  Xóa các sản phẩm trong giỏ hàng
    @DeleteMapping("/{user_id}/cart")
    public void deleteAllCart(@RequestParam List<Long> cart_id,@PathVariable Long user_id) {
        orderService.delete(cart_id,user_id);
    }

}
