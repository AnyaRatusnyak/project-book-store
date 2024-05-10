package projectbookstore.service.impl;

import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import projectbookstore.dto.UserRegistrationRequestDto;
import projectbookstore.dto.UserResponseDto;
import projectbookstore.exception.RegistrationException;
import projectbookstore.mapper.UserMapper;
import projectbookstore.model.Role;
import projectbookstore.model.User;
import projectbookstore.repository.RoleRepository;
import projectbookstore.repository.UserRepository;
import projectbookstore.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto request)
            throws RegistrationException {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RegistrationException("Can't register user");
        }
        User user = userMapper.toModel(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Role defaultRole = roleRepository.findByRole(Role.RoleName.USER);
        user.setRoles(Collections.singleton(defaultRole));
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }
}
