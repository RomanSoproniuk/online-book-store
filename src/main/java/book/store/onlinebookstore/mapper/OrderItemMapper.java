package book.store.onlinebookstore.mapper;

import book.store.onlinebookstore.config.MapperConfig;
import book.store.onlinebookstore.dto.OrderItemResponseDto;
import book.store.onlinebookstore.model.CartItem;
import book.store.onlinebookstore.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = BookMapper.class)
public interface OrderItemMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "bookId", source = "book", qualifiedByName = "getBookId")
    OrderItemResponseDto toDto(OrderItem orderItem);

    OrderItem toOrderItemFromCartItemDto(CartItem cartItem);
}
