package ee.ituk.api.user;

import ee.ituk.api.user.dto.PasswordChangeDto;
import ee.ituk.api.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    @ResponseBody
    public List<UserDto> findAll() {
        return mapper.usersToDto(userService.findAll());
    }

    @PostMapping
    @ResponseBody
    public UserDto createUser(@RequestBody UserDto userDto) {
        return mapper.userToDto(userService.createUser(mapper.userToEntity(userDto)));
    }

    @PutMapping
    @ResponseBody
    public UserDto updateUser(@RequestBody UserDto userDto) {
        return mapper.userToDto(userService.updateUser(mapper.userToEntity(userDto)));
    }

    @PutMapping("/{id}/new-password")
    public void changePassword(@PathVariable Long id, @RequestBody PasswordChangeDto passwordChangeDto) {
        userService.changePassword(id, passwordChangeDto);
    }

    @GetMapping("/recovery/{key}")
    public UserDto getRecoveryUser(@PathVariable("key") String key) {
        return mapper.userToDto(userService.getRecoveryUser(key));
    }

    @GetMapping("/logout")
    public void logout() {
        userService.logout();
    }

    @GetMapping("/count")
    @ResponseBody
    public long getMemberCount() {
        return userService.getMemberCount();
    }

    @GetMapping("/me")
    @ResponseBody
    public UserDto getLoggedUser() {
        return mapper.userToDto(userService.getLoggedUser());
    }

}
