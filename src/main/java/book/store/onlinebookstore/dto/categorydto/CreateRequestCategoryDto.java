package book.store.onlinebookstore.dto.categorydto;

import jakarta.validation.constraints.NotEmpty;

public record CreateRequestCategoryDto(
        Long id,
        @NotEmpty
        String name,
        String description
) {
}
