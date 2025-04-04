package com.ohci.hello_spring_boot.DTO.respone;


import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CartResponse {
    private Long id;
    private String name;
    private String brandName;
    private Integer quantity;
    private Double totalPrice;
    private Date createdAt;
    private String thumbnailUrl;
}
