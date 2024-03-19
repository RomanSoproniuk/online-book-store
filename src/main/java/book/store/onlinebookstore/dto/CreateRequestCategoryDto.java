package book.store.onlinebookstore.dto;

import jakarta.validation.constraints.NotEmpty;

public record CreateRequestCategoryDto(
        Long id,
        @NotEmpty
        String name,
        String description
) {
}
