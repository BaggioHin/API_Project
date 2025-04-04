package com.ohci.hello_spring_boot.Controller;

import com.ohci.hello_spring_boot.DTO.request.CartRequest;
import com.ohci.hello_spring_boot.DTO.respone.ApiResponse;
import com.ohci.hello_spring_boot.DTO.respone.CartResponse;
import com.ohci.hello_spring_boot.DTO.respone.ProductResponse;
import com.ohci.hello_spring_boot.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    CartService cartService;

    //    Xem các item của giỏ hàng
    @GetMapping("/user")
    public ApiResponse<List<CartResponse>> getCart() {
        return ApiResponse.<List<CartResponse>>builder()
                .result(cartService.getProducts())
                .build();
    }

//    Xem thông tin của cartItem
    @GetMapping(value = "/{cart_id}")
    public ApiResponse<ProductResponse> GetProductCard(@PathVariable Long user_id) {
        ProductResponse getProductsCart = cartService.getProductsCart(user_id);
        return ApiResponse.<ProductResponse>builder()
                .result(getProductsCart)
                .build();
    }

    //    Thêm các sản phẩm trong giỏ hangf
    @PostMapping(value = "/{product_id}")
    public ApiResponse<CartResponse> addProductCart(@PathVariable("product_id") Long productId,
                                                    @RequestBody CartRequest request) {
        CartResponse addProductsCart = cartService.addProductsCart( productId, request);
        return ApiResponse.<CartResponse>builder()
                .result(addProductsCart)
                .build();
    }

    //    Cập nhật các sản phẩm trong giỏ hàng
    @PutMapping(value = "/{cart_id}")
    public ApiResponse<CartResponse> updateCart(@PathVariable("cart_id") Long cart_id
            ,@RequestBody CartRequest cartRequest) {
        CartResponse  cartResponse = cartService.update(cart_id,cartRequest);
        return ApiResponse.<CartResponse>builder()
                .result(cartResponse)
                .build();
    }
//    Xóa toàn bộ cac sản phẩm trong giỏ hàng
    @DeleteMapping
    public void deleteCart(){
        cartService.deleteAll();
    }
//  Xóa các sản phẩm trong giỏ hàng
    @DeleteMapping("/{Id}")
    public void deleteAllCart(@PathVariable("Id") Long Id){
        cartService.delete(Id);
    }
}
