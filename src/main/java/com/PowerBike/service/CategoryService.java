package com.PowerBike.service;

import com.PowerBike.dto.CategoryDto;
import com.PowerBike.entity.Category;
import com.PowerBike.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public ResponseEntity<?> getAllCategories() {
        List<Category> categories = (List<Category>) categoryRepository.findAll();
        if (categories.isEmpty()) {
            return new ResponseEntity<>("No hay categorias registradas", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    public ResponseEntity<?> getCategoryById(long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            return new ResponseEntity<>(category, HttpStatus.OK);
        }
        return new ResponseEntity<>("La categoria no existe", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> deleteCategory(long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category != null) {
            categoryRepository.deleteById(id);
            return new ResponseEntity<>("La categoria se ha eliminado correctamente", HttpStatus.OK);
        }
        return new ResponseEntity<>("La categoria que intenta eliminar no existe", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> saveCategory(CategoryDto dto) {
        Category category = Category.builder()
                .nameCategory(dto.getNameCategory())
                .description(dto.getDescription())
                .build();
        categoryRepository.save(category);
        return new ResponseEntity<>("Se ha creado la categoria ".concat(category.getNameCategory()), HttpStatus.OK);
    }

    public ResponseEntity<?> updateCategory(long id, CategoryDto dto) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category != null) {
            category.setNameCategory(dto.getNameCategory());
            category.setDescription(dto.getDescription());
            categoryRepository.save(category);
            return new ResponseEntity<>("Se ha actualizado la categoria ".concat(dto.getNameCategory()), HttpStatus.OK);
        }
        return new ResponseEntity<>("Error de solicitud", HttpStatus.BAD_REQUEST);
    }
}
