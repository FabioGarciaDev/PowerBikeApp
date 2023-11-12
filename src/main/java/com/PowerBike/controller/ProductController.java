package com.PowerBike.controller;

import com.PowerBike.dto.CategoryDto;
import com.PowerBike.dto.ProductDto;
import com.PowerBike.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/getAllStore")
    public ResponseEntity<?> getAllProductsStore() {
        return productService.getAllProductsStore();
    }

    @GetMapping("/getProduct/{id}")
    public ResponseEntity<?> getProduct(@PathVariable long id) {
        return productService.getProductById(id);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveCategory(@ModelAttribute ProductDto dto) throws IOException {
        if (productService.isValidFile(dto.getImage())) {
            return productService.saveProduct(dto);
        }
        return new ResponseEntity<>("El tipo de archivo debe ser JPG, JPEG o PNG", HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable long id, @ModelAttribute ProductDto dto) throws IOException {
        if (productService.isValidFile(dto.getImage())) {
            return productService.updateProduct(id, dto);
        }
        return new ResponseEntity<>("El tipo de archivo debe ser JPG, JPEG o PNG", HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/desactive/{id}")
    public ResponseEntity<?> desactiveProduct(@PathVariable long id) {
        return productService.desactiveProduct(id);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable long id) {
        return productService.deleteProduct(id);
    }
}
