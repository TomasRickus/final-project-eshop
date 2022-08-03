package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Component
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(min = 4)
    private String username;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Size(min = 4)
    private String password;
    private Boolean enabled;

    @OneToOne(mappedBy = "customer", cascade=CascadeType.ALL)
    private Authority authority;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Orders> orders;

}
