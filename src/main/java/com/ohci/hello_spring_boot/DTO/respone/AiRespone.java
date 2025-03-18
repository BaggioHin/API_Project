package com.ohci.hello_spring_boot.DTO.respone;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ohci.hello_spring_boot.repository.Entity.ProductsEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AiRespone {
    private Long id;
    private String name;
    private String brand;
    private Double price;
    private String thumbnail_url;
    private Double rating;
    private String category;
}
