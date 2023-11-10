package com.PowerBike.service;

import com.PowerBike.dto.ProductDto;
import com.PowerBike.entity.Product;
import com.PowerBike.repository.ProductRepository;
import com.PowerBike.utils.moreUtils.UploadFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UploadFileService uploadFileService;

    public ResponseEntity<?> getAllProducts() {
        List<Product> products = (List<Product>) productRepository.findAll();
        if (products.isEmpty()) {
            return new ResponseEntity<>("No hay productos registrados", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    public ResponseEntity<?> getAllProductsStore() {
        List<Product> products = (List<Product>) productRepository.findAll();
        if (products.isEmpty()) {
            return new ResponseEntity<>("No hay productos registrados", HttpStatus.NOT_FOUND);
        }
        List<Product> productsStore = products.stream()
                .filter(product -> product.getStock()>0)
                .collect(Collectors.toList());
        return new ResponseEntity<>(productsStore, HttpStatus.OK);
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

    public ResponseEntity<?> saveProduct(ProductDto dto, MultipartFile image) throws IOException {
        //Guardar imagen producto
        String nameFile = uploadFileService.saveImage(dto.getImage());
        Product product = Product.builder()
                .productName(dto.getProductName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .discount(dto.getDiscount())
                .isActive(true)
                .image(nameFile)
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
