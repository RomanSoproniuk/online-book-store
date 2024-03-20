package book.store.onlinebookstore.mapper;

import book.store.onlinebookstore.config.MapperConfig;
import book.store.onlinebookstore.dto.OrderRequestDto;
import book.store.onlinebookstore.dto.OrderResponseDto;
import book.store.onlinebookstore.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = UserMapper.class)
public interface OrderMapper {
    @Mapping(target = "userId", source = "user", qualifiedByName = "getUserId")
    OrderResponseDto toDto(Order order);

    Order toModel(OrderRequestDto requestDto);
}
