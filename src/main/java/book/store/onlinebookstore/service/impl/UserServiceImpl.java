package book.store.onlinebookstore.service.impl;

import book.store.onlinebookstore.dto.userdto.UserRegistrationRequestDto;
import book.store.onlinebookstore.dto.userdto.UserResponseDto;
import book.store.onlinebookstore.exceptions.RegistrationException;
import book.store.onlinebookstore.mapper.UserMapper;
import book.store.onlinebookstore.model.User;
import book.store.onlinebookstore.repository.UserRepository;
import book.store.onlinebookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto authenticate(UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException {
        String userEmail = userRegistrationRequestDto.getEmail();
        User user = userRepository.findByEmail(userEmail).orElseThrow(() ->
                new RegistrationException("User by email " + userEmail + " is not already exists"));
        return userMapper.toDto(user);
    }
}
