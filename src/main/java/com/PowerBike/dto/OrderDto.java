package com.PowerBike.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long clientId;
    private List<OrderDetailsDto> orderDetails;
    private LocalDateTime orderDate;
    private String shippingAddress;
    private BigDecimal totalPaid;
    private String commentsForDelivery;
    private String statusOrder;
}
