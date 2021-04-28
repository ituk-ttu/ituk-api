package ee.ituk.api.user;

import ee.ituk.api.recovery.RecoveryService;
import ee.ituk.api.user.domain.Role;
import ee.ituk.api.user.dto.MentorNameDto;
import ee.ituk.api.user.dto.PasswordChangeDto;
import ee.ituk.api.user.dto.UserBirthdayDto;
import ee.ituk.api.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);
    private final RecoveryService recoveryService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findUserById(@PathVariable Long id) {
        return ok(mapper.userToDto(userService.findUserById(id)));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll(@RequestParam Optional<Boolean> showArchived) {
        return ok(mapper.usersToDto(userService.findAll(showArchived.orElse(false))));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        return ok(mapper.userToDto(userService.createUser(mapper.userToEntity(userDto))));
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        return ok(mapper.userToDto(userService.updateUser(mapper.userToEntity(userDto))));
    }

    @PutMapping("/{id}/new-password")
    public ResponseEntity<Void> changePassword(@PathVariable Long id, @RequestBody PasswordChangeDto passwordChangeDto) {
        userService.changePassword(id, passwordChangeDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/recovery/{email}")
    public ResponseEntity<Void> sendNewPasswordToEmail(@PathVariable("email") String email) {
        recoveryService.sendNewRecoveryPassword(email);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout() {
        userService.logout();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getMemberCount() {
        return ok(userService.getMemberCount());
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getLoggedUser() {
        return ok(mapper.userToDto(userService.getLoggedUser()));
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<Void> changeRole(@PathVariable Long id, @RequestParam Role role) {
        userService.changeRole(id, role);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/isArchived")
    public ResponseEntity<Void> archive(@PathVariable Long id, @RequestParam boolean isArchived) {
        userService.archive(id, isArchived);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/today-birthdays")
    public ResponseEntity<List<String>> getTodayBirthdayUserNames() {
        return ok(userService.getTodayBirthdayUserNames());
    }

    @GetMapping("/birthdays")
    public ResponseEntity<List<UserBirthdayDto>> getBirthdayUsers() {
        return ok(userService.getLastWeekBirthdays());
    }

    @GetMapping("/{userId}/mentor-name")
    public ResponseEntity<MentorNameDto> getMentorName(@PathVariable Long userId) {
        return ok(userService.getMentorName(userId));
    }
}
