package book.store.onlinebookstore.dto;

import book.store.onlinebookstore.model.Order;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponseDto {
    private Long id;
    private Long userId;
    private Set<OrderItemResponseDto> orderItems = new HashSet<>();
    private LocalDateTime orderDate;
    private BigDecimal total;
    private Order.Status status;
}
