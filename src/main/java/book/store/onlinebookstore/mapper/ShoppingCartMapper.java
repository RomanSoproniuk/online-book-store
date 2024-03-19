package book.store.onlinebookstore.mapper;

import book.store.onlinebookstore.config.MapperConfig;
import book.store.onlinebookstore.dto.shoppingcartdto.ShoppingCartResponseDto;
import book.store.onlinebookstore.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = UserMapper.class)
public interface ShoppingCartMapper {

    @Mapping(target = "usersId", source = "user", qualifiedByName = "getUserId")
    ShoppingCartResponseDto toDto(ShoppingCart cart);

}
