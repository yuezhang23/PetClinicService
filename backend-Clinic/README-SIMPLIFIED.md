# Pet Clinic Simplified Setup

This simplified setup provides only the essential infrastructure services needed for your Pet Clinic project.

## 🚀 Quick Start

### Start Services
```bash
./start-simple.sh
```

### Stop Services
```bash
./stop-simple.sh
```

## 📊 Available Services

| Service | Port | Purpose | Credentials |
|---------|------|---------|-------------|
| **PostgreSQL** | 5433 | Main database | user: `postgres`, password: `password`, db: `petclinic_db` |
| **Redis** | 6379 | Caching & sessions | No auth required |
| **Adminer** | 8080 | Database management UI | Use PostgreSQL credentials above |

## 🔧 Configuration for Your Java Services

### PostgreSQL Connection
Update your `application.properties` or `application.yml` files:

```properties
# For services using PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5433/petclinic_db
spring.datasource.username=postgres
spring.datasource.password=password
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

### Redis Connection
```properties
# Redis configuration
spring.redis.host=localhost
spring.redis.port=6379
```

## 🗄️ Database Management

- **Access Adminer**: http://localhost:8080
- **Select PostgreSQL** from the dropdown
- **Server**: localhost
- **Port**: 5433
- **Username**: postgres
- **Password**: password
- **Database**: petclinic_db

## 📁 What's Included

✅ **Essential Services Only:**
- PostgreSQL database
- Redis cache
- Adminer (database UI)

❌ **Removed Complex Services:**
- Kafka & Zookeeper
- MongoDB
- MySQL
- Prometheus
- Grafana
- Jaeger
- Multiple monitoring tools

## 🔄 Migration from Complex Setup

If you were using the complex setup before:

1. **Update database ports** in your Java services from 5432 to 5433
2. **Remove Kafka dependencies** if not needed immediately
3. **Update Redis connections** if you were using different ports
4. **Test your services** with the simplified setup

## 🚨 Troubleshooting

### Port Already in Use
- PostgreSQL: Change port in `docker-compose-simple.yml` (currently 5433)
- Redis: Change port if 6379 is busy

### Service Won't Start
- Check if Docker Desktop is running
- Run `docker-compose -f docker-compose-simple.yml logs [service-name]` for errors

### Database Connection Issues
- Verify services are running: `docker-compose -f docker-compose-simple.yml ps`
- Check port mappings in the output
- Test connection through Adminer first

## 📈 When to Add More Services

Consider adding these back when you need:

- **Kafka**: Event streaming between services
- **MongoDB**: Document storage for specific services
- **Monitoring**: Prometheus + Grafana for production
- **Tracing**: Jaeger for distributed tracing

## 🎯 Next Steps

1. **Test the setup** by accessing Adminer
2. **Set up the database schema** using the provided SQL script
3. **Start your microservices** using the simplified startup script
4. **Verify connectivity** between services and databases

## 🚀 Quick Start with Microservices

### 1. Start Infrastructure
```bash
./start-simple.sh
```

### 2. Set up Database Schema
```bash
# Access Adminer at http://localhost:8080
# Connect to PostgreSQL (localhost:5433, postgres/password)
# Run the setup-database.sql script
```

### 3. Start All Microservices
```bash
./start-microservices.sh
```

### 4. Stop Microservices
```bash
./stop-microservices.sh
```

### 5. Stop Infrastructure
```bash
./stop-simple.sh
```

## 📊 Service Ports

| Service | Port | Purpose |
|---------|------|---------|
| **Auth Server** | 8761 | Service Discovery (Eureka) |
| **API Gateway** | 8085 | Main entry point |
| **Clinic Server** | 8081 | Core clinic services |
| **Donor Server** | 8083 | Donation management |
| **Account Service** | 8082 | User account management |
| **Maps Service** | 8088 | Location services |
| **Subscription Service** | 8086 | Subscription management |
| **Pet Activities Service** | 8087 | Activity tracking |

This simplified setup gives you a solid foundation to build upon! 🚀
