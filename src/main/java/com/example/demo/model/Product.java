package com.example.demo.model;

import com.example.demo.enums.ProductType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String size;
    private String color;
    private Double price;
    private String type;
    private String fabric;
    private Integer quantity;
    private String description;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "orders_Id")
    @JsonBackReference
    private Orders orders;
}
