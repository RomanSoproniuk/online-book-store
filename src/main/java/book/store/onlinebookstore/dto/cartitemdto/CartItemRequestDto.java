package book.store.onlinebookstore.dto.cartitemdto;

import jakarta.validation.constraints.NotEmpty;

public record CartItemRequestDto(
        Long id,
        @NotEmpty
        Long bookId,
        int quantity
) {
}
