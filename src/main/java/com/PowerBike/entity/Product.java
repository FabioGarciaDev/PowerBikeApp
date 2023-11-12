package com.PowerBike.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idProducts;
    private String productName;
    private String description;
    private String price;
    private int stock;
    private double discount;
    private String image;
    private Boolean activeProduct;
}
