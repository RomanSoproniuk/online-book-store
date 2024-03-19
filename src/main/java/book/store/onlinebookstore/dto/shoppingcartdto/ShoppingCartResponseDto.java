package book.store.onlinebookstore.dto.shoppingcartdto;

import book.store.onlinebookstore.model.CartItem;

import java.util.Set;

public class ShoppingCartResponseDto {
    private Long id;
    private Long usersId;
    private Set<CartItem> cartItems;
}
