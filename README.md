# CRM-система для управления продавцами и транзакциями

## Описание проекта

Данная CRM-система предназначена для управления информацией о продавцах и их транзакциях. Основные функции включают создание, чтение, обновление и удаление данных о продавцах и транзакциях. Система также поддерживает аналитические функции для обработки и анализа данных о транзакциях.

## Функциональность

- **CRUD-операции для продавцов**: создание, чтение, обновление и удаление продавцов.
- **CRUD-операции для транзакций**: создание, чтение, обновление и удаление транзакций.
- **Аналитика**:
    - Получение дня с наибольшими продажами для конкретного продавца.
    - Определение лучшего продавца на основе объема продаж за день, квартал или год.
    - Получение списка продавцов с транзакциями ниже определенной суммы.
    - Получение списка продавцов с транзакциями ниже определенной суммы за выбранный период.

## Стек технологий

- **Java 17+**
- **Spring Boot**
- **Gradle** (система сборки)
- **PostgreSQL** (в качестве основной базы данных)
- **Lombok** (для сокращения кода моделей и сервисов)
- **Spring Data JPA** (для работы с базой данных)
- **REST API** (для взаимодействия с клиентами)

## Зависимости

- `Spring Boot Starter Web`
- `Spring Boot Starter Data JPA`
- `Spring Boot Starter Test`
- `PostgreSQL Driver`
- `Lombok`
- `Gradle Wrapper`

## Сборка и запуск

### Требования

- **JDK 17+**
- **PostgreSQL** установленный и запущенный локально или в облаке
- **Gradle**

### Инструкции по запуску

1. Склонируйте репозиторий проекта:
   ```bash
   git clone https://github.com/Cookie-Boy/Shop.git
   cd Shop
   ```
2. Настройте базу данных PostgreSQL и обновите параметры подключения в файле application.properties:
    ```
   spring.application.name=shop

   spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    spring.datasource.driver-class-name=org.postgresql.Driver

    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
   ```
3. Соберите проект с использованием Gradle:
    ```
   ./gradlew build
   ```
4. Соберите проект с использованием Gradle:
   ```
   ./gradlew bootRun
   ```

## Примеры использования API

### 1. CRUD для Продавцов

### Получение всех продавцов:

- **Метод**: `GET`
- **URL**: `/sellers`

**Пример ответа**:

```json
[
  {
    "id": 1,
    "name": "FirstSeller",
    "info": "First seller info"
  },
  {
    "id": 2,
    "name": "SecondSeller",
    "info": "Second seller info"
  }
]
```

## 2. CRUD для Транзакций

### Получение всех транзакций:
- **Метод**: `GET`
- **URL**: `/transactions`

**Пример запроса**:

```http
GET /transactions
```

**Пример ответа**:

```json
[
  {
    "id": 1,
    "seller": { "id": 1, "name": "FirstSeller" },
    "amount": 123.00,
    "paymentMethod": "Cash",
    "transactionDate": "2024-10-01T14:30:00"
  },
  {
    "id": 2,
    "seller": { "id": 2, "name": "SecondSeller" },
    "amount": 227.00,
    "paymentMethod": "Credit",
    "transactionDate": "2023-10-01T14:30:00"
  }
]
```

### Создание новой транзакции:
- **Метод**: `POST`
- **URL**: `/transactions/create`
- **Content-Type**: `application/json`

**Пример запроса**:

```http
POST /transactions/create
Content-Type: application/json
{
  "sellerId": 1,
  "amount": 150.50,
  "paymentMethod": "Card",
  "transactionDate": "2024-10-01T14:30:00"
}
```

**Пример ответа**:

```json
[
  {
    "id": 3,
    "seller": { "id": 1, "name": "FirstSeller" },
    "amount": 150.50,
    "paymentMethod": "Card",
    "transactionDate": "2024-10-01T14:30:00"
  }
]
```

## 3. Аналитические запросы

### Лучший день по продажам для продавца:
- **Метод**: `GET`
- **URL**: `/analytics/best-day/{sellerId}`

**Пример запроса**:

```http
GET /analytics/best-day/1
```

**Пример ответа**:

```json
"2024-10-01"
```

### Лучший продавец за день, квартал, год:
- **Метод**: `GET`
- **URL**:
    - `/analytics/best-seller-today`
    - `/analytics/best-seller-quarterly`
    - `/analytics/best-seller-this-year`

### Продавцы с транзакциями ниже суммы:
- **Метод**: `GET`
- **URL**: `/analytics/sellers-below?amount=200`

**Пример запроса**:

```http
GET /analytics/sellers-below?amount=200
```

## Заключение

Данная CRM-система предоставляет простой и удобный способ управления данными о продавцах и транзакциях, а также включает инструменты для проведения аналитики на основе транзакций. Используя REST API, вы можете легко интегрировать систему с другими приложениями.

## Тестирование

В проекте были созданы юнит-тесты для всех сервисов и контроллеров, что позволяет гарантировать стабильность и корректность работы системы. Тестирование включает в себя:

- **Типы тестов**: юнит-тесты и интеграционные тесты.
- **Инструменты**: JUnit и Mockito для написания и выполнения тестов.
- **Запуск тестов**: Тесты могут быть запущены с помощью Gradle, используя команду:

    ```bash
    ./gradlew test
    ```

Тестирование помогает обеспечить высокое качество кода и минимизировать количество ошибок в работе системы.