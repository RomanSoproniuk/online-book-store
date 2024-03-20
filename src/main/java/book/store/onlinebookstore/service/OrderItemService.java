package book.store.onlinebookstore.service;

import book.store.onlinebookstore.dto.OrderItemResponseDto;
import java.security.Principal;
import java.util.Set;
import org.springframework.data.domain.Pageable;

public interface OrderItemService {
    Set<OrderItemResponseDto> getAllOrderItemsByUserId(
            Long userId,
            Pageable pageable);

    Set<OrderItemResponseDto> getAllOrderItemsByOrderId(
            Long orderId,
            Pageable pageable,
            Principal principal);

    OrderItemResponseDto getSpecificOrderItemByOrderAndOrderItemIds(
            Long orderId,
            Long itemId,
            Principal principal);
}
