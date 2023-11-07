package com.PowerBike.controller;

import com.PowerBike.dto.ProductDto;
import com.PowerBike.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/getProduct/{id}")
    public ResponseEntity<?> getProduct (@PathVariable long id) {
        return productService.getProductById(id);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable long id){
        return productService.deleteProduct(id);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveCategory(@RequestBody ProductDto dto){
        return productService.saveProduct(dto);
    }

    @PutMapping(value = "/desactiveProduct/{id}")
    public ResponseEntity<?> desactiveProduct(@PathVariable long id){
        return productService.desactiveProduct(id);
    }
}
