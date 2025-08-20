#!/bin/bash

echo "🚀 Starting Enhanced Pet Clinic Microservices System"
echo "=================================================="

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker is not running. Please start Docker first."
    exit 1
fi

# Start infrastructure services
echo "📦 Starting infrastructure services..."
docker-compose up -d

echo "⏳ Waiting for infrastructure to be ready..."
sleep 30

# Check if services are running
echo "🔍 Checking service status..."
docker-compose ps

echo ""
echo "🏥 Starting microservices..."
echo ""

# Function to start a service
start_service() {
    local service_name=$1
    local service_dir=$2
    local port=$3
    local health_endpoint=$4
    
    echo "Starting $service_name on port $port..."
    cd "$service_dir"
    
    # Check if Maven wrapper exists
    if [ -f "mvnw" ]; then
        ./mvnw spring-boot:run > "../logs/$service_name.log" 2>&1 &
    else
        mvn spring-boot:run > "../logs/$service_name.log" 2>&1 &
    fi
    
    local pid=$!
    echo "$service_name started with PID $pid"
    
    # Wait for service to be ready
    if [ -n "$health_endpoint" ]; then
        echo "Waiting for $service_name to be ready..."
        local max_attempts=30
        local attempt=1
        
        while [ $attempt -le $max_attempts ]; do
            if curl -s "http://localhost:$port$health_endpoint" > /dev/null 2>&1; then
                echo "✅ $service_name is ready!"
                break
            fi
            echo "⏳ Attempt $attempt/$max_attempts - Waiting for $service_name..."
            sleep 10
            attempt=$((attempt + 1))
        done
        
        if [ $attempt -gt $max_attempts ]; then
            echo "⚠️  Warning: $service_name may not be fully ready"
        fi
    fi
    
    cd ..
    sleep 5
}

# Create logs directory
mkdir -p logs

# Start services in order with health checks
start_service "Auth Server" "authServer" "8761" "/actuator/health"
start_service "API Gateway" "api-gateway" "8085" "/actuator/health"
start_service "Clinic Server" "clinicServer" "8080" "/actuator/health"
start_service "Donor Server" "donorServer" "8081" "/actuator/health"
start_service "Subscription Service" "subscription-service" "8086" "/actuator/health"
start_service "Pet Activities Service" "pet-activities-service" "8087" "/actuator/health"
start_service "Maps Service" "maps-service" "8088" "/actuator/health"
start_service "Account Service" "account-service" "8089" "/actuator/health"

echo ""
echo "✅ All services started!"
echo ""
echo "📊 Service Status:"
echo "=================="
echo "Auth Server (Eureka): http://localhost:8761"
echo "API Gateway: http://localhost:8085"
echo "Clinic Server: http://localhost:8080"
echo "Donor Server: http://localhost:8081"
echo "Subscription Service: http://localhost:8086"
echo "Pet Activities Service: http://localhost:8087"
echo "Maps Service: http://localhost:8088"
echo "Account Service: http://localhost:8089"
echo ""
echo "🔧 Infrastructure:"
echo "=================="
echo "Kafka UI: http://localhost:8080"
echo "MongoDB Express: http://localhost:8081"
echo "Prometheus: http://localhost:9090"
echo "Grafana: http://localhost:3000 (admin/admin)"
echo "Jaeger Tracing: http://localhost:16686"
echo "PostgreSQL: localhost:5432"
echo "MongoDB: localhost:27017"
echo "Redis: localhost:6379"
echo "MySQL: localhost:3306"
echo ""
echo "📝 Logs are available in the logs/ directory"
echo "🛑 To stop all services, run: ./stop-services.sh"
echo ""
echo "🎉 System is ready! You can now access the services."
echo ""
echo "💡 Quick Start Guide:"
echo "====================="
echo "1. Access API Gateway: http://localhost:8085"
echo "2. View service registry: http://localhost:8761"
echo "3. Monitor metrics: http://localhost:9090"
echo "4. View dashboards: http://localhost:3000"
echo "5. Check distributed traces: http://localhost:16686"
echo ""
echo "🔐 Default credentials:"
echo "======================="
echo "Grafana: admin/admin"
echo "PostgreSQL: postgres/password"
echo "MongoDB: admin/password"
echo "MySQL: root/root"
