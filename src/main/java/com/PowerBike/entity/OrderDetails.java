package com.PowerBike.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@ToString(exclude = {"product", "orderEntity"})
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrderDetails;

    @JsonIgnore
    @ManyToOne(targetEntity = OrderEntity.class)
    @JoinColumn(name = "id_order")
    private OrderEntity orderEntity;

    @JsonIgnore
    @ManyToOne(targetEntity = Product.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_product")
    private Product product;

    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal discountPercentage;
    private BigDecimal cashValue;
    private BigDecimal totalPrice;


}
