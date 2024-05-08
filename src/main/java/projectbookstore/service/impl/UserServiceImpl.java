package projectbookstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbookstore.dto.UserRegistrationRequestDto;
import projectbookstore.dto.UserResponseDto;
import projectbookstore.exception.RegistrationException;
import projectbookstore.mapper.UserMapper;
import projectbookstore.model.User;
import projectbookstore.repository.UserRepository;
import projectbookstore.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto request)
            throws RegistrationException {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RegistrationException("Can't register user");
        }
        User user = userMapper.toModel(request);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }
}
