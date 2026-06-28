\# Spring Boot Microservices Project



A complete microservices architecture built with Spring Boot, Docker, and AWS.



\## Services

| Service | Port | Description |

|---------|------|-------------|

| eureka-server | 8761 | Service Registry |

| api-gateway | 8080 | Entry point + JWT validation |

| auth-service | 8083 | Login, Register, JWT generation |

| user-service | 8081 | User management |

| policy-service | 8082 | Policy management + Circuit Breaker |

| mysql | 3307 | Database |



\## Tech Stack

\- Java 17, Spring Boot 3.x

\- Spring Security + JWT

\- Spring Cloud (Eureka, Gateway, Feign, Resilience4j)

\- Docker + Docker Compose

\- MySQL



\## How to Run



\### Prerequisites

\- Docker Desktop installed

\- Java 17



\### Steps

1\. Build all services:

&#x20;  cd each service folder and run:

&#x20;  mvnw.cmd clean package -DskipTests



2\. Start all containers:

&#x20;  cd microservices-docker

&#x20;  docker-compose up --build



3\. Access:

&#x20;  - Eureka Dashboard: http://localhost:8761

&#x20;  - API Gateway: http://localhost:8080



\## API Endpoints

| Method | URL | Auth Required | Role |

|--------|-----|---------------|------|

| POST | /auth/register | No | - |

| POST | /auth/login | No | - |

| GET | /users/{id} | Yes | USER, ADMIN |

| GET | /users | Yes | ADMIN only |

| POST | /users | Yes | ADMIN only |

| DELETE | /users/{id} | Yes | ADMIN only |

| GET | /policies/{id}/user | Yes | USER, ADMIN |



\## Architecture

Client → API Gateway → Eureka → Services → MySQL

JWT token required for all endpoints except /auth/\*\*

