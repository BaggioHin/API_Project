package com.ohci.hello_spring_boot.DTO.respone;


import lombok.*;

import java.util.Date;

@Data  // Tự động tạo getter, setter, toString, equals, hashCode
@AllArgsConstructor // Tạo constructor có tham số
@NoArgsConstructor  // Tạo constructor không tham số
@Builder
@Getter
@Setter
public class CartResponse {
    private Long id;
    private Integer quantity;
    private Double totalPrice;
    private Date createdAt;
    private String thumbnailUrl;
}
