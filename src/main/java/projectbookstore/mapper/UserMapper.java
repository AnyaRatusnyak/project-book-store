package projectbookstore.mapper;

import org.mapstruct.Mapper;
import projectbookstore.config.MapperConfig;
import projectbookstore.dto.registration.UserRegistrationRequestDto;
import projectbookstore.dto.registration.UserResponseDto;
import projectbookstore.model.User;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User user);

    User toModel(UserRegistrationRequestDto request);
}
