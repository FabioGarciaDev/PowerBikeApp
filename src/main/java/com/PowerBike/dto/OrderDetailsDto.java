package com.PowerBike.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsDto {

    private Long orderId;
    private Long productId;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal discountPercentage;
    private BigDecimal cashValue;
    private BigDecimal totalPrice;
}
