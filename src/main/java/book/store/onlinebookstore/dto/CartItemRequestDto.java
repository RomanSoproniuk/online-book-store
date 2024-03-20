package book.store.onlinebookstore.dto;

import jakarta.validation.constraints.NotEmpty;

public record CartItemRequestDto(
        Long id,
        @NotEmpty
        Long bookId,
        int quantity
) {
}
