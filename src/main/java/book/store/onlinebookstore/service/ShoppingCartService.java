package book.store.onlinebookstore.service;

import book.store.onlinebookstore.dto.shoppingcartdto.ShoppingCartResponseDto;
import org.springframework.data.domain.Pageable;

import java.security.Principal;

public interface ShoppingCartService {
    ShoppingCartResponseDto getShoppingCartByUser(Pageable pageable,
                                                  Principal principal);
}
