package book.store.onlinebookstore.service.impl;

import book.store.onlinebookstore.dto.cartitemdto.CartItemRequestDto;
import book.store.onlinebookstore.dto.cartitemdto.CartItemUpdateRequestDto;
import book.store.onlinebookstore.exceptions.EntityNotFoundException;
import book.store.onlinebookstore.mapper.CartItemMapper;
import book.store.onlinebookstore.model.CartItem;
import book.store.onlinebookstore.model.ShoppingCart;
import book.store.onlinebookstore.repository.BookRepository;
import book.store.onlinebookstore.repository.cartitem.CartItemRepository;
import book.store.onlinebookstore.repository.shoppingcart.ShoppingCartRepository;
import book.store.onlinebookstore.service.CartItemService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final ShoppingCartRepository shoppingCartRepository;
    private final BookRepository bookRepository;

    @Override
    public void saveBookToShoppingCart(CartItemRequestDto cartItemRequestDto,
                                       Principal principal) {
        ShoppingCart usersShoppingCart = shoppingCartRepository
                .findByUserEmail(principal.getName()).orElseThrow(() ->
                        new EntityNotFoundException("Shopping cart by user email: " + principal
                                .getName()
                                + " is not exist"));
        CartItem cartItem = cartItemMapper.toEntity(cartItemRequestDto);
        cartItem.setShoppingCart(usersShoppingCart);
        Long bookId = cartItemRequestDto.bookId();
        cartItem.setBook(bookRepository.findById(bookId).orElseThrow(() ->
                new EntityNotFoundException("Can't find book by id: " + bookId)));
        CartItem savedCartItem = cartItemRepository.save(cartItem);
        usersShoppingCart.getCartItems().add(savedCartItem);
        shoppingCartRepository.save(usersShoppingCart);
    }

    @Override
    public void updateBooksQuantityInCartItem(Long cartItemId,
                                              CartItemUpdateRequestDto cartItemUpdateRequestDto) {
        CartItem cartItemById = cartItemRepository.findById(cartItemId).orElseThrow(() ->
                new EntityNotFoundException("Cart item by id: " + cartItemId
                        + " is not exist"));
        cartItemById.setQuantity(cartItemUpdateRequestDto.getQuantity());
        cartItemRepository.save(cartItemById);
    }

    @Override
    public void deleteCartItemById(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
}
