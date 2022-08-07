package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Component
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String userId;
    @NotBlank
    @Size(min = 4)
    private String username;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Size(min = 4)
    private String password;
    private String[] roles;
    private boolean enabled;
    private boolean isActive;
    private boolean isNotLocked;

    private String[] authorities;

    @OneToOne(mappedBy = "customer", cascade=CascadeType.ALL)
    private Authority authority;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Orders> orders;

}
