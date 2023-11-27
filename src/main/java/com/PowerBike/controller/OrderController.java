package com.PowerBike.controller;

import com.PowerBike.dto.OrderDto;
import com.PowerBike.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    @GetMapping(value = "/getAll")
    public ResponseEntity<?> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/getOrder/{id}")
    public ResponseEntity<?> getOrder(@PathVariable long id) {
        return orderService.getOrder(id);
    }

    @GetMapping("/getOrderByClient/{idClient}")
    public ResponseEntity<?> getOrderByClient(@PathVariable long idClient) {
        return orderService.getOrdersbyClient(idClient);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<?> createOrder(@RequestBody OrderDto dto) {
        return orderService.createOrder(dto);
    }

}
