package com.ohci.hello_spring_boot.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {
    private String name;
    private String branchName;
    private Double price;
    private String thumbnailUrl;
//    private Integer reviewCount;
//    private Double ratingAverage;
    private Double discount;
//    private Double discountRate;
    private Integer quantity;
    private Long categoryId;
//    private Long orderItemId;
}
