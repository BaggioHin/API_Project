package com.ohci.hello_spring_boot.DTO.request;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRequest {
    private Long Id;
    private String category_name;
    private String description;
}
