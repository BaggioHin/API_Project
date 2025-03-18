package com.ohci.hello_spring_boot.DTO.respone;


import lombok.*;

@Data  // Tự động tạo getter, setter, toString, equals, hashCode
@AllArgsConstructor // Tạo constructor có tham số
@NoArgsConstructor  // Tạo constructor không tham số
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
