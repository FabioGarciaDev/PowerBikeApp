package com.PowerBike.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducts;
    private String productName;
    private String description;
    private BigDecimal price;
    private int stock;
    private BigDecimal discount;
    private String image;
    private Boolean activeProduct;
}
