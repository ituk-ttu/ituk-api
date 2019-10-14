package ee.ituk.api.user;

import ee.ituk.api.user.domain.User;
import ee.ituk.api.user.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto userToDto(User user);

    @Mapping(target = "password", ignore = true)
    User userToEntity(UserDto userDto);
}
