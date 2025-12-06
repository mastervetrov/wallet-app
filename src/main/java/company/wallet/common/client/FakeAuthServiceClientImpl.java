package company.wallet.common.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

// todo Класс заглушка для тестов - эмулирует обращение к внешнему сервису auth
// todo и по умолчанию всегда возвращает успешную валидацию JWT токена
@Component
@Slf4j
public class FakeAuthServiceClientImpl implements AuthServiceClient {
    @Override
    public Boolean validateToken(String token) {
        log.info("Token validate with FakeAuthServiceClient(always return true)");
        return true;
    }
}
