package book.store.onlinebookstore.service;

import book.store.onlinebookstore.dto.OrderRequestDto;
import book.store.onlinebookstore.dto.OrderResponseDto;
import book.store.onlinebookstore.dto.OrderUpdateRequestDto;
import java.security.Principal;
import java.util.Set;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Set<OrderResponseDto> getAllOrders(Principal principal, Pageable pageable);

    void placeOrder(OrderRequestDto orderRequestDto, Principal principal, Pageable pageable);

    void updateOrderStatus(Long id, OrderUpdateRequestDto orderUpdateRequestDto);
}
