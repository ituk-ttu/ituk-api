package ee.ituk.api.login;

import ee.ituk.api.common.exception.BadCredentialsException;
import ee.ituk.api.common.exception.NoPasswordSetException;
import ee.ituk.api.config.security.JwtTokenUtil;
import ee.ituk.api.login.dto.LoginUserDto;
import ee.ituk.api.login.dto.TokenDto;
import ee.ituk.api.recovery.RecoveryService;
import ee.ituk.api.user.UserService;
import ee.ituk.api.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final RecoveryService recoveryService;
    private final JwtTokenUtil jwtTokenUtil;
    private final BCryptPasswordEncoder encoder;

    TokenDto loginUser(LoginUserDto loginUserDto) {
        User user = userService.loadInternalUserByUsername(loginUserDto.getEmail());
        if (Objects.isNull(user.getPassword())) {
            throw new NoPasswordSetException();
        }
        Optional<String> recoveryPassword = recoveryService.getRecoveryPasswordByEmail(loginUserDto.getEmail());
        if (encoder.matches(loginUserDto.getPassword(), user.getPassword())
                || (recoveryPassword.isPresent()
                && encoder.matches(loginUserDto.getPassword(), recoveryPassword.get()))
        ) {
            return new TokenDto(jwtTokenUtil.generateToken(user));
        }
        throw new BadCredentialsException();
    }
}
