package book.store.onlinebookstore.dto;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ShoppingCartResponseDto {
    private Long id;
    private Long usersId;
    private Set<CartItemResponseDto> cartItems;
}
