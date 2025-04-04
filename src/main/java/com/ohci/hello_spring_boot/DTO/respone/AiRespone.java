package com.ohci.hello_spring_boot.DTO.respone;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
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
    private String thumbnailUrl;
    private Double rating;
    private String category;
}
