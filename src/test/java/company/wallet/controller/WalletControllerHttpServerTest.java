package company.wallet.controller;

import company.wallet.api.ApiResponse;
import company.wallet.model.OperationType;
import company.wallet.model.Wallet;
import company.wallet.repository.WalletRepository;
import company.wallet.request.WalletOperationRequest;
import company.wallet.response.WalletBalanceResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class WalletControllerHttpServerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private String baseUrl;
    private UUID existingWalletId;

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    static {
        DockerImageName postgres = DockerImageName.parse("postgres:17.5");

        postgreSQLContainer = (PostgreSQLContainer) new PostgreSQLContainer(postgres).withReuse(true);

        postgreSQLContainer.start();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");

        registry.add("spring.liquibase.enabled", () -> "false");
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
        registry.add("spring.jpa.properties.hibernate.dialect",
                () -> "org.hibernate.dialect.PostgreSQLDialect");
    }

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/v1";

        Wallet wallet = new Wallet();
        wallet.setBalance(new BigDecimal("1500.50"));
        wallet.setAvailableBalance(new BigDecimal("1500.50"));
        Wallet savedWallet = walletRepository.save(wallet);
        existingWalletId = savedWallet.getId();
    }

    @Test
    @DisplayName("GET api/v1/wallets/{WALLET_UUID} - когда кошелек существует, эндпоинт должен вернуть баланс")
    void getByWalletId_withExistingWallet_shouldReturnBalance() {
        ResponseEntity<ApiResponse<WalletBalanceResponse>> response = restTemplate.exchange(
                baseUrl + "/wallets/{WALLET_UUID}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ApiResponse<WalletBalanceResponse>>() {},
                existingWalletId.toString()
        );

        assertThat(response.getStatusCode().equals(HttpStatus.OK));
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().success()).isTrue();

        WalletBalanceResponse balanceResponse = objectMapper.convertValue(
                response.getBody().data(),
                WalletBalanceResponse.class
        );

        assertThat(balanceResponse.getBalance()).isEqualByComparingTo("1500.50");
        assertThat(balanceResponse.getAvailableBalance()).isEqualByComparingTo("1500.50");
        assertThat(balanceResponse.getCurrency()).isEqualTo("RUB");
    }

    @Test
    @DisplayName("GET api/v1/wallets/{WALLET_UUID} - когда кошелек не существует, эндпоинт должен вернуть 404 и сообщение с ошибкой")
    void getByWalletId_withNonExistingWallet_shouldReturnError() {
        UUID nonExistingId = UUID.randomUUID();

        ResponseEntity<ApiResponse> response = restTemplate.getForEntity(
                baseUrl + "/wallets/{WALLET_UUID}",
                ApiResponse.class,
                nonExistingId
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().success()).isFalse();
        assertThat(response.getBody().error()).isNotNull();
    }

    @Test
    @DisplayName("POST api/v1/wallet - при успешном пополнении кошелька, на кошелька прибавляется сумма пополнения")
    void request_depositOperation_shouldProcessSuccessfully() {
        WalletOperationRequest request = new WalletOperationRequest();
        request.setValletId(existingWalletId);
        request.setOperationType(OperationType.DEPOSIT);
        request.setAmount(new BigDecimal("500.00"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<WalletOperationRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<Void> response = restTemplate.postForEntity(
                baseUrl + "/wallet",
                entity,
                Void.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Wallet updatedWallet = walletRepository.findById(existingWalletId).orElseThrow();
        assertThat(updatedWallet.getBalance()).isEqualByComparingTo("2000.50");
    }

    @Test
    @DisplayName("POST api/v1/wallet - при успешном снятии средств с кошелька, из кошелька убавляется сумма снятия")
    void request_withdrawalOperation_shouldProcessSuccessfully() {
        WalletOperationRequest request = new WalletOperationRequest();
        request.setValletId(existingWalletId);
        request.setOperationType(OperationType.WITHDRAW);
        request.setAmount(new BigDecimal("300.50"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<WalletOperationRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<Void> response = restTemplate.postForEntity(
                baseUrl + "/wallet",
                entity,
                Void.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Wallet updatedWallet = walletRepository.findById(existingWalletId).orElseThrow();
        assertThat(updatedWallet.getBalance()).isEqualByComparingTo("1200.00");
    }

    @Test
    @DisplayName("POST api/v1/wallet - когда не валидный json, эндпоинт должен вернуть ошибку 4XX")
    void request_withInvalidJson_shouldReturnBadRequest() {
        String invalidJson = "{ invalid json }";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(invalidJson, headers);

        ResponseEntity<Void> response = restTemplate.postForEntity(
                baseUrl + "/wallet",
                entity,
                Void.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("POST api/v1/wallet - когда не валидный json, эндпоинт должен вернуть ошибку 4XX")
    void request_withNullRequest_shouldReturnBadRequest() {
        WalletOperationRequest request = new WalletOperationRequest();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<WalletOperationRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<Void> response = restTemplate.postForEntity(
                baseUrl + "/wallet",
                entity,
                Void.class
        );

        assertThat(response.getStatusCode().is4xxClientError()).isTrue();
    }
}