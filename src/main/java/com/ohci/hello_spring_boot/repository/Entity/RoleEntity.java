package com.ohci.hello_spring_boot.repository.Entity;

import jakarta.persistence.*;
import lombok.*;


import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity {
    @Id
    String name;
    String description;

    @ManyToMany
    Set<PermissionEntity> permissions;
}
