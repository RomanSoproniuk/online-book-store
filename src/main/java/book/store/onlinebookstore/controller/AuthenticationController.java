package book.store.onlinebookstore.controller;

import book.store.onlinebookstore.dto.userdto.UserRegistrationRequestDto;
import book.store.onlinebookstore.dto.userdto.UserResponseDto;
import book.store.onlinebookstore.exceptions.RegistrationException;
import book.store.onlinebookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;

    @PostMapping("/registration")
    public UserResponseDto registration(@RequestBody
                                            @Validated
                                            UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException {
        return userService.register(userRegistrationRequestDto);
    }
}
