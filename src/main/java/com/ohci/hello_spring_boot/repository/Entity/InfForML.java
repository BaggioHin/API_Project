package com.ohci.hello_spring_boot.repository.Entity;

import jakarta.persistence.Entity;
import lombok.*;

import java.util.List;

//@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InfForML {
    private String query;
    private List<CartItemEntity> cartItemEntityList;
}
