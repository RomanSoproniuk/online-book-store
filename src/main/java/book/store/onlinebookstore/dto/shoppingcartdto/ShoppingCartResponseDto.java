package book.store.onlinebookstore.dto.shoppingcartdto;

import book.store.onlinebookstore.dto.cartitemdto.CartItemResponseDto;
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
