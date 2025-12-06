# Wallet web-приложение для управления кошельком

## **Функционал**
#### 1) получить текущий баланс GET /api/v1/wallets/{WALLET_ID}
#### 2) пополнить счет POST /api/v1/wallet { application/json }
#### 3) снять со счета POST /api/v1/wallet { application/json }

## **<br><br>Запуск**
#### скачай docker-compose-prod.yaml
#### введи команду docker compose -f docker-compose-prod.yaml up

## **<br><br>Использованные технологии**
### Java 17
### Spring boot 3.5.8
### Spring-framework
### Spring-boot-starters (Tests, Cache, Data-Jpa, Security, Web, Validation)
### PostgreSQL
### Gradle
### Lombok
### Docker
### Caffeine


##  Безопасность
### Реализован JwtAuthenticationFilter, на текущий момент приложение валидирует все Токены как доверенные.

## Управление сборкой
### .env.prod файл позволяет указать основные перменные BASE и расширенные ADVANCED (HICARI, TOMCAT, JVM, POSTGRES)

## Тестирование
### Нагрузочное тестирование ~400rps в конкурентной среде через Jmeter (Параллельно 3 запроса на 2 эндпоинта одного кошелька)
### Реализован интеграционный тест эндпоинтов.

# Bugs:


