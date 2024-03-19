package book.store.onlinebookstore.controller;

import book.store.onlinebookstore.dto.CartItemRequestDto;
import book.store.onlinebookstore.dto.CartItemUpdateRequestDto;
import book.store.onlinebookstore.dto.ShoppingCartResponseDto;
import book.store.onlinebookstore.service.CartItemService;
import book.store.onlinebookstore.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@Tag(name = "Shopping cart and cart item management",
        description = "Endpoint for managing shopping cart and cart item")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final CartItemService cartItemService;

    @GetMapping
    @Operation(summary = "Retrieve user's shopping cart", description = "You can "
            + "get a shopping cart and your books in shopping cart.")
    public ShoppingCartResponseDto getShoppingCartByUser(Pageable pageable,Principal principal) {
        return shoppingCartService.getShoppingCartByUser(pageable, principal);
    }

    @PostMapping
    @Operation(summary = "Add book to the shopping cart", description = "You can "
            + "add book to shopping cart, "
            + "and add quantity of the book.")
    @ResponseStatus(HttpStatus.OK)
    public void addBookToShoppingCart(@RequestBody
                                          CartItemRequestDto cartItemRequestDto,
                                      Principal principal) {
        cartItemService.saveBookToShoppingCart(cartItemRequestDto, principal);
    }

    @PutMapping("cart-items/{cartItemId}")
    @Operation(summary = "Update quantity of a book in the shopping cart",
            description = "You can update quantity of books in your shopping cart")
    @ResponseStatus(HttpStatus.OK)
    public void updateBooksQuantityInCartItem(
            @PathVariable Long cartItemId,
            @RequestBody CartItemUpdateRequestDto cartItemUpdateRequestDto) {
        cartItemService.updateBooksQuantityInCartItem(cartItemId, cartItemUpdateRequestDto);
    }

    @DeleteMapping("cart-items/{cartItemId}")
    @Operation(summary = "Remove a book from the shopping cart",
            description = "If you dont want buy books from shopping cart "
                    + "you can easy remove them")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCartItemFromShoppingCart(@PathVariable Long cartItemId) {
        cartItemService.deleteCartItemById(cartItemId);
    }
}
