# Pet Clinic Service Refactoring

This document describes the refactoring changes made to simplify and consolidate the Pet Clinic microservices architecture.

## 🚀 Changes Made

### 1. Unified Service Management Script

**Before**: Multiple shell scripts (`start-simple.sh`, `start-microservices.sh`, `stop-microservices.sh`, `stop-simple.sh`, `fix-mysql.sh`)

**After**: Single `manage-services.sh` script with all functionality

**Usage**:
```bash
# Start infrastructure services
./manage-services.sh infrastructure

# Start all microservices
./manage-services.sh start

# Check status
./manage-services.sh status

# Stop services
./manage-services.sh stop

# View logs
./manage-services.sh logs "Auth Server"
```

### 2. Database Configuration Cleanup

**Donor Service**: Now uses **PostgreSQL only** (removed MySQL dependency)
- Updated `application.properties` to use PostgreSQL
- Removed MySQL configuration files
- Simplified data source configuration

**Other Services**: Continue using **MySQL** as before
- Account service (now merged with auth-server)
- Clinic server
- Legacy authentication system

### 3. API Gateway Route Cleanup

**Removed Routes**:
- `subscription-service` (not essential for core functionality)
- `pet-activities-service` (not essential for core functionality)
- `account-service` (merged with auth-server)

**Simplified Routes**:
- `/auth/**` → Unified authentication (merged account-service + auth-server)
- `/clinic/**` → Clinic services
- `/donor/**` → Donor services (PostgreSQL-based)
- `/maps/**` → Maps services (PostgreSQL-based)
- `/health/**` → Health checks
- `/public/**` → Public information

### 4. Unified Authentication System

**Merged Services**: `account-service` + `auth-server`

**New Architecture**:
- **Single authentication endpoint**: `/auth/login`
- **Unified approach**: Tries new account service first, falls back to legacy authentication
- **Role assignment**: Based on successful login from either system
- **JWT tokens**: For new users, legacy tokens for existing users

**Key Components**:
- `UnifiedAuthController`: Single controller for all auth operations
- `UnifiedAuthService`: Combines both authentication approaches
- `UserAccountService`: Manages new user accounts
- `LegacyAuthService`: Wraps existing legacy authentication
- `JwtService`: JWT token generation and validation

**Authentication Flow**:
1. User attempts login with username/password
2. System tries new account service authentication first
3. If successful, creates JWT tokens and assigns user role
4. If fails, tries legacy authentication system
5. If legacy succeeds, creates legacy token and returns role information
6. User role is determined from successful authentication method

## 🗄️ Database Architecture

### PostgreSQL (Port 5433)
- **Services**: Donor Service, Maps Service
- **Database**: `petclinic_db`
- **Purpose**: Modern services with relational data

### MySQL (Port 3307)
- **Services**: Auth Server (merged), Clinic Server
- **Database**: `we_pc`
- **Purpose**: Legacy services and user authentication

## 🔧 Configuration Files

### Updated Files
- `docker-compose-simple.yml`: Simplified infrastructure
- `manage-services.sh`: Unified service management
- `api-gateway/application.yml`: Cleaned up routes
- `authServer/application.properties`: MySQL configuration
- `donorServer/application.properties`: PostgreSQL only

### New Files
- `authServer/account/`: Unified authentication components
- `authServer/account/model/UserAccount.java`: User account model
- `authServer/account/service/UnifiedAuthService.java`: Main auth service
- `authServer/account/controller/UnifiedAuthController.java`: Auth controller
- `authServer/account/config/SecurityConfig.java`: Security configuration

## 🚀 Quick Start

### 1. Start Infrastructure
```bash
cd backend-Clinic
./manage-services.sh infrastructure
```

### 2. Start Microservices
```bash
./manage-services.sh start
```

### 3. Check Status
```bash
./manage-services.sh status
```

### 4. Access Services
- **Eureka Dashboard**: http://localhost:8761
- **API Gateway**: http://localhost:8085
- **Database Admin**: http://localhost:8080

## 🔄 Migration Notes

### For Existing Users
- Legacy authentication continues to work
- New users can register through `/auth/register`
- JWT tokens for new users, legacy tokens for existing users

### For Developers
- Account service code moved to `authServer/account/`
- Donor service now PostgreSQL-only
- Simplified service management with single script

## 🧹 Cleanup Benefits

1. **Reduced Complexity**: Single management script instead of 5 separate ones
2. **Unified Authentication**: One approach for all users
3. **Cleaner Architecture**: Removed unnecessary services and routes
4. **Better Maintainability**: Consolidated code and configuration
5. **Simplified Deployment**: Fewer moving parts and dependencies

## 📝 Future Considerations

- Consider migrating all services to PostgreSQL for consistency
- Implement proper JWT token invalidation for logout
- Add comprehensive logging and monitoring
- Consider implementing API rate limiting
- Add health checks for all services
