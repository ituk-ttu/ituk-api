package ee.ituk.api.user;

import ee.ituk.api.user.domain.User;
import ee.ituk.api.user.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {

    UserDto userToDto(User user);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User userToEntity(UserDto userDto);

    List<UserDto> usersToDto(List<User> users);
}
