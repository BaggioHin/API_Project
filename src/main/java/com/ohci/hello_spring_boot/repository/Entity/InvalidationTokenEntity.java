package com.ohci.hello_spring_boot.repository.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class InvalidationTokenEntity {
    @Id
    String id;
    Date expiryTime;
}
