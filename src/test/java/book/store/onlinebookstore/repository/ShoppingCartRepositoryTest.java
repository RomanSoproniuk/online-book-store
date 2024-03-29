package book.store.onlinebookstore.repository;

import book.store.onlinebookstore.model.CartItem;
import book.store.onlinebookstore.model.ShoppingCart;
import book.store.onlinebookstore.model.User;
import book.store.onlinebookstore.repository.shoppingcart.ShoppingCartRepository;
import java.util.Optional;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ShoppingCartRepositoryTest {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    private final Pageable pageable = PageRequest.of(0, 5);

    @Test
    @DisplayName("""
            Return correct shopping cart
            """)
    @Sql(scripts = "classpath:database/books/add-shopping-cart-user-and-cart-item-to-db.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/books/delete-shopping-cart-user-and-cart-iem-from-db.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findByUserEmail_ReturnCorrectUser_Success() {
        //given
        User user = new User();
        user.setId(1L);
        ShoppingCart expected = new ShoppingCart();
        expected.setId(1L);
        expected.setUser(user);
        CartItem cartItem = new CartItem();
        cartItem.setId(2L);
        expected.setCartItems(Set.of(cartItem));
        String userEmail = "roman@gmail.com";
        //when
        Optional<ShoppingCart> actual = shoppingCartRepository.findByUserEmail(userEmail);
        //then
        EqualsBuilder.reflectionEquals(actual.get(), expected);
    }
}
