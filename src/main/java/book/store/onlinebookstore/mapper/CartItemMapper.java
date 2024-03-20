package book.store.onlinebookstore.mapper;

import book.store.onlinebookstore.config.MapperConfig;
import book.store.onlinebookstore.dto.CartItemRequestDto;
import book.store.onlinebookstore.dto.CartItemResponseDto;
import book.store.onlinebookstore.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = BookMapper.class)
public interface CartItemMapper {
    CartItem toEntity(CartItemRequestDto cartItemRequestDto);

    @Mapping(target = "bookId", source = "book", qualifiedByName = "getBookId")
    @Mapping(target = "bookTitle", source = "book", qualifiedByName = "getBookName")
    CartItemResponseDto toDto(CartItem cartItem);
}
