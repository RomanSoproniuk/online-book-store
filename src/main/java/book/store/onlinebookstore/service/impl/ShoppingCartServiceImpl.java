package book.store.onlinebookstore.service.impl;

import book.store.onlinebookstore.dto.CartItemResponseDto;
import book.store.onlinebookstore.dto.ShoppingCartResponseDto;
import book.store.onlinebookstore.exceptions.EntityNotFoundException;
import book.store.onlinebookstore.mapper.CartItemMapper;
import book.store.onlinebookstore.mapper.ShoppingCartMapper;
import book.store.onlinebookstore.model.CartItem;
import book.store.onlinebookstore.model.ShoppingCart;
import book.store.onlinebookstore.repository.cartitem.CartItemRepository;
import book.store.onlinebookstore.repository.shoppingcart.ShoppingCartRepository;
import book.store.onlinebookstore.service.ShoppingCartService;
import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;
    private final CartItemRepository cartItemRepository;

    @Override
    public ShoppingCartResponseDto getShoppingCartByUser(Pageable pageable,
                                                         Principal principal) {
        String userEmail = principal.getName();
        ShoppingCart usersShoppingCart = shoppingCartRepository
                .findByUserEmail(userEmail).orElseThrow(() ->
                new EntityNotFoundException("Shopping cart for User: "
                        + userEmail + " is not exist"));
        List<CartItem> allByShoppingCartId = cartItemRepository
                .findAllByShoppingCartId(usersShoppingCart.getId(), pageable);
        Set<CartItemResponseDto> cartItemResponseDtos = allByShoppingCartId.stream()
                .map(cartItemMapper::toDto)
                .collect(Collectors.toSet());
        ShoppingCartResponseDto shoppingCartDto = shoppingCartMapper.toDto(usersShoppingCart);
        shoppingCartDto.setCartItems(cartItemResponseDtos);
        return shoppingCartDto;
    }
}
