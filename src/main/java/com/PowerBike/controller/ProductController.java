package com.PowerBike.controller;

import com.PowerBike.dto.CategoryDto;
import com.PowerBike.dto.ProductDto;
import com.PowerBike.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/getAllStore")
    public ResponseEntity<?> getAllProductsStore() {
        return productService.getAllProductsStore();
    }

    @GetMapping("/getProduct/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<?> getProduct(@PathVariable long id) {
        return productService.getProductById(id);
    }

    @PostMapping(value = "/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> saveProduct(@ModelAttribute ProductDto dto) throws IOException {
        if (productService.isValidFile(dto.getImage())) {
            return productService.saveProduct(dto);
        }
        return new ResponseEntity<>("El tipo de archivo debe ser JPG, JPEG o PNG", HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateProduct(@PathVariable long id, @ModelAttribute ProductDto dto) throws IOException {
        return productService.updateProduct(id, dto);
    }

    @PutMapping(value = "/desactive/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> desactiveProduct(@PathVariable long id) {
        return productService.desactiveProduct(id);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id) {
        return productService.deleteProduct(id);
    }
}
