package ee.ituk.api.user;

import ee.ituk.api.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @GetMapping("/{id}")
    @ResponseBody
    public UserDto findUserById(@PathVariable Long id) {
        return mapper.userToDto(userService.findUserById(id));
    }
}
