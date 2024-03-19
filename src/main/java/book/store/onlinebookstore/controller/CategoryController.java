package book.store.onlinebookstore.controller;

import book.store.onlinebookstore.dto.BookDtoWithoutCategoryIds;
import book.store.onlinebookstore.dto.CategoryDto;
import book.store.onlinebookstore.dto.CreateRequestCategoryDto;
import book.store.onlinebookstore.service.BookService;
import book.store.onlinebookstore.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Category management", description = "Endpoint for managing categories")
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final BookService bookService;

    @GetMapping("/{id}/books")
    @Operation(summary = "Get all books by category id", description = "You can "
            + "get a certain number of books per issue page and with "
            + "a certain number of books per page")
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(Pageable pageable,
                                                                @PathVariable Long id) {
        return bookService.getAllByCategoriesId(pageable, id);
    }

    @GetMapping
    @Operation(summary = "Get all categories", description = "You can get a certain number of "
            + "categories per issue page and with a certain number of categories per page")
    public List<CategoryDto> getAll(Pageable pageable) {
        return categoryService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID", description = "You can get a specific "
            + "ID category if one exists")
    public CategoryDto getCategoryById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Add category to repository", description = "You can add "
            + "the category to repository")
    public CategoryDto createCategory(@RequestBody
                                          @Valid CreateRequestCategoryDto categoryDto) {
        return categoryService.save(categoryDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Update the category in the repository",
            description = "You can update "
                    + "a category for a specific ID if one exists")
    public CategoryDto updateCategory(@PathVariable Long id,
                                      @RequestBody
                                      @Valid CreateRequestCategoryDto categoryDto) {
        return categoryService.update(id, categoryDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Remove the category from storage",
            description = "You can delete a category by "
            + "a specific ID if it exists in the repository")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
    }
}
