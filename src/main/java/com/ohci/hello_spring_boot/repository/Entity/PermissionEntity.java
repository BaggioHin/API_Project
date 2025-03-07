package com.ohci.hello_spring_boot.repository.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionEntity {
    @Id
    String name;
    String description;
}
