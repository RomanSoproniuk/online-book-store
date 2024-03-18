package book.store.onlinebookstore.dto.categorydto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateRequestCategoryDto(
        Long id,
        @NotEmpty
        String name,
        String description
) {
}
