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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException {
        String userEmail = userRegistrationRequestDto.getEmail();
        Optional<User> existingUser = userRepository.findByEmail(userEmail);
        if (existingUser.isPresent()) {
            throw new RegistrationException("User by email " + userEmail + " already exists");
        }
        User user = userMapper.toModel(userRegistrationRequestDto);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }
}