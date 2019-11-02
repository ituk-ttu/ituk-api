package ee.ituk.api.login;

import ee.ituk.api.login.dto.LoginUserDto;
import ee.ituk.api.login.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    @ResponseBody
    public TokenDto login(@RequestBody LoginUserDto loginUserDto) {
        return authService.loginUser(loginUserDto);
    }
}
