package com.PowerBike.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private String productName;
    private String description;
    private BigDecimal price;
    private int stock;
    private BigDecimal discount;
    private MultipartFile image;
    private boolean isActive;
}
