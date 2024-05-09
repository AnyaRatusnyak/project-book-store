package projectbookstore.mapper;

import org.mapstruct.Mapper;
import projectbookstore.config.MapperConfig;
import projectbookstore.dto.UserRegistrationRequestDto;
import projectbookstore.dto.UserResponseDto;
import projectbookstore.model.User;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User user);

    User toModel(UserRegistrationRequestDto request);
}
