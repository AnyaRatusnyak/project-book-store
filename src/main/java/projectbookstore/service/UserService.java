package projectbookstore.service;

import projectbookstore.dto.registration.UserRegistrationRequestDto;
import projectbookstore.dto.registration.UserResponseDto;
import projectbookstore.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto request) throws RegistrationException;

}
