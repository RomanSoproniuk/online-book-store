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
        if (userRepository.findByEmail(userRegistrationRequestDto.getEmail()) == null) {
            User savedUser
                    = userRepository.save(userMapper.toModel(userRegistrationRequestDto));
            return userMapper.toDto(savedUser);
        }
        throw new RegistrationException("User by email " + userRegistrationRequestDto.getEmail()
                + " is exist");
    }
}
