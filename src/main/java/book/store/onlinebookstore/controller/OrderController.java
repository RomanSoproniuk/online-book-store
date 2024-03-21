package book.store.onlinebookstore.controller;

import book.store.onlinebookstore.dto.OrderItemResponseDto;
import book.store.onlinebookstore.dto.OrderRequestDto;
import book.store.onlinebookstore.dto.OrderResponseDto;
import book.store.onlinebookstore.dto.OrderUpdateRequestDto;
import book.store.onlinebookstore.service.OrderItemService;
import book.store.onlinebookstore.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.security.Principal;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Order management", description = "Endpoint for managing orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    @Operation(summary = "Get all orders by user", description = "You can get all orders"
            + " for current user")
    @GetMapping
    public Set<OrderResponseDto> getAllOrders(Principal principal, Pageable pageable) {
        return orderService.getAllOrders(principal, pageable);
    }

    @Operation(summary = "Get all order items by order id", description = "You can get "
            + "all order items for current user by order id")
    @GetMapping("/{orderId}/items")
    public Set<OrderItemResponseDto> getAllOrderItemsByOrderId(
            @PathVariable
            Long orderId,
            Pageable pageable,
            Principal principal) {
        return orderItemService.getAllOrderItemsByOrderId(orderId, pageable, principal);
    }

    @Operation(summary = "Get specific order item for specific order", description = "You can get "
            + "specific order item for specific order")
    @GetMapping("/{orderId}/items/{itemId}")
    public OrderItemResponseDto getSpecificOrderItemByOrderAndOrderItemIds(
            @PathVariable Long orderId,
             @PathVariable Long itemId,
            Principal principal) {
        return orderItemService.getSpecificOrderItemByOrderAndOrderItemIds(orderId,
                itemId, principal);
    }

    @Operation(summary = "You can place order", description = "You can make order input specific"
            + " data avout order")
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void placeOrder(@RequestBody OrderRequestDto orderRequestDto,
                           Principal principal,
                           Pageable pageable) {
        orderService.placeOrder(orderRequestDto, principal, pageable);
    }

    @Operation(summary = "Manage order status", description = "You can manage order status if"
            + " you have role ADMIN")
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void updateOrderStatus(@PathVariable Long id,
                                  @RequestBody OrderUpdateRequestDto orderUpdateRequestDto) {
        orderService.updateOrderStatus(id, orderUpdateRequestDto);
    }

}
