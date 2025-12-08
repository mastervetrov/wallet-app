# Wallet web-приложение для управления кошельком

## **<br><br>📖 Функционал**
#### 1) получить текущий баланс кошелька
#### 2) пополнить счет кошелька
#### 3) снять средства с кошелька

## **<br><br>🔛 Запуск**
#### скачай docker-compose-prod.yaml
#### введи команду docker compose -f docker-compose-prod.yaml up

## **<br><br>🛠 Использованные технологии**
### Java 17
### Spring boot 3.5.8
### Spring-framework
### Spring-boot-starters (Tests, Cache, Data-Jpa, Security, Web, Validation)
### PostgreSQL
### Gradle
### Lombok
### Docker
### Caffeine

## **<br><br>🔗 Эндпоинты**
### 1) GET /api/v1/wallets/{WALLET_ID} - получить баланс
### response: ![img.png](img.png)
### statusCode: 200 OK
### 2) POST /api/v1/wallet - выполнить транзакцию (Deposit/Withdraw)
### request: ![img_2.png](img_2.png)
#### statusCode: 200 OK

## **<br><br>🛡 Безопасность**
### Реализован JwtAuthenticationFilter, на текущий момент приложение валидирует все Токены как доверенные.

## **<br><br>🏗 Управление сборкой**
### .env.prod файл позволяет указать основные перменные BASE и расширенные ADVANCED (HICARI, TOMCAT, JVM, POSTGRES)

## **<br><br>📊 Тестирование**
### Branch [dev-and-tests](https://github.com/mastervetrov/wallet-app/tree/dev-and-tests)
### Нагрузочное тестирование ~400rps в конкурентной среде через Jmeter (Параллельно 3 запроса на 2 эндпоинта одного кошелька)
### Реализован интеграционный тест эндпоинтов.
### Postman collection: [link_of_collection](https://www.postman.com/aviation-astronomer-78061967/workspace/wallet-api/collection/36175459-62122b1d-d4db-4bf5-a477-eb6b3762ccd3?action=share&source=copy-link&creator=36175459)

# **<br><br>🐞 Bugs**:


