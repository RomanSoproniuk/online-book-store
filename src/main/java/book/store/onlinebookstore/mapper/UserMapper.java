package book.store.onlinebookstore.mapper;

import book.store.onlinebookstore.config.MapperConfig;
import book.store.onlinebookstore.dto.userdto.UserRegistrationRequestDto;
import book.store.onlinebookstore.dto.userdto.UserResponseDto;
import book.store.onlinebookstore.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User user);

    User toModel(UserRegistrationRequestDto userRegistrationRequestDto);

    @Named("getUserId")
    default Long getUserId(User user) {
        return user.getId();
    }
}
