package com.ohci.hello_spring_boot.repository.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer quantity;
    private Double price;
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

   @ManyToOne
   @JoinColumn(name="product_id")
    private ProductsEntity product;
}
