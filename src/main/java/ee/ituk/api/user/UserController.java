package ee.ituk.api.user;

import ee.ituk.api.user.domain.Role;
import ee.ituk.api.user.dto.PasswordChangeDto;
import ee.ituk.api.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> changePassword(@PathVariable Long id, @RequestBody PasswordChangeDto passwordChangeDto) {
        userService.changePassword(id, passwordChangeDto);
        return ResponseEntity.noContent().build();
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

    @PutMapping("/{id}/role")
    public ResponseEntity<?> changeRole(@PathVariable Long id, @RequestParam Role role) {
        userService.changeRole(id, role);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/birthdays")
    public List<String> getBirthdayUsers() {
        return userService.getBirthdayUserNames();
    }



}
