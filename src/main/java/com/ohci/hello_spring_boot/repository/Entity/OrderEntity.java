//package com.ohci.hello_spring_boot.repository.Entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.util.Date;
//import java.util.Set;
//
//@Entity
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
//@ToString
//@Builder
//public class OrderEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @OneToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    private UserEntity user;
//
//    @Column(name = "Created_at")
//    private Date createdAt;
//
//    @ManyToMany
//    private Set<OrderItemEntity> orderItems;
//}
