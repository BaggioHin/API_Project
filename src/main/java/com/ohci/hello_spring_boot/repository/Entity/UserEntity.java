package com.ohci.hello_spring_boot.repository.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String password;

    private String fullName;

    private String address;

    private String email;

    private Boolean isActive;

    private String phone;

    private Integer point;

    private String gender;

    private String user_rank;

    @ManyToMany
    @JoinTable(
            name = "user_entity_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_name")
    )
    private Set<RoleEntity> roles;

    @OneToMany(mappedBy = "user")
    private Set<CartItemEntity> cart;

    @OneToMany(mappedBy = "user")
    private Set<OrderItemEntity> order;
}
