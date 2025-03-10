package com.ohci.hello_spring_boot.Controller;

import com.ohci.hello_spring_boot.DTO.request.CartRequest;
import com.ohci.hello_spring_boot.DTO.respone.ApiResponse;
import com.ohci.hello_spring_boot.DTO.respone.CartResponse;
import com.ohci.hello_spring_boot.DTO.respone.ProductResponse;
import com.ohci.hello_spring_boot.Mapper.CartMapper;
import com.ohci.hello_spring_boot.repository.CartRepository;
import com.ohci.hello_spring_boot.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    CartService cartService;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartMapper cartMapper;

    //    Xem các item của giỏ hàng
    @GetMapping("user/{user_id}")
    public ApiResponse<CartResponse> getCart(@PathVariable Long user_id) {
        return ApiResponse.<CartResponse>builder()
                .result(cartService.getProduct(user_id))
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
    @PostMapping(value = "/{user_id}/{product_id}")
    public ApiResponse<CartResponse> AddProductCard(@PathVariable("product_id") Long id,
                                                    @PathVariable("user_id") Long user_id,
                                                    CartRequest request) {
        CartResponse addProductsCard = cartService.addProductsCart(id,user_id,request);
        return ApiResponse.<CartResponse>builder()
                .result(addProductsCard)
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
    @DeleteMapping("/cart")
    public void deleteAllCart(@RequestParam List<Long> user_id){
        cartService.delete(user_id);
    }
}
