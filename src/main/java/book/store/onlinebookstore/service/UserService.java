package book.store.onlinebookstore.service;

import book.store.onlinebookstore.dto.UserRegistrationRequestDto;
import book.store.onlinebookstore.dto.UserResponseDto;
import book.store.onlinebookstore.exceptions.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException;
}
