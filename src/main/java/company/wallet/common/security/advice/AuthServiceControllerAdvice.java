package company.wallet.common.security.advice;

import company.wallet.common.security.dto.SecurityMessage;
import company.wallet.common.security.exception.AuthServiceNotFoundException;
import company.wallet.common.security.exception.JwtTokenInvalidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
@Slf4j
public class AuthServiceControllerAdvice {

    @ExceptionHandler(AuthServiceNotFoundException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public SecurityMessage authServiceNotFoundExceptionHandler(AuthServiceNotFoundException e) {
        return new SecurityMessage("Не удалось установить соединение с сервисом аутентификации");
    }

    @ExceptionHandler(JwtTokenInvalidException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public SecurityMessage jwtTokenInvalidExceptionHandler(JwtTokenInvalidException e) {
        return new SecurityMessage("Токен не валиден.");
    }
}
