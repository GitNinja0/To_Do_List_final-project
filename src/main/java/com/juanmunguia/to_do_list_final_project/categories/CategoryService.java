package com.juanmunguia.to_do_list_final_project.categories;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> getAll() {
        return repository.findAll();
    }

    public Category create(CategoryDTO dto) {
        if (repository.findByName(dto.getName()).isPresent()) {
            throw new RuntimeException("Category already exists");
        }
        Category newCategory = Category.builder()
                .name(dto.getName())
                .build();
        return repository.save(newCategory);
    }

    public Category update(Long id, CategoryDTO dto) {
        Category category = repository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(dto.getName());
        return repository.save(category);
    }

    public String delete(Long id) {
        Category category = repository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        repository.delete(category);
        return "Category deleted successfully";
    }
}
