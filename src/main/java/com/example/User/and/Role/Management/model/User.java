package com.example.User.and.Role.Management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    JPA entities should use Long because it can be null.
//    Before persistence, the ID must be null so Hibernate knows it has to generate it.
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String status;

    private Long createdBy;  // will store the user Id

    @CreationTimestamp      // automatically creates the insertion time for the data
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private Long updatedBy;

    @UpdateTimestamp
    private LocalDateTime updatedAt; // automatically update the updation time to the data
}
