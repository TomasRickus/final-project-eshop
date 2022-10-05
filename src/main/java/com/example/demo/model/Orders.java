package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double totalAmount;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Product> products;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private User user;

}