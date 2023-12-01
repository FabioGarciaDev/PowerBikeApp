package com.PowerBike.service;

import com.PowerBike.dto.ProductDto;
import com.PowerBike.entity.Product;
import com.PowerBike.repository.ProductRepository;
import com.PowerBike.service.utils.UploadFileService;
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
    private final String defaultImage = "default.jpg";

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
                .filter(product -> product.getStock()>0 && product.getActiveProduct())
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
            if (!product.getImage().equals(defaultImage)){
                uploadFileService.deleteImage(product.getImage());
            }
            productRepository.deleteById(id);

            return new ResponseEntity<>("El producto se ha eliminado correctamente", HttpStatus.OK);
        }
        return new ResponseEntity<>("El producto que intenta eliminar no existe", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> saveProduct(ProductDto dto) throws IOException {
        //Guardar imagen producto
        String nameFile = uploadFileService.saveImage(dto.getImage());
        Product product = Product.builder()
                .productName(dto.getProductName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .discount(dto.getDiscount())
                .activeProduct(true)
                .image(nameFile)
                .build();
        productRepository.save(product);
        return new ResponseEntity<>("Se ha creado el producto ".concat(product.getProductName()), HttpStatus.OK);
    }

    public ResponseEntity<?> updateProduct(long id, ProductDto dto) throws IOException {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            product.setProductName(dto.getProductName());
            product.setDescription(dto.getDescription());
            product.setPrice(dto.getPrice());
            product.setStock(dto.getStock());
            product.setDiscount(dto.getDiscount());

            if (dto.getImage() != null || !dto.getImage().isEmpty() || !product.getImage().equals(defaultImage)) {
                uploadFileService.deleteImage(product.getImage());
                String newImage = uploadFileService.saveImage(dto.getImage());
                product.setImage(newImage);
            }
            productRepository.save(product);
            return new ResponseEntity<>("Se ha actualizado el producto ".concat(dto.getProductName()), HttpStatus.OK);
        }
        return new ResponseEntity<>("Error de solicitud", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> desactiveProduct(long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            product.setActiveProduct(false);
            productRepository.save(product);
            return new ResponseEntity<>("El producto se ha desactivado correctamente", HttpStatus.OK);
        }
        return new ResponseEntity<>("El producto que intenta desactivar no existe", HttpStatus.BAD_REQUEST);
    }

    public boolean isValidFile(MultipartFile file) {
        if (file.getOriginalFilename().endsWith(".jpeg")
                || file.getOriginalFilename().endsWith(".jpg")
                || file.getOriginalFilename().endsWith(".png")
                || file.isEmpty()) {
            return true;
        }
        return false;
    }
}
