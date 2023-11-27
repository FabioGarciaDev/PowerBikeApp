package com.PowerBike.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
//@ToString(exclude = "client")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrderEntity;

    @JsonIgnore
    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client")
    private UserEntity client;

    @OneToMany(targetEntity = OrderDetails.class,cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, mappedBy = "orderEntity")
    private List<OrderDetails> orderDetails;

    private LocalDateTime orderDate;
    private String shippingAddress;
    private BigDecimal totalPaid;
    private String commentsForDelivery;
    private String statusOrder;

}
