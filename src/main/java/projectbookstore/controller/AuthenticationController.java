package projectbookstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projectbookstore.dto.UserRegistrationRequestDto;
import projectbookstore.dto.UserResponseDto;
import projectbookstore.exception.RegistrationException;
import projectbookstore.service.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/auth")
public class AuthenticationController {
    private final UserService userService;

    @PostMapping("/registration")
    public UserResponseDto register(@Valid @RequestBody UserRegistrationRequestDto request)
            throws RegistrationException {
        return userService.register(request);

    }
}
