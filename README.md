🚀 SolarHub Installment Payment Engine

A smart backend system for managing **solar financing installment payments**, built with **Spring Boot + Java**, featuring intelligent retry logic, portfolio analytics, and payment simulation.

---

 📌 Table of Contents

* Tech Stack
* System Architecture
* Domain Model
* Database Schema
* Core Features
* Retry Engine Logic
* API Documentation (Swagger)
* Setup Instructions
* Test Data Strategy
* JUnit Testing
* Design Decisions

---

 🧰 Tech Stack

| Layer         | Technology                       |
| ------------- | -------------------------------- |
| Backend       | Java 17 + Spring Boot 3          |
| Database      | PostgreSQL (or H2 for local dev) |
| ORM           | Spring Data JPA (Hibernate)      |
| API           | REST APIs                        |
| Documentation | SpringDoc OpenAPI (Swagger UI)   |
| Testing       | JUnit 5 + Mockito                |
| Build Tool    | Maven                            |

---

 🏗 System Architecture

```
┌──────────────────────────────┐
│        REST Controllers      │
│  - RetryController           │
│  - PortfolioController       │
│  - CustomerController        │
└────────────┬─────────────────┘
             ↓
┌──────────────────────────────┐
│        Service Layer         │
│ - RetryEngineService        │
│ - PortfolioHealthService    │
│ - PaymentSimulationService  │
└────────────┬─────────────────┘
             ↓
┌──────────────────────────────┐
│       Repository Layer       │
│ - InstallmentRepository      │
│ - PaymentAttemptRepository   │
│ - CustomerRepository         │
└────────────┬─────────────────┘
             ↓
┌──────────────────────────────┐
│        PostgreSQL DB         │
└──────────────────────────────┘
```

---

 📦 Domain Model

 👤 Customer

* id (UUID)
* firstName
* lastName
* email
* country (MEXICO, PERU, COLOMBIA)
* riskScore

---

 📑 InstallmentSchedule

* id
* customerId
* totalInstallments
* installmentAmount
* currency (MXN, COP, PEN)
* paymentMethodType (CARD, BANK_TRANSFER, CASH)
* startDate

---

 💳 Installment

* id
* scheduleId
* installmentNumber
* amount
* dueDate
* status (PENDING, RETRYING, PAID, DELINQUENT)
* retryCount
* nextRetryAt

---

 🔁 PaymentAttempt

* id
* installmentId
* attemptedAt
* success
* amount
* declineReason (nullable for success)
* paymentMethodType

---

 🗄 Database Schema (Simplified)

```sql
CUSTOMERS
---------
id (UUID)
first_name
last_name
email
country
risk_score


INSTALLMENT_SCHEDULES
---------------------
id
customer_id
total_installments
installment_amount
currency
payment_method_type
start_date


INSTALLMENTS
------------
id
schedule_id
installment_number
amount
due_date
status
retry_count
next_retry_at


PAYMENT_ATTEMPTS
----------------
id
installment_id
attempted_at
success
amount
decline_reason
payment_method_type
```

---

 ⚙ Core Features

 ✅ 1. Installment Management

* Multi-currency support (MXN, COP, PEN)
* Payment method tracking
* Customer-linked financing schedules

 ✅ 2. Payment Simulation Engine

* Simulates real payment gateway behavior
* 65% success rate
* Soft + hard decline classification

 ✅ 3. Smart Retry Engine

* Auto-retries failed payments
* Smart delay rules based on failure type
* Max 5 retries
* 10-day grace period enforcement

 ✅ 4. Portfolio Health API

* Collection rate
* Pending retries
* Delinquent accounts
* Decline breakdown
* Estimated recovery score

---

 🔁 Retry Engine Logic

 Rules Engine

| Condition       | Action           |
| --------------- | ---------------- |
| Success         | Mark PAID        |
| Hard decline    | Mark DELINQUENT  |
| Soft decline    | Retry with delay |
| Retry count ≥ 5 | DELINQUENT       |
| Past 10 days    | DELINQUENT       |

---

 Retry Delays

```java
INSUFFICIENT_FUNDS → +3 days
ISSUER_TIMEOUT → +2 hours
PROCESSOR_UNAVAILABLE → +2 hours
RATE_LIMIT_EXCEEDED → +6 hours
```

---

 Flow

```
FAILED PAYMENT
      ↓
Check decline type
      ↓
Soft? → schedule retry
Hard? → delinquent
      ↓
Retry via simulator
      ↓
Update installment status
```

---

 📡 API Documentation

 🔁 Retry Engine

 POST

```
/api/retries/process
```

 Response

```json
{
  "processed": 12
}
```

---

 📊 Portfolio Health

 GET

```
/api/portfolio/health
```

 Response

```json
{
  "collectionRate": 82.5,
  "pendingRetryCount": 9,
  "delinquentCount": 0,
  "estimatedRecovery": 6,
  "declineBreakdown": {
    "INSUFFICIENT_FUNDS": 53,
    "ISSUER_TIMEOUT": 46
  }
}
```

---

 👤 Customers

 GET

```
/api/customers
```

 GET by ID

```
/api/customers/{id}
```

---

 📘 Swagger Documentation

 URL

```
http://localhost:8080/swagger-ui
```

 OpenAPI JSON

```
http://localhost:8080/api-docs
```

---

 Swagger Features

* Tagged controllers (Retry, Portfolio, Customers)
* Request/Response documentation
* Operation summaries
* Interactive API testing

---

 🧪 Test Data Strategy

Generated via `CommandLineRunner`

 Includes:

* 50+ customers
* 200+ installment schedules
* 800+ payment attempts

 Distribution:

* 65% success
* 25% soft decline
* 10% hard decline

 Edge Cases:

* Always-paid customers
* Always-failing customers
* Retry-heavy customers
* Delinquent accounts
* Mixed behavior users

---

 🧪 JUnit Testing Strategy

 Unit Tests

 Retry Engine

* Soft decline retry validation
* Hard decline no retry validation
* Max retry enforcement
* Grace period enforcement

 Example

```java
@Test
void shouldMarkInstallmentAsPaidWhenSuccess() {
    PaymentResult result = PaymentResult.success();
    assertTrue(result.success());
}
```

---

 Repository Tests

* Verify decline breakdown query
* Validate installment filtering

---

 Service Tests

* Retry processing logic
* Portfolio aggregation accuracy

---

 🧠 Design Decisions

 1. Event-driven retry logic (simulated)

Instead of external queue, retry is time-based query driven.

---

 2. Stateless retry engine

Retry process is idempotent and safe to re-run.

---

 3. Separation of concerns

* Simulation layer (payment gateway mock)
* Retry engine (business logic)
* Portfolio analytics (reporting layer)

---

 4. Real-world fintech alignment

* Soft vs hard decline modeling
* Grace period enforcement
* Risk scoring readiness
* Multi-country support

---

 📈 Possible Improvements (Mention in interview)

* Kafka-based async retry queue
* Redis for retry scheduling
* Circuit breaker for payment provider
* ML-based risk scoring
* Real PSP integration (Stripe/Yuno)

---

 🏁 How to Run

```
mvn clean install
mvn spring-boot:run
```
Swagger UI

Interactive API documentation:
```
http://localhost:8080/swagger-ui
```
OpenAPI JSON:
```
http://localhost:8080/api-docs
```

Key Design Decisions
1. Stateless Retry Engine

System can safely re-run retry process anytime without duplication or side effects.

2. Time-based Orchestration

No message queues are used — retry logic is fully driven by timestamps (nextRetryAt, dueDate, grace period rules).

3. Simulation-based Payment Service (PSP Mock)

No external payment gateway integration is required.
Payment success/failure is simulated using controlled random logic to mimic real-world behavior.
