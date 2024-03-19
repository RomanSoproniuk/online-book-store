package book.store.onlinebookstore.repository.shoppingcart;

import book.store.onlinebookstore.model.ShoppingCart;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    @EntityGraph(attributePaths = {"cartItems", "user"})
    Optional<ShoppingCart> findByUserEmail(String userEmail);
}
