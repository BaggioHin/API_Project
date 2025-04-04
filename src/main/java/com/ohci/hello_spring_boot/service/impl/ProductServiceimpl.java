package com.ohci.hello_spring_boot.service.impl;

import com.ohci.hello_spring_boot.DTO.request.AIRequest;
import com.ohci.hello_spring_boot.DTO.request.ProductRequest;
import com.ohci.hello_spring_boot.DTO.respone.AiRespone;
import com.ohci.hello_spring_boot.DTO.respone.ProductResponse;
import com.ohci.hello_spring_boot.Mapper.ProductsMapper;
import com.ohci.hello_spring_boot.repository.CartRepository;
import com.ohci.hello_spring_boot.repository.CategoryRespository;
import com.ohci.hello_spring_boot.repository.Entity.CartItemEntity;
import com.ohci.hello_spring_boot.repository.Entity.CategoryEntity;
import com.ohci.hello_spring_boot.repository.Entity.ProductsEntity;
import com.ohci.hello_spring_boot.repository.Entity.UserEntity;
import com.ohci.hello_spring_boot.repository.ProductRepository;
import com.ohci.hello_spring_boot.repository.UserRepository;
import com.ohci.hello_spring_boot.service.CartService;
import com.ohci.hello_spring_boot.service.CategorySevice;
import com.ohci.hello_spring_boot.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceimpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductsMapper productsMapper;

    @Autowired
    CategorySevice categorySevice;

    @Autowired
    CartService cartService;

    @Autowired
    CategoryRespository categoryRespository;
    @Autowired
    private CategorySeviceImpl categorySeviceImpl;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;

    @Override
    public ProductResponse getProduct(Long id) {
        Optional<ProductsEntity> productsEntity = productRepository.findById(id);
        ProductResponse productRespone = productsEntity.map(productsMapper::toRespone)
                .orElse(null);
        return  productRespone;// hoặc có thể thay bằng một lỗi cụ thể như RuntimeException
    }

    @Override
    public List<ProductResponse> getProductByName(String name) {
        List<ProductsEntity> productsEntities = productRepository.findByName(name);
        List<ProductResponse> productResponses = new ArrayList<>();
        for (ProductsEntity productsEntity : productsEntities) {
            productResponses.add(productsMapper.toRespone(productsEntity));
        }
        return productResponses;
    }


    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<AiRespone> getProductName(String name) {
        final String AI_SERVICE_URL = "https://johnwickcp-test.hf.space/search/";

        // Lấy thông tin user từ context
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        UserEntity user = userRepository.findByUserName(username).get();

        // Lấy danh sách sản phẩm trong giỏ hàng
        List<CartItemEntity> cartItemEntityList = cartRepository.findByUserId(user.getId());
        if (cartItemEntityList.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Giỏ hàng trống!");
        }

        // Lấy danh sách tên sản phẩm
        List<String> productNames = cartItemEntityList.stream()
                .map(cartItem -> cartItem.getProduct().getName())
                .toList();

        // Tạo request gửi đến AI API
        AIRequest aiRequest = AIRequest.builder()
                .query(name)
                .favorite_products(productNames)
                .build();

        // Debug: Kiểm tra dữ liệu gửi lên API
        System.out.println("Sending request to AI API:");
        System.out.println("Query: " + aiRequest.getQuery());
        System.out.println("Product Names: " + aiRequest.getFavorite_products());

        // Gửi request bằng RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<AiRespone>> response = restTemplate.exchange(
                AI_SERVICE_URL,
                HttpMethod.POST,
                new HttpEntity<>(aiRequest),
                new ParameterizedTypeReference<List<AiRespone>>() {}
        );

        ResponseEntity<String> rawResponse = restTemplate.exchange(
                AI_SERVICE_URL,
                HttpMethod.POST,
                new HttpEntity<>(aiRequest),
                String.class
        );

        System.out.println("Raw AI Response: " + rawResponse.getBody());

        for(AiRespone aiRespone : response.getBody()) {
            String thumnailUrl = productRepository.findById(aiRespone.getId()).get().getThumbnailUrl();
            aiRespone.setThumbnailUrl(thumnailUrl);
        }
        // Kiểm tra dữ liệu nhận về
        if (response.getBody() != null) {
            System.out.println("AI Response:");
            response.getBody().forEach(System.out::println);
        }

        return response.getBody();
    }


    @Override
    public List<ProductResponse> getAllProductsByCategoryId(Long category_id) {
        List<ProductsEntity> entities = productRepository.findAllByCategoryId(category_id);
        List<ProductResponse> productResponses = new ArrayList<>();
        for (ProductsEntity entity : entities) {
            productResponses.add(productsMapper.toRespone(entity));
        }
        return productResponses;
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<ProductsEntity> ProductEntities = productRepository.findAll();
        List<ProductResponse> result = new ArrayList<>();
        for (ProductsEntity ProductEntity : ProductEntities) {
            ProductResponse ProductRespone = productsMapper.toRespone(ProductEntity);
            result.add(ProductRespone);
        }
        return result;
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ProductResponse addProduct(ProductRequest productRequest) {
        ProductsEntity productsEntity =productsMapper.toProductsEntity(productRequest);
        var id = productRequest.getCategoryId();
        productsEntity.setCategory(categoryRespository.findById(id).get());
        productRepository.save(productsEntity);
        CategoryEntity categoryEntity = categoryRespository.findById(productsEntity.getCategory().getId()).get();
        categorySeviceImpl.addProducts(categoryEntity,productsEntity);
        return productsMapper.toRespone(productsEntity);
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public String deleteProduct(List<Long> ids) {
        productRepository.deleteByIdIn(ids);
        System.out.println("Danh sách sản phẩm tìm thấy: " + ids.size());
        return "oke";
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void deleteAllProducts() {
        productRepository.deleteAll();
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ProductResponse update(Long id, ProductRequest productRequest) {
        ProductsEntity productEntity = productRepository.findById(id).get();
        productsMapper.updateProductFromRequest(productRequest,productEntity);
        productRepository.save(productEntity);
        cartService.updateFollowProgram(productEntity.getCartItemEntity(),productEntity.getPrice());
        return productsMapper.toRespone(productEntity);
    }
}
