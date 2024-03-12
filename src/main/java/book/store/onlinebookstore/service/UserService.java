package book.store.onlinebookstore.service;

import book.store.onlinebookstore.dto.userdto.UserRegistrationRequestDto;
import book.store.onlinebookstore.dto.userdto.UserResponseDto;
import book.store.onlinebookstore.exceptions.RegistrationException;

public interface UserService {
    UserResponseDto authenticate(UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException;
}
