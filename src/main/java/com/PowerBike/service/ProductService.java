package com.PowerBike.service;

import com.PowerBike.dto.ProductDto;
import com.PowerBike.entity.Product;
import com.PowerBike.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ResponseEntity<?> getAllProducts() {
        List<Product> products = (List<Product>) productRepository.findAll();
        if (products.isEmpty()) {
            return new ResponseEntity<>("No hay productos registrados", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    public ResponseEntity<?> getProductById(long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        return new ResponseEntity<>("El producto no existe", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> deleteProduct(long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            productRepository.deleteById(id);
            return new ResponseEntity<>("El producto se ha eliminado correctamente", HttpStatus.OK);
        }
        return new ResponseEntity<>("El producto que intenta eliminar no existe", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> saveProduct(ProductDto dto) {
        Product product = Product.builder()
                .productName(dto.getProductName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .discount(dto.getDiscount())
                .image(dto.getImage())
                .isActive(true)
                .build();
        productRepository.save(product);
        return new ResponseEntity<>("Se ha creado el producto ".concat(product.getProductName()), HttpStatus.OK);
    }

    public ResponseEntity<?> desactiveProduct(long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            product.setActive(false);
            return new ResponseEntity<>("El producto se ha desactivado correctamente", HttpStatus.OK);
        }
        return new ResponseEntity<>("El producto que intenta desactivar no existe", HttpStatus.BAD_REQUEST);
    }
}
