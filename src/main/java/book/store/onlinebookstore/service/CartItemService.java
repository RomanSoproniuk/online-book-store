package book.store.onlinebookstore.service;

import book.store.onlinebookstore.dto.CartItemRequestDto;
import book.store.onlinebookstore.dto.CartItemUpdateRequestDto;
import java.security.Principal;

public interface CartItemService {
    void saveBookToShoppingCart(CartItemRequestDto cartItemRequestDto,
                                Principal principal);

    void updateBooksQuantityInCartItem(Long cartItemId,
                                       CartItemUpdateRequestDto cartItemUpdateRequestDto);

    void deleteCartItemById(Long cartItemId);
}
