package company.wallet.common.client;

public interface AuthServiceClient {
    Boolean validateToken(String token);
}
