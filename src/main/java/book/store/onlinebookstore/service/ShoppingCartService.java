package book.store.onlinebookstore.service;

import book.store.onlinebookstore.dto.ShoppingCartResponseDto;
import java.security.Principal;
import org.springframework.data.domain.Pageable;

public interface ShoppingCartService {
    ShoppingCartResponseDto getShoppingCartByUser(Pageable pageable,
                                                  Principal principal);
}
