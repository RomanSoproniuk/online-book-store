package book.store.onlinebookstore.dto;

import book.store.onlinebookstore.model.Order;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderUpdateRequestDto {
    private Order.Status status;
}
