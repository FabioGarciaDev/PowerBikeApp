package com.PowerBike.service;

import com.PowerBike.dto.OrderDetailsDto;
import com.PowerBike.dto.OrderDto;
import com.PowerBike.entity.OrderDetails;
import com.PowerBike.entity.OrderEntity;
import com.PowerBike.entity.Product;
import com.PowerBike.entity.UserEntity;
import com.PowerBike.repository.OrderRepository;
import com.PowerBike.repository.ProductRepository;
import com.PowerBike.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ResponseEntity<?> getAllOrders() {
        List<OrderEntity> orders = (List<OrderEntity>) orderRepository.findAll();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    public ResponseEntity<?> getOrder(long id) {
        Optional<OrderEntity> product = orderRepository.findById(id);
        if (product.isPresent()) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        return new ResponseEntity<>("La orden no existe", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> getOrdersbyClient(long id) {
        Optional<UserEntity> clientOptional = userRepository.findById(id);
        if (clientOptional.isPresent()) {
            UserEntity client = clientOptional.get();
            List<OrderEntity> ordersClient = orderRepository.findByClient(client);
            return new ResponseEntity<>(ordersClient, HttpStatus.OK);
        }
        return new ResponseEntity<>("El cliente no tiene pedidos", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> createOrder(OrderDto orderDTO) {
        Optional<UserEntity> optional = userRepository.findById(orderDTO.getClientId());
        List<OrderDetailsDto> orderDetailsList = orderDTO.getOrderDetails();
        List<OrderDetails> orderDetailToAdd = new ArrayList<>();

        if (optional.isPresent()) {
            UserEntity client = optional.get();
            OrderEntity order = OrderEntity.builder()
                    .client(client)
                    .orderDate(LocalDateTime.now())
                    .shippingAddress(orderDTO.getShippingAddress())
                    .totalPaid(orderDTO.getTotalPaid())
                    .commentsForDelivery(orderDTO.getCommentsForDelivery())
                    .statusOrder("CREATED")
                    .build();

            //itero la lista de productos y creo los details y los lamaceno en una lista
            if (orderDetailsList != null && !orderDetailsList.isEmpty()) {
                for (OrderDetailsDto orderDetailsDto : orderDetailsList) {
                    OrderDetails orderDetails = createOrderDetails(order, orderDetailsDto.getProductId(), orderDetailsDto.getQuantity());
                    orderDetailToAdd.add(orderDetails);
                }
            }
            // Agregar los detalles de pedido a la orden
            order.setOrderDetails(orderDetailToAdd);

            orderRepository.save(order);
            return new ResponseEntity<>("Pedido creado correctamente", HttpStatus.OK);
        }

        return new ResponseEntity<>("Error al crear el pedido", HttpStatus.BAD_REQUEST);
    }

    //Metodo para settear la order details
    private OrderDetails createOrderDetails(OrderEntity order, Long idProduct, int quantity) {
        Product product = productRepository.findById(idProduct)
                .orElseThrow();
        BigDecimal discountValue =
                (product.getPrice().multiply(product.getDiscount()).divide(BigDecimal.valueOf(100)));
        return OrderDetails.builder()
                .orderEntity(order)
                .product(product)
                .quantity(quantity)
                .unitPrice(product.getPrice())
                .discountPercentage(product.getDiscount())
                .cashValue(discountValue)
                .totalPrice((product.getPrice().subtract(discountValue)).multiply(BigDecimal.valueOf(quantity)))
                .build();
    }
}
