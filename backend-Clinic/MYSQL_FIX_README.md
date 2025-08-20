# MySQL Configuration Fix for Pet Clinic Service

## Issues Identified

The MySQL container was failing to start due to several configuration problems:

1. **Deprecated Authentication Plugin**: `--default-authentication-plugin=mysql_native_password` is not supported in MySQL 8.4+
2. **Missing Component Tables**: The `mysql.component` table was missing, causing initialization failures
3. **GTID Table Issues**: Replication-related tables were not properly configured
4. **Port Mismatch**: Account service was configured for port 8081 but start script expected 8082

## Solutions Implemented

### 1. Updated Docker Compose Configuration (`docker-compose-simple.yml`)

- Changed MySQL version from `mysql:8` to `mysql:8.0` for better stability
- Added proper MySQL initialization script mounting
- Enhanced command line options with proper character set and SQL mode settings
- Added health check for MySQL container
- Created dedicated database user with proper permissions

### 2. Created MySQL Initialization Script (`mongo-init/01-init-mysql.sql`)

- Properly initializes the `we_pc` database
- Creates dedicated user `petclinic_user` with appropriate permissions
- Sets up proper SQL mode and character encoding
- Includes basic table structure for user accounts

### 3. Updated Account Service Configuration (`account-service/src/main/resources/application.yml`)

- Fixed port from 8081 to 8082 to match start script
- Updated database credentials to use new `petclinic_user`
- Enhanced database connection URL with proper parameters
- Changed `ddl-auto` from `none` to `update` for automatic table creation

### 4. Created Fix Script (`fix-mysql.sh`)

- Automatically stops and removes problematic MySQL container
- Cleans up data volume for fresh start
- Restarts MySQL with new configuration
- Tests connection and provides status information

## How to Fix

### Option 1: Use the Fix Script (Recommended)

```bash
cd backend-Clinic
./fix-mysql.sh
```

### Option 2: Manual Fix

```bash
cd backend-Clinic

# Stop and remove existing MySQL container
docker-compose -f docker-compose-simple.yml stop mysql
docker-compose -f docker-compose-simple.yml rm -f mysql

# Remove MySQL data volume
docker volume rm backend-clinic_mysql-data

# Start MySQL with new configuration
docker-compose -f docker-compose-simple.yml up -d mysql

# Wait for MySQL to be ready
sleep 30

# Test connection
docker exec petclinic-mysql mysql -u root -pChinadoll1002 -e "SELECT VERSION();"
```

## New Database Credentials

- **Host**: localhost
- **Port**: 3307
- **Database**: we_pc
- **Root Password**: Chinadoll1002
- **Application User**: petclinic_user
- **Application Password**: petclinic_pass

## Access via Adminer

- **URL**: http://localhost:8080
- **Server**: mysql
- **Username**: petclinic_user
- **Password**: petclinic_pass
- **Database**: we_pc

## Verification

After running the fix, verify MySQL is working:

```bash
# Check container status
docker-compose -f docker-compose-simple.yml ps mysql

# Check logs
docker-compose -f docker-compose-simple.yml logs mysql

# Test connection
docker exec petclinic-mysql mysql -u petclinic_user -ppetclinic_pass -e "USE we_pc; SHOW TABLES;"
```

## Next Steps

1. Run the fix script: `./fix-mysql.sh`
2. Wait for MySQL to fully initialize
3. Start your microservices: `./start-microservices.sh`
4. Verify all services can connect to the database

## Troubleshooting

If issues persist:

1. Check Docker logs: `docker-compose -f docker-compose-simple.yml logs mysql`
2. Verify container is running: `docker ps | grep mysql`
3. Check volume permissions: `docker volume ls | grep mysql`
4. Ensure ports are not conflicting: `netstat -an | grep 3307`
