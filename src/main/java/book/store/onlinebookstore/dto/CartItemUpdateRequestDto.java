package book.store.onlinebookstore.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CartItemUpdateRequestDto {
    private int quantity;
}
