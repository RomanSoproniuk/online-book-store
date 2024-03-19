package book.store.onlinebookstore.dto;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShoppingCartResponseDto {
    private Long id;
    private Long usersId;
    private Set<CartItemResponseDto> cartItems;
}
