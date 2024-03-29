package book.store.onlinebookstore.service;

import book.store.onlinebookstore.dto.CartItemResponseDto;
import book.store.onlinebookstore.dto.ShoppingCartResponseDto;
import book.store.onlinebookstore.mapper.CartItemMapper;
import book.store.onlinebookstore.mapper.ShoppingCartMapper;
import book.store.onlinebookstore.model.CartItem;
import book.store.onlinebookstore.model.ShoppingCart;
import book.store.onlinebookstore.model.User;
import book.store.onlinebookstore.repository.cartitem.CartItemRepository;
import book.store.onlinebookstore.repository.shoppingcart.ShoppingCartRepository;
import book.store.onlinebookstore.service.impl.ShoppingCartServiceImpl;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class ShoppingCartServiceTest {
    @Mock
    private ShoppingCartRepository shoppingCartRepository;
    @Mock
    private ShoppingCartMapper shoppingCartMapper;
    @Mock
    private CartItemMapper cartItemMapper;
    @Mock
    private CartItemRepository cartItemRepository;
    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;
    private final Pageable pageable = PageRequest.of(0, 2);
    private final Principal principal = new Principal() {
        @Override
        public String getName() {
            return "admin@gmail.com";
        }
    };

    @Test
    @DisplayName("""
            Check if return correct shopping cart
            """)
    public void getShoppingCartByUser_ReturnCorrectShoppingCart_Success() {
        //given
        User user = new User();
        user.setEmail("admin@gmail.com");
        user.setId(1L);
        CartItem cartItem = new CartItem();
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setUser(user);
        shoppingCart.setCartItems(Set.of(cartItem));
        Mockito.when(shoppingCartRepository.findByUserEmail(principal
                .getName())).thenReturn(Optional.of(shoppingCart));
        Mockito.when(cartItemRepository.findAllByShoppingCartId(shoppingCart.getId(), pageable))
                .thenReturn(List.of(cartItem));
        CartItemResponseDto cartItemResponseDto = new CartItemResponseDto();
        cartItemResponseDto.setId(1L);
        cartItemResponseDto.setBookId(1L);
        cartItemResponseDto.setBookTitle("Kobzar");
        Mockito.when(cartItemMapper.toDto(cartItem)).thenReturn(cartItemResponseDto);
        ShoppingCartResponseDto expected = new ShoppingCartResponseDto();
        expected.setId(1L);
        expected.setCartItems(Set.of(cartItemResponseDto));
        expected.setUsersId(1L);
        Mockito.when(shoppingCartMapper.toDto(shoppingCart)).thenReturn(expected);
        //when
        ShoppingCartResponseDto actual = shoppingCartService
                .getShoppingCartByUser(pageable, principal);
        //then
        Assertions.assertEquals(actual.getId(), expected.getId());
        Assertions.assertEquals(actual.getUsersId(), expected.getUsersId());
        Assertions.assertEquals(actual.getCartItems(), expected.getCartItems());
    }

}
