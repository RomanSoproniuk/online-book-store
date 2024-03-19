package book.store.onlinebookstore.service;

import book.store.onlinebookstore.dto.CategoryDto;
import book.store.onlinebookstore.dto.CreateRequestCategoryDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    List<CategoryDto> findAll(Pageable pageable);

    CategoryDto getById(Long id);

    CategoryDto save(CreateRequestCategoryDto categoryDto);

    CategoryDto update(Long id, CreateRequestCategoryDto categoryDto);

    void deleteById(Long id);
}
