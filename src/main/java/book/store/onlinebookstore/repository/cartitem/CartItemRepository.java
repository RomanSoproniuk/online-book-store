package book.store.onlinebookstore.repository.cartitem;

import book.store.onlinebookstore.model.CartItem;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @EntityGraph(attributePaths = {"shoppingCart", "book"})
    List<CartItem> findAllByShoppingCartId(Long shoppingCartId, Pageable pageable);
}
