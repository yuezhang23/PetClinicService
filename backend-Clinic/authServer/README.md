# Pet Clinic Eureka Server

This is the Eureka Server for the Pet Clinic microservices architecture. It serves as the service discovery and registration center for all the microservices in the Pet Clinic system.

## Features

- **Service Discovery**: Centralized service registry for all microservices
- **Role-Based Authentication**: Handles user authentication and role management
- **Load Balancing**: Automatic load balancing across service instances
- **Health Monitoring**: Monitors the health of registered services
- **High Availability**: Supports multiple Eureka server instances for high availability

## Architecture

The Eureka Server is part of a microservices architecture that includes:

- **auth-server** (this service): Eureka Server and authentication service
- **clinic-server**: Pet clinic management service
- **donor-server**: Donation management service
- **frontend-wee-clinic**: React frontend application

## User Roles

The system supports the following user roles:

1. **pet_owner**: Pet owners who can manage their pets and appointments
2. **veterinarian**: Veterinarians who can manage appointments and treatments
3. **administrative**: Administrative staff who can manage the clinic
4. **accountant**: Accountants who can manage billing and financial records
5. **donor**: Donors who can make donations to the clinic

## API Endpoints

### Authentication Endpoints

- `POST /clinic/login` - User login with role-based authentication
- `GET /clinic/emps` - Get all employees
- `POST /clinic/service/current={role}` - Switch to a specific role
- `POST /clinic/service/current={role}/{request}` - Execute role-specific requests
- `GET /clinic/service/clinic={clinic_id}` - Get clinic information
- `GET /clinic/service/profile/role=vet/appointment={apt_id}` - Get veterinarian profile
- `GET /clinic/register?usertype={type}` - Check user registration status

### Eureka Server Endpoints

- `GET /` - Eureka Server dashboard
- `GET /eureka/apps` - List all registered applications
- `GET /eureka/apps/{appName}` - Get specific application information

## Configuration

### Application Properties

```properties
# Eureka Server Configuration
spring.application.name=auth-server
server.port=8761

# Eureka Server Settings
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
eureka.server.enable-self-preservation=false
eureka.server.eviction-interval-timer-in-ms=1000

# Database Configuration
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true

# JPA Configuration
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect
```

## Running the Application

### Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- Spring Boot 3.3.2

### Steps to Run

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd backend-Clinic/authServer
   ```

2. **Build the application**:
   ```bash
   mvn clean install
   ```

3. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**:
   - Eureka Dashboard: http://localhost:8761
   - H2 Console: http://localhost:8761/h2-console
   - API Base URL: http://localhost:8761/clinic

## Service Registration

Other microservices can register with this Eureka Server by adding the following configuration:

```properties
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
spring.application.name=<service-name>
```

## Development

### Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/example/WeePetClinic/
│   │       ├── AuthServerApplication.java
│   │       └── Components/
│   │           ├── Controller/
│   │           │   └── AuthManger.java
│   │           ├── Service/
│   │           │   ├── ServiceUserLogin.java
│   │           │   ├── ServiceUserLoginImpl.java
│   │           │   ├── ServiceClientPetOwner.java
│   │           │   ├── ServiceEmpVet.java
│   │           │   ├── ServiceEmpAdmin.java
│   │           │   ├── ServiceClientDonor.java
│   │           │   └── doubleRole/
│   │           │       └── ServiceEmpAccountant.java
│   │           ├── RestController/
│   │           │   ├── Manager.java
│   │           │   ├── petOwnerManagerImpl.java
│   │           │   ├── adminManager.java
│   │           │   ├── vetManagerImpl.java
│   │           │   ├── accountantManager.java
│   │           │   ├── donorManager.java
│   │           │   ├── requestDTO/
│   │           │   │   └── logInReq.java
│   │           │   └── reponseDTO/
│   │           │       └── ProjUser.java
│   │           ├── Repository/
│   │           │   └── repoDTO/
│   │           │       ├── ProjUser.java
│   │           │       ├── ProjVetEmpDetail.java
│   │           │       └── ProjAppointmentDetail.java
│   │           └── Model/
│   │               ├── UserOri.java
│   │               ├── InvoiceOri.java
│   │               └── forSql/
│   │                   ├── User/
│   │                   ├── Pet/
│   │                   └── clinicService/
│   └── resources/
│       └── application.properties
└── test/
    └── java/
        └── com/example/WeePetClinic/
            └── AuthServerApplicationTests.java
```

### Key Components

1. **AuthManger**: Main controller handling authentication and role-based access
2. **ServiceUserLogin**: Service for user authentication and role management
3. **Manager Interface**: Base interface for role-specific managers
4. **Role Managers**: Specific implementations for each user role
5. **DTOs**: Data transfer objects for request/response handling

## Testing

### Unit Tests

Run the unit tests:
```bash
mvn test
```

### Integration Tests

The application includes integration tests for the authentication flow and Eureka Server functionality.

## Monitoring and Health Checks

- **Health Check**: http://localhost:8761/actuator/health
- **Metrics**: http://localhost:8761/actuator/metrics
- **Info**: http://localhost:8761/actuator/info

## Troubleshooting

### Common Issues

1. **Port Already in Use**: Change the port in `application.properties`
2. **Service Registration Issues**: Check Eureka client configuration
3. **Database Connection**: Ensure H2 database is properly configured

### Logs

Check the application logs for detailed error information:
```bash
tail -f logs/auth-server.log
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details. 