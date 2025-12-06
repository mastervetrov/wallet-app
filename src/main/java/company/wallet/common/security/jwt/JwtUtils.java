package company.wallet.common.security.jwt;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import company.wallet.common.client.AuthServiceClient;
import company.wallet.common.security.exception.AuthServiceNotFoundException;
import company.wallet.common.security.exception.JwtTokenInvalidException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtils {

    private final AuthServiceClient authServiceClient;

    @Cacheable(value = "userIdsFromJwt", key = "#token")
    public UUID getUserIdFromJwt(String token) {
        try {
            if (!authServiceClient.validateToken(token)) {
                log.info("Токен " + token + " не прошёл валидацию");
                throw new JwtTokenInvalidException("Токен не проше валидацию");
            }

            try {
                String[] parts = token.split("\\.");
                if (parts.length < 2) {
                    throw new JwtTokenInvalidException("Invalid JWT format");
                }
                String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));

                JsonNode payload = new ObjectMapper().readTree(payloadJson);

                String userIdStr = payload.get("sub").asText();
                log.info("Токен c userId {} успешно прошел валидацию в Authentication Service", userIdStr);
                return UUID.fromString(userIdStr);
            } catch (Exception e) {
                System.err.println("Failed to parse JWT: " + e.getMessage());
                throw new JwtTokenInvalidException("Invalid JWT token");
            }
        } catch (RuntimeException e) {
            log.info("Не удалось связаться с сервисом аутентификации или возникла ошибка в сервисе аутентификации");
            throw new AuthServiceNotFoundException("Не удалось связаться с сервисом аутентификации или возникла ошибка в сервисе аутентификации");
        }
    }
}