package com.PowerBike.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private String productName;
    private String description;
    private String price;
    private int stock;
    private double discount;
    private String image;
    private boolean isActive;
}
