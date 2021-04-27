package ee.ituk.api.login;

import ee.ituk.api.login.dto.LoginUserDto;
import ee.ituk.api.login.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<TokenDto> login(@RequestBody LoginUserDto loginUserDto) {
        return ok(authService.loginUser(loginUserDto));
    }
}
