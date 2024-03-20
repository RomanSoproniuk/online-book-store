package book.store.onlinebookstore.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDto {
    private String shippingAddress;
    private LocalDateTime localDateTimeNow = LocalDateTime.now();
}
