package com.ohci.hello_spring_boot.service.impl;

import com.ohci.hello_spring_boot.DTO.request.ProductRequest;
import com.ohci.hello_spring_boot.DTO.respone.ProductResponse;
import com.ohci.hello_spring_boot.Mapper.ProductsMapper;
import com.ohci.hello_spring_boot.repository.Entity.ProductsEntity;
import com.ohci.hello_spring_boot.repository.ProductRepository;
import com.ohci.hello_spring_boot.service.CartService;
import com.ohci.hello_spring_boot.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    ProductsMapper productsConverter;

    @Autowired
    CartService cartService;

    @Override
    public ProductResponse getProduct(Long id) {
        Optional<ProductsEntity> productsEntity = productRepository.findById(id);
        ProductResponse productRespone = productsEntity.map(productsConverter::toRespone)
                .orElse(null);
        return  productRespone;// hoặc có thể thay bằng một lỗi cụ thể như RuntimeException
    }

    @Override
    public List<ProductResponse> getAllProductsByCategoryId(Long category_id) {
        List<ProductsEntity> entities = productRepository.findAllByCategoryId(category_id);
        List<ProductResponse> productResponses = new ArrayList<>();
        for (ProductsEntity entity : entities) {
            productResponses.add(productsConverter.toRespone(entity));
        }
        return productResponses;
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<ProductsEntity> ProductEntities = productRepository.findAll();
        List<ProductResponse> result = new ArrayList<>();
        for (ProductsEntity ProductEntity : ProductEntities) {
            ProductResponse ProductRespone = productsConverter.toRespone(ProductEntity);
            result.add(ProductRespone);
        }
        return result;
    }

    @Override
    public void deleteProduct(List<Long> ids) {
        productRepository.deleteByIdIn(ids);
    }

    @Override
    public void deleteAllProducts() {
        productRepository.deleteAll();
    }

    @Override
    public ProductResponse update(Long id, ProductRequest productRequest) {
        ProductsEntity productsEntity = productRepository.findById(id).get();
        ProductsEntity productEntity = productsConverter.toProductsEntity(productRequest);
        productRepository.save(productEntity);
        cartService.updateFollowProgram(productEntity.getId());
        return productsConverter.toRespone(productEntity);
    }
}
