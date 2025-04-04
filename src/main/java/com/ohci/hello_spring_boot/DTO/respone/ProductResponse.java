package com.ohci.hello_spring_boot.DTO.respone;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private String brandName;
    private Double price;
    private String thumbnailUrl;
//    private Integer reviewCount;
//    private Float reviewRate;
    private Float ratingAverage;
    private Float discount;
    private Float discountRate;
    private Float quantitySold;
}
