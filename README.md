# PetClinicService

A comprehensive pet clinic management system with microservices architecture, featuring authentication, clinic operations, donation management, and a modern React frontend.

## 🏗️ Project Structure

```
PetClinicService/
├── backend-Clinic/           # Backend microservices
│   ├── authServer/          # Authentication & Eureka Server
│   ├── clinicServer/        # Main clinic operations
│   └── donorServer/         # Donation management
├── frontend-wee-clinic/     # React TypeScript frontend
└── ls/                      # Astro-based landing page
```

## 🚀 Quick Start

### Prerequisites

- **Java 11** or higher
- **Node.js** 18+ and **npm** or **yarn**
- **MySQL** 8.0+
- **PostgreSQL** 13+ (for donation server)
- **Maven** 3.6+

### Backend Services

#### 1. Authentication Server (Eureka Server)

```bash
cd backend-Clinic/authServer
mvn spring-boot:run
```

- **Port:** 8761
- **Purpose:** Service discovery and authentication
- **Technology:** Spring Boot 3.3.2, Spring Cloud Netflix Eureka

#### 2. Clinic Server

```bash
cd backend-Clinic/clinicServer
mvn spring-boot:run
```

- **Port:** 8080 (default)
- **Purpose:** Main clinic operations, appointments, pet management
- **Technology:** Spring Boot 2.7.18, MySQL, JPA
- **Database:** MySQL (`we_pc`)

#### 3. Donor Server

```bash
cd backend-Clinic/donorServer
mvn spring-boot:run
```

- **Port:** 8081 (default)
- **Purpose:** Donation management and user services
- **Technology:** Spring Boot 2.7.18, MySQL + PostgreSQL, JPA
- **Databases:** 
  - MySQL (`we_pc`) - for clinic data
  - PostgreSQL (`donation`) - for donation data

### Frontend Application

#### React Frontend

```bash
cd frontend-wee-clinic
npm install
npm start
```

- **Port:** 3000
- **Technology:** React 18, TypeScript, Redux Toolkit, React Router
- **Features:** 
  - User authentication
  - Pet management
  - Appointment scheduling
  - Donation tracking
  - Employee management

#### Astro Landing Page

```bash
cd ls
npm install
npm run dev
```

- **Port:** 4321 (default)
- **Technology:** Astro 4.15, React, Tailwind CSS

## 🗄️ Database Setup

### MySQL Database

Create the `we_pc` database:

```sql
CREATE DATABASE we_pc;
```

### PostgreSQL Database

Create the `donation` database:

```sql
CREATE DATABASE donation;
```

### Database Configuration

Update the database credentials in the respective `application.properties` files:

- `backend-Clinic/clinicServer/src/main/resources/application.properties`
- `backend-Clinic/donorServer/src/main/resources/application.properties`
- `backend-Clinic/authServer/src/main/resources/application.properties`

## 🏥 System Features

### Core Functionality

- **User Management:** Registration, authentication, profile management
- **Pet Management:** Pet registration, medical records, vaccination tracking
- **Appointment System:** Scheduling, rescheduling, cancellation
- **Employee Management:** Staff profiles, role-based access
- **Billing & Invoicing:** Payment processing, invoice generation
- **Donation System:** Donor management, donation tracking
- **Clinic Operations:** Service management, resource allocation

### Technical Features

- **Microservices Architecture:** Decoupled services with service discovery
- **Multi-Database Support:** MySQL and PostgreSQL integration
- **RESTful APIs:** Comprehensive API endpoints
- **Security:** JWT authentication, role-based access control
- **Real-time Updates:** WebSocket support for live updates
- **Responsive Design:** Mobile-first approach

## 🛠️ Development

### Backend Development

The backend follows a microservices pattern with:

- **Model Layer:** JPA entities and DTOs
- **Repository Layer:** Data access objects
- **Service Layer:** Business logic
- **Controller Layer:** REST endpoints
- **Configuration:** Multi-datasource configuration

### Frontend Development

The React frontend includes:

- **Component Architecture:** Reusable UI components
- **State Management:** Redux Toolkit for global state
- **Routing:** React Router for navigation
- **TypeScript:** Type-safe development
- **Styling:** CSS modules and Bootstrap

## 📁 Key Directories

### Backend Structure

```
backend-Clinic/
├── authServer/
│   └── src/main/java/com/example/WeePetClinic/
│       ├── AuthServerApplication.java
│       └── Components/
│           ├── Controller/          # REST controllers
│           ├── Model/              # JPA entities
│           ├── Repository/         # Data access
│           └── Service/            # Business logic
├── clinicServer/
│   └── src/main/java/com/example/WeePetClinic/
│       ├── ClinicServer.java
│       └── Components/
│           ├── RestController/     # REST endpoints
│           ├── Model/             # Entities and DTOs
│           ├── Repository/        # Data repositories
│           └── Service/           # Business services
└── donorServer/
    └── src/main/java/com/example/WeePetClinic/
        ├── DonationApplication.java
        └── Components/
            ├── CController/        # Controllers
            ├── Model/             # Data models
            ├── Repository/        # Data access
            └── Service/           # Business logic
```

### Frontend Structure

```
frontend-wee-clinic/
├── src/
│   ├── components/               # Reusable UI components
│   ├── hooks/                   # Custom React hooks
│   ├── project/                 # Main application pages
│   │   ├── Home/               # Home page components
│   │   ├── User/               # User management
│   │   ├── Donate/             # Donation features
│   │   └── Claim/              # Claim management
│   ├── utils/                  # Utility functions
│   └── App.tsx                 # Main application component
```

## 🔧 Configuration

### Environment Variables

Create `.env` files in the respective directories:

```bash
# Database Configuration
DB_HOST=localhost
DB_PORT=3306
DB_NAME=we_pc
DB_USERNAME=root
DB_PASSWORD=your_password

# PostgreSQL Configuration
POSTGRES_HOST=localhost
POSTGRES_PORT=5432
POSTGRES_DB=donation
POSTGRES_USER=postgres
POSTGRES_PASSWORD=your_password
```

### Application Properties

Key configuration files:

- `backend-Clinic/*/src/main/resources/application.properties`
- `frontend-wee-clinic/.env`

## 🧪 Testing

### Backend Testing

```bash
# Run tests for all services
cd backend-Clinic/authServer && mvn test
cd backend-Clinic/clinicServer && mvn test
cd backend-Clinic/donorServer && mvn test
```

### Frontend Testing

```bash
cd frontend-wee-clinic
npm test
```

## 🚀 Deployment

### Docker Deployment

Build and run with Docker:

```bash
# Build backend services
docker build -t petclinic-auth ./backend-Clinic/authServer
docker build -t petclinic-clinic ./backend-Clinic/clinicServer
docker build -t petclinic-donor ./backend-Clinic/donorServer

# Build frontend
docker build -t petclinic-frontend ./frontend-wee-clinic

# Run with docker-compose
docker-compose up -d
```

### Production Deployment

1. **Database Setup:** Configure production databases
2. **Environment Configuration:** Set production environment variables
3. **Service Deployment:** Deploy each microservice independently
4. **Frontend Deployment:** Build and deploy React application
5. **Load Balancer:** Configure reverse proxy (nginx/Apache)

## 📊 Monitoring

- **Application Metrics:** Spring Boot Actuator
- **Database Monitoring:** Connection pooling and query performance
- **Frontend Monitoring:** React DevTools and performance monitoring

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🆘 Support

For support and questions:

1. Check the [Issues](../../issues) page
2. Review the documentation in each service directory
3. Contact the development team

## 🔄 Version History

- **v1.0.0** - Initial release with core functionality
- **v1.1.0** - Added donation system
- **v1.2.0** - Enhanced UI/UX and mobile responsiveness
- **v1.3.0** - Microservices architecture implementation

---

**Note:** This is a comprehensive pet clinic management system designed for scalability and maintainability. The microservices architecture allows for independent development and deployment of each service component.
