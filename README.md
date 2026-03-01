# Microservices Lab – Spring Boot + Docker

## 1. Project Overview
This project implements a complete microservices-based system using Spring Boot and Docker.

The system contains four services:
- `item-service` (Port `8081`)
- `order-service` (Port `8082`)
- `payment-service` (Port `8083`)
- `api-gateway` (Port `8080`)

All external requests must go through the API Gateway on port `8080`.

## 2. System Architecture (Text-Based Diagram)
```text
Client (Postman / Browser)
          |
          v
   +-------------------+
   |    api-gateway    |  :8080
   +-------------------+
      |       |       |
      v       v       v
+-----------+ +-----------+ +---------------+
|item-service| |order-service| |payment-service|
|   :8081    | |   :8082    | |    :8083      |
+-----------+ +-----------+ +---------------+

Docker Network: microservices-net
```

## 3. Services Description
### item-service
- Manages item catalog in memory
- Base path: `/items`

### order-service
- Creates and fetches orders in memory
- Base path: `/orders`

### payment-service
- Processes and tracks payments in memory
- Base path: `/payments`

### api-gateway
- Single entry point for all client requests
- Routes:
  - `/items/**` -> `item-service:8081`
  - `/orders/**` -> `order-service:8082`
  - `/payments/**` -> `payment-service:8083`

## 4. API Endpoints Table
| Service | Method | Endpoint (via Gateway) | Description |
|---|---|---|---|
| item-service | GET | `http://localhost:8080/items` | Get all items |
| item-service | GET | `http://localhost:8080/items/{id}` | Get item by ID |
| item-service | POST | `http://localhost:8080/items` | Add new item |
| order-service | GET | `http://localhost:8080/orders` | Get all orders |
| order-service | GET | `http://localhost:8080/orders/{id}` | Get order by ID |
| order-service | POST | `http://localhost:8080/orders` | Place order |
| payment-service | GET | `http://localhost:8080/payments` | Get all payments |
| payment-service | GET | `http://localhost:8080/payments/{id}` | Get payment by ID |
| payment-service | POST | `http://localhost:8080/payments/process` | Process payment |

## 5. Sample Request Bodies
### Add Item
```json
{
  "name": "Tablet"
}
```

### Place Order
```json
{
  "item": "Laptop",
  "quantity": 2,
  "customerId": "C001"
}
```

### Process Payment
```json
{
  "orderId": 1,
  "amount": 1299.99,
  "method": "CARD"
}
```

## 6. How to Run the Project (Step-by-step)
### Step 1: Build all services
```bash
cd item-service && ./mvnw clean package -DskipTests
cd ../order-service && ./mvnw clean package -DskipTests
cd ../payment-service && ./mvnw clean package -DskipTests
cd ../api-gateway && ./mvnw clean package -DskipTests
cd ..
```

### Step 2: Build Docker images
```bash
docker-compose build
```

### Step 3: Start all containers
```bash
docker-compose up -d
```

### Step 4: Verify running services
```bash
docker compose ps
```

## 7. Testing Instructions (Postman Examples)
1. Open Postman.
2. Import collection: `Item-Service.postman_collection.json`.
3. Ensure Gateway-first testing using `http://localhost:8080`.
4. Run example requests:
   - `GET http://localhost:8080/items`
   - `POST http://localhost:8080/orders`
   - `POST http://localhost:8080/payments/process`
5. Use Postman Collection Runner to execute all requests.

## 8. Docker Commands Reference
```bash
# Build all images
docker-compose build

# Start all services
docker-compose up -d

# View running containers
docker compose ps

# View logs
docker compose logs -f

# View logs of one service
docker compose logs -f api-gateway

# Stop and remove containers
docker-compose down

# Stop and remove containers + images
docker-compose down --rmi local
```

## 9. Folder Structure
```text
Microservice/
├── README.md
├── DOCKER_COMPOSE_SETUP.md
├── docker-compose.yml
├── Item-Service.postman_collection.json
├── item-service/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
├── order-service/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
├── payment-service/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
└── api-gateway/
    ├── Dockerfile
    ├── pom.xml
    └── src/
```

## 10. Screenshots Section (Placeholder)
Add screenshots here before submission:
- [ ] Docker containers running (`docker compose ps`)
- [ ] API Gateway routing success
- [ ] Postman test results for Items
- [ ] Postman test results for Orders
- [ ] Postman test results for Payments

## 11. Submission Details
- Module: `SE4010 - CTSE`
- Assignment: `Microservices Lab – Spring Boot + Docker`

## 12. Author
- Name: `THAMEL W M A`
- Student ID: `IT22195234`
- Batch/Group: `SE2026`
