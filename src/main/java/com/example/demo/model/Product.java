package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

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

    @NotBlank
    private String title;
    @NotBlank
    private String size;
    private String color;
    private Double price;
    @NotBlank
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
