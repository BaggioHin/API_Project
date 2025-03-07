package com.ohci.hello_spring_boot.repository.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
//    1
    private String name;

    private String branchName;

    private Double price;

    @Column(name="thumbnail_url",length = 500)
    private String thumbnailUrl;

    private Integer reviewCount;

    private Double ratingAverage;

    private Double discount;

    private Double discountRate;

    private Integer quantitySold;

    @ManyToOne
    @JoinColumn(name = "categoty_id")
    private CategoryEntity category;

    @OneToOne
    @JoinColumn(name = "order_id")
    private OrderItemEntity orderItemEntity;

    @OneToOne
    @JoinColumn(name = "cart_id")
    private CartItemEntity cartItemEntity;
}
