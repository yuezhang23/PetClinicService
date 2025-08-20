# 🏥 Enhanced Pet Clinic Microservices System

A comprehensive, scalable microservices architecture for pet clinic management with advanced features including location services, account management, and event-driven workflows.

## 🏗️ Architecture Overview

The system is built using a microservices architecture with the following key components:

### Core Services
- **Auth Server** (Port 8761) - User authentication and authorization
- **API Gateway** (Port 8085) - Centralized routing and security
- **Clinic Server** (Port 8080) - Appointment and clinic management
- **Donor Server** (Port 8081) - Donation processing and management

### Enhanced Services
- **Subscription Service** (Port 8086) - Purchase and subscription management
- **Pet Activities Service** (Port 8087) - Pet activity tracking and analytics
- **Maps Service** (Port 8088) - Location services and route planning
- **Account Service** (Port 8089) - Comprehensive user account management

### Infrastructure
- **Kafka** - Event-driven communication and message streaming
- **PostgreSQL** - Primary relational database
- **MongoDB** - Document database for pet activities
- **Redis** - Caching and session management
- **MySQL** - Legacy service database
- **Prometheus** - Metrics collection and monitoring
- **Grafana** - Visualization and dashboards
- **Jaeger** - Distributed tracing

## 🚀 Features

### 🔐 Authentication & Security
- JWT-based authentication with role-based access control
- Two-factor authentication support
- Secure password management with encryption
- Session management and security policies

### 📍 Location & Mapping Services
- Clinic location management with GPS coordinates
- Pet activity tracking and location history
- Route planning between clinics
- Geospatial queries and proximity search
- Weather integration for outdoor activities

### 💳 Subscription & Payment Management
- Flexible subscription plans
- Multiple payment methods
- Purchase history and tracking
- Automated billing and renewals
- Event-driven payment processing

### 🐕 Pet Activity Management
- Comprehensive activity tracking
- Health metrics monitoring
- Exercise and nutrition logging
- Social features and sharing
- Analytics and insights

### 👥 Account Management
- User profile management
- Address and contact information
- Preference settings
- Account verification workflows
- Admin user management

### 📊 Monitoring & Observability
- Real-time metrics collection
- Distributed tracing
- Health checks and alerts
- Performance monitoring
- Log aggregation and analysis

## 🛠️ Technology Stack

- **Backend**: Java 17, Spring Boot 3.2, Spring Cloud 2023
- **Database**: PostgreSQL 15, MongoDB 7.0, MySQL 8.0, Redis 7
- **Message Broker**: Apache Kafka 7.4
- **Monitoring**: Prometheus, Grafana, Jaeger
- **Security**: Spring Security, JWT, BCrypt
- **API**: RESTful APIs with OpenAPI documentation
- **Containerization**: Docker, Docker Compose

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- Docker and Docker Compose
- At least 8GB RAM available
- Ports 8080-8089, 8761, 9090, 3000, 16686 available

## 🚀 Quick Start

### 1. Clone the Repository
```bash
git clone <repository-url>
cd PetClinicService/backend-Clinic
```

### 2. Start Infrastructure Services
```bash
./start-services.sh
```

This script will:
- Start all infrastructure services (databases, Kafka, monitoring)
- Launch all microservices
- Perform health checks
- Display service status and access URLs

### 3. Access Services
- **API Gateway**: http://localhost:8085
- **Service Registry**: http://localhost:8761
- **Monitoring**: http://localhost:9090 (Prometheus)
- **Dashboards**: http://localhost:3000 (Grafana)
- **Tracing**: http://localhost:16686 (Jaeger)

### 4. Stop Services
```bash
./stop-services.sh
```

## 🔧 Configuration

### Environment Variables
Key configuration files are located in each service's `src/main/resources/application.yml`:

- Database connections
- Kafka broker settings
- Service discovery configuration
- Security settings
- Monitoring endpoints

### Database Setup
The system automatically creates necessary databases and tables on first run. You can also run initialization scripts manually:

```bash
# PostgreSQL
psql -h localhost -U postgres -d petclinic_db -f init-scripts/init.sql

# MySQL
mysql -h localhost -u root -p we_pc < mysql-init/init.sql
```

## 📚 API Documentation

### API Gateway Routes
- `/auth/**` - Authentication services
- `/clinic/**` - Clinic and appointment management
- `/donor/**` - Donation services
- `/subscription/**` - Subscription and purchase management
- `/activities/**` - Pet activity tracking
- `/maps/**` - Location and mapping services
- `/accounts/**` - User account management

### Authentication
Most endpoints require JWT authentication. Include the token in the Authorization header:

```bash
curl -H "Authorization: Bearer <your-jwt-token>" \
     http://localhost:8085/clinic/appointments
```

### Public Endpoints
- `/auth/login` - User login
- `/auth/register` - User registration
- `/health` - Health checks
- `/public/**` - Public information

## 🔍 Monitoring & Debugging

### Health Checks
Each service provides health endpoints:
```bash
curl http://localhost:8080/actuator/health
curl http://localhost:8085/actuator/health
```

### Metrics
Prometheus collects metrics from all services:
- Application metrics
- JVM metrics
- Database connection metrics
- Custom business metrics

### Logs
Service logs are stored in the `logs/` directory:
```bash
tail -f logs/clinic-server.log
tail -f logs/api-gateway.log
```

### Tracing
Jaeger provides distributed tracing for request flows across services.

## 🧪 Testing

### Unit Tests
```bash
cd <service-directory>
mvn test
```

### Integration Tests
```bash
mvn verify -P integration-test
```

### API Testing
Use the provided Postman collection or test with curl:
```bash
# Test user registration
curl -X POST http://localhost:8085/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","email":"test@example.com","password":"password123","firstName":"Test","lastName":"User"}'
```

## 🔒 Security

### Authentication Flow
1. User registers/logs in via Auth Server
2. JWT token issued with user claims
3. API Gateway validates tokens for protected routes
4. Downstream services receive user context via headers

### Role-Based Access Control
- **USER**: Basic user operations
- **ADMIN**: Administrative functions
- **VETERINARIAN**: Medical staff operations
- **CLINIC_STAFF**: Clinic management

### Security Headers
The API Gateway adds security headers:
- X-User-ID
- X-User-Role
- X-User-Email
- X-User-Name

## 📈 Scaling & Performance

### Horizontal Scaling
Services can be scaled independently:
```bash
docker-compose up --scale clinic-server=3
docker-compose up --scale subscription-service=2
```

### Caching Strategy
- Redis for session storage
- Application-level caching for frequently accessed data
- Database query result caching

### Load Balancing
API Gateway provides load balancing across service instances using Eureka service discovery.

## 🚨 Troubleshooting

### Common Issues

#### Service Won't Start
1. Check if required ports are available
2. Verify Docker is running
3. Check service logs in `logs/` directory
4. Ensure all infrastructure services are running

#### Database Connection Issues
1. Verify database containers are running
2. Check connection strings in `application.yml`
3. Ensure database credentials are correct

#### Kafka Connection Issues
1. Check if Zookeeper and Kafka are running
2. Verify broker addresses in configuration
3. Check network connectivity between services

### Debug Mode
Enable debug logging by setting log level to DEBUG in `application.yml`:
```yaml
logging:
  level:
    com.example.WeePetClinic: DEBUG
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🆘 Support

For support and questions:
- Create an issue in the repository
- Check the troubleshooting section
- Review service logs for error details
- Consult the API documentation

## 🔮 Future Enhancements

- Mobile app integration
- AI-powered pet health insights
- Advanced analytics and reporting
- Multi-language support
- Integration with external veterinary systems
- Blockchain for medical records
- IoT device integration for pet monitoring

---

**Happy coding! 🐾**
