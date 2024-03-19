package book.store.onlinebookstore.service;

import book.store.onlinebookstore.dto.shoppingcartdto.ShoppingCartResponseDto;
import java.security.Principal;
import org.springframework.data.domain.Pageable;

public interface ShoppingCartService {
    ShoppingCartResponseDto getShoppingCartByUser(Pageable pageable,
                                                  Principal principal);
}
