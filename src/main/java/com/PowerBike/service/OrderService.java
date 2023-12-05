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
import jakarta.transaction.Transactional;
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

    public ResponseEntity<?> getOrder(Long id) {
        Optional<OrderEntity> product = orderRepository.findById(id);
        if (product.isPresent()) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        return new ResponseEntity<>("La orden no existe", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> getOrdersbyClient(Long id) {
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
        if (!optional.isPresent()) {
            return new ResponseEntity<>("El usuario no existe", HttpStatus.BAD_REQUEST);
        }
        UserEntity client = optional.get();
        List<OrderDetailsDto> orderDetailsList = orderDTO.getOrderDetails();
        List<OrderDetails> orderDetailsToAdd;
        BigDecimal totalPriceOrder = BigDecimal.valueOf(0);
        //Creo la orden
        OrderEntity order = OrderEntity.builder()
                .client(client)
                .orderDate(LocalDateTime.now())
                .shippingAddress(orderDTO.getShippingAddress())
                .totalPaid(orderDTO.getTotalPaid())
                .commentsForDelivery(orderDTO.getCommentsForDelivery())
                .statusOrder("CREATED")
                .build();
        //Creo la lista de detalles
        orderDetailsToAdd = createListOrderDetails(order, orderDetailsList);
        if (orderDetailsToAdd == null || orderDetailsToAdd.isEmpty()) {
            return new ResponseEntity<>("Stock de producto insuficiente", HttpStatus.BAD_REQUEST);
        }
        //calculo el total de los productos para el total de la orden
        for (OrderDetails details : orderDetailsToAdd) {
            totalPriceOrder = totalPriceOrder.add(details.getTotalPrice());
        }
        // Agrego la lista de details y el precio total a la order
        order.setOrderDetails(orderDetailsToAdd);
        order.setTotalPaid(totalPriceOrder);

        orderRepository.save(order);
        return new ResponseEntity<>(order, HttpStatus.OK);

    }

    public ResponseEntity<?> updateStatusOrder(Long idOrder, String status) {
        OrderEntity order = orderRepository.findById(idOrder).orElseThrow(null);
        String newStatus = status.toUpperCase();
        if (isValidStatus(newStatus) && order!=null){
            if ("CANCELADO".equals(newStatus)) {
                returnProducts(order.getOrderDetails());
                order.setStatusOrder(newStatus);
                orderRepository.save(order);
                return new ResponseEntity<>(order, HttpStatus.OK);
            } else if ("PAGADO".equals(newStatus) || "DESPACHADO".equals(status)) {
                if (order.getStatusOrder().equals("CANCELADO")){
                    return new ResponseEntity<>("No es posible modificar una order CANCELADA", HttpStatus.BAD_REQUEST);
                }
                order.setStatusOrder(newStatus);
                orderRepository.save(order);
                return new ResponseEntity<>(order, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("La orden con ID " + idOrder + " no existe o el estado proporcionado no es válido", HttpStatus.NOT_FOUND);

    }

    //Metodo para crear la lista de los details de la orden
    @Transactional
    public List<OrderDetails> createListOrderDetails(OrderEntity order, List<OrderDetailsDto> detailsList) {
        List<OrderDetails> listToAdd = new ArrayList<>();
        if (detailsList != null && !detailsList.isEmpty()) {
            for (OrderDetailsDto detail : detailsList) {
                int quantityProductDetails = detail.getQuantity();
                Product product = productRepository.findById(detail.getProductId())
                        .orElseThrow();
                if (product.getStock() < quantityProductDetails) {
                    return null;
                }
                BigDecimal discountValue =
                        (product.getPrice().multiply(product.getDiscount()).divide(BigDecimal.valueOf(100)));

                OrderDetails orderDetail = OrderDetails.builder()
                        .orderEntity(order)
                        .product(product)
                        .quantity(quantityProductDetails)
                        .unitPrice(product.getPrice())
                        .discountPercentage(product.getDiscount())
                        .cashValue(discountValue)
                        .totalPrice((product.getPrice().subtract(discountValue)).multiply(BigDecimal.valueOf(detail.getQuantity())))
                        .build();
                listToAdd.add(orderDetail);
                product.setStock(product.getStock() - quantityProductDetails);
                productRepository.save(product);
            }
        }
        return listToAdd;
    }

    private void returnProducts(List<OrderDetails> orderDetails) {
        for (OrderDetails detail : orderDetails) {
            Product product = productRepository.findById(detail.getProduct().getIdProducts()).get();
            product.setStock(product.getStock() + detail.getQuantity());
            productRepository.save(product);
        }
    }

    private boolean isValidStatus(String status) {
        // Por ejemplo, verificar que el estado sea uno de los valores permitidos (creado, cancelado, pagado, despachado)
        List<String> allowedStatusList = List.of("CREADO", "CANCELADO", "PAGADO", "DESPACHADO");
        return allowedStatusList.contains(status.toUpperCase());
    }
}
