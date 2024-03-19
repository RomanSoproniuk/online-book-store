package book.store.onlinebookstore.mapper;

import book.store.onlinebookstore.config.MapperConfig;
import book.store.onlinebookstore.dto.shoppingcartdto.ShoppingCartResponseDto;
import book.store.onlinebookstore.model.ShoppingCart;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface ShoppingCartMapper {

    ShoppingCartResponseDto toDto(ShoppingCart cart);
}
