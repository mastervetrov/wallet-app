# Wallet бэкенд для управления кошельком с REST API

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

## **<br><br>🛢Хранение информации**
### Postgres + Liquibase

## **<br><br>🛡 Безопасность**
### Реализован JwtAuthenticationFilter в составе SecurityConfig для работы с токеном JWT, на текущий момент приложение валидирует все Токены как доверенные.

## **<br><br>🏗 Управление сборкой**
### Централизованное управление сборкой через .env.prod файл.
### .env.prod содержит два раздела для настроек: BASE и ADVANCED (HICARI, TOMCAT, POSTGRES, JAVA)

## **<br><br>💾 Кэширование**
### Кэшируется токен JWT **3 минуты** с помощью Caffeine для снижения накладных расходов на валидацию токена. Размер кэша 1000. 

## **<br><br>📊 Тестирование**
### Branch [dev-and-tests](https://github.com/mastervetrov/wallet-app/tree/dev-and-tests)
### Нагрузочное тестирование ~400rps в конкурентной среде через Jmeter (Параллельно 3 запроса на 2 эндпоинта одного кошелька)
### Реализован интеграционный тест эндпоинтов с Testcontainers. 
### Postman collection: [link_of_collection](https://www.postman.com/aviation-astronomer-78061967/workspace/wallet-api/collection/36175459-62122b1d-d4db-4bf5-a477-eb6b3762ccd3?action=share&source=copy-link&creator=36175459)

# **<br><br>🐞 Bugs и особенности**:
Пользователь имеет право изменить кошелек другого пользователя
Валидацию проходит любой JWT токен, содержащий любой UUID в 'sub' payload.


