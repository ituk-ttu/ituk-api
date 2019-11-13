package ee.ituk.api.user;

import ee.ituk.api.recovery.RecoveryService;
import ee.ituk.api.user.domain.Role;
import ee.ituk.api.user.dto.PasswordChangeDto;
import ee.ituk.api.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);
    private final RecoveryService recoveryService;

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity findUserById(@PathVariable Long id) {
        return ok(mapper.userToDto(userService.findUserById(id)));
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity findAll() {
        return ok(mapper.usersToDto(userService.findAll()));
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity createUser(@RequestBody UserDto userDto) {
        return ok(mapper.userToDto(userService.createUser(mapper.userToEntity(userDto))));
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity updateUser(@RequestBody UserDto userDto) {
        return ok(mapper.userToDto(userService.updateUser(mapper.userToEntity(userDto))));
    }

    @PutMapping("/{id}/new-password")
    public ResponseEntity changePassword(@PathVariable Long id, @RequestBody PasswordChangeDto passwordChangeDto) {
        userService.changePassword(id, passwordChangeDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/recovery/{email}")
    public ResponseEntity sendNewPasswordToEmail(@PathVariable("email") String email) {
        recoveryService.sendNewRecoveryPassword(email);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/logout")
    public ResponseEntity logout() {
        userService.logout();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    @ResponseBody
    public ResponseEntity getMemberCount() {
        return ok(userService.getMemberCount());
    }

    @GetMapping("/me")
    @ResponseBody
    public ResponseEntity getLoggedUser() {
        return ok(mapper.userToDto(userService.getLoggedUser()));
    }

    @PutMapping("/{id}/role")
    public ResponseEntity changeRole(@PathVariable Long id, @RequestParam Role role) {
        userService.changeRole(id, role);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/isArchived")
    public ResponseEntity archive(@PathVariable Long id, @RequestParam boolean isArchived) {
        userService.archive(id, isArchived);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/birthdays")
    public ResponseEntity getBirthdayUsers() {
        return ok(userService.getBirthdayUserNames());
    }
}
