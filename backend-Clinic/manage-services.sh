#!/bin/bash

# Pet Clinic Service Management Script
# This script combines all service management functionality

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Configuration
DOCKER_COMPOSE_SIMPLE="docker-compose-simple.yml"
LOGS_DIR="logs"
SERVICES=(
    "Auth Server:authServer:8761"
    "Clinic Server:clinicServer:8081"
    "Donor Server:donorServer:8083"
    "Maps Service:maps-service:8088"
    "API Gateway:api-gateway:8085"
)

# Function to print colored output
print_status() {
    echo -e "${GREEN}✅ $1${NC}"
}

print_warning() {
    echo -e "${YELLOW}⚠️  $1${NC}"
}

print_error() {
    echo -e "${RED}❌ $1${NC}"
}

print_info() {
    echo -e "${BLUE}ℹ️  $1${NC}"
}

# Function to check if Docker is running
check_docker() {
    if ! docker info > /dev/null 2>&1; then
        print_error "Docker is not running. Please start Docker Desktop first."
        exit 1
    fi
}

# Function to start infrastructure services
start_infrastructure() {
    print_info "🚀 Starting Pet Clinic Infrastructure Services..."
    
    check_docker
    
    # Stop any existing containers
    print_info "🛑 Stopping existing containers..."
    docker-compose -f $DOCKER_COMPOSE_SIMPLE down
    
    # Start the simplified services
    print_info "✅ Starting PostgreSQL, MySQL, Redis, and Adminer..."
    docker-compose -f $DOCKER_COMPOSE_SIMPLE up -d
    
    # Wait a moment for services to start
    sleep 5
    
    # Check service status
    print_info "📊 Infrastructure Service Status:"
    docker-compose -f $DOCKER_COMPOSE_SIMPLE ps
    
    print_status "Infrastructure services are ready!"
    print_info "📊 PostgreSQL: localhost:5433 (user: postgres, password: password, db: petclinic_db)"
    print_info "🟡 MySQL: localhost:3307 (user: root, password: Chinadoll1002, db: we_pc)"
    print_info "🔴 Redis: localhost:6379"
    print_info "🌐 Adminer (Database UI): http://localhost:8080"
}

# Function to start a microservice
start_service() {
    local service_name=$1
    local service_path=$2
    local port=$3
    
    print_info "🔄 Starting $service_name..."
    cd "$service_path"
    
    if [ -f "pom.xml" ]; then
        print_info "   Building and starting $service_name on port $port..."
        # Start in background and capture PID
        mvn spring-boot:run > "../$LOGS_DIR/${service_name}.log" 2>&1 &
        echo $! > "../$LOGS_DIR/${service_name}.pid"
        print_status "$service_name started (PID: $!)"
        print_info "   📝 Logs: $LOGS_DIR/${service_name}.log"
    else
        print_error "   No pom.xml found in $service_path"
    fi
    
    cd ..
    echo ""
}

# Function to start all microservices
start_microservices() {
    print_info "🚀 Starting Pet Clinic Microservices..."
    
    # Check if infrastructure is running
    if ! docker-compose -f $DOCKER_COMPOSE_SIMPLE ps | grep -q "Up"; then
        print_error "Infrastructure services are not running. Please start them first:"
        print_info "   ./manage-services.sh infrastructure"
        exit 1
    fi
    
    print_status "Infrastructure services are running (PostgreSQL, MySQL, Redis, Adminer)"
    echo ""
    
    # Create logs directory
    mkdir -p $LOGS_DIR
    
    print_info "📁 Starting services from: $(pwd)"
    echo ""
    
    # Start services in dependency order
    start_service "Auth Server" "authServer" "8761"
    sleep 5  # Wait for Eureka to start
    
    start_service "Clinic Server" "clinicServer" "8081"
    start_service "Donor Server" "donorServer" "8083"
    start_service "Maps Service" "maps-service" "8088"
    
    sleep 3  # Wait for services to start
    
    start_service "API Gateway" "api-gateway" "8085"
    
    echo ""
    print_status "All microservices are starting up!"
    echo ""
    print_info "📊 Service Status:"
    print_info "   Infrastructure: docker-compose -f $DOCKER_COMPOSE_SIMPLE ps"
    print_info "   Microservices: Check $LOGS_DIR/ directory for startup logs"
    echo ""
    print_info "🌐 Access URLs:"
    print_info "   Eureka Dashboard: http://localhost:8761"
    print_info "   API Gateway: http://localhost:8085"
    print_info "   Database Admin: http://localhost:8080"
    echo ""
    print_info "📝 To view logs: tail -f $LOGS_DIR/[service-name].log"
    print_info "🛑 To stop all: ./manage-services.sh stop"
}

# Function to stop a service
stop_service() {
    local service_name=$1
    local pid_file="$LOGS_DIR/${service_name}.pid"
    
    if [ -f "$pid_file" ]; then
        local pid=$(cat "$pid_file")
        if ps -p $pid > /dev/null 2>&1; then
            print_info "🔄 Stopping $service_name (PID: $pid)..."
            kill $pid
            rm "$pid_file"
            print_status "$service_name stopped"
        else
            print_warning "   $service_name not running"
            rm "$pid_file"
        fi
    else
        print_warning "   No PID file found for $service_name"
    fi
}

# Function to stop all microservices
stop_microservices() {
    print_info "🛑 Stopping Pet Clinic Microservices..."
    
    print_info "📁 Stopping services..."
    
    # Stop services in reverse order
    stop_service "API Gateway"
    stop_service "Maps Service"
    stop_service "Donor Server"
    stop_service "Clinic Server"
    stop_service "Auth Server"
    
    echo ""
    print_status "All microservices stopped!"
    print_info "💡 To start again: ./manage-services.sh start"
    echo ""
    print_warning "Note: Infrastructure services (PostgreSQL, Redis) are still running."
    print_info "   To stop them: ./manage-services.sh stop-infrastructure"
}

# Function to stop infrastructure services
stop_infrastructure() {
    print_info "🛑 Stopping Pet Clinic Infrastructure Services..."
    
    # Stop the simplified services
    docker-compose -f $DOCKER_COMPOSE_SIMPLE down
    
    print_status "Infrastructure services stopped successfully!"
    print_info "💡 To start again: ./manage-services.sh infrastructure"
}

# Function to fix MySQL issues
fix_mysql() {
    print_info "🔧 Fixing MySQL configuration issues..."
    
    # Stop and remove the existing MySQL container
    print_info "🛑 Stopping existing MySQL container..."
    docker-compose -f $DOCKER_COMPOSE_SIMPLE stop mysql
    docker-compose -f $DOCKER_COMPOSE_SIMPLE rm -f mysql
    
    # Remove the MySQL data volume to start fresh
    print_info "🗑️  Removing MySQL data volume..."
    docker volume rm backend-clinic_mysql-data 2>/dev/null || print_warning "Volume already removed or doesn't exist"
    
    # Start the MySQL container with new configuration
    print_info "🚀 Starting MySQL with new configuration..."
    docker-compose -f $DOCKER_COMPOSE_SIMPLE up -d mysql
    
    # Wait for MySQL to be ready
    print_info "⏳ Waiting for MySQL to be ready..."
    sleep 30
    
    # Check MySQL status
    print_info "📊 Checking MySQL status..."
    docker-compose -f $DOCKER_COMPOSE_SIMPLE ps mysql
    
    # Test MySQL connection
    print_info "🔍 Testing MySQL connection..."
    docker exec petclinic-mysql mysql -u root -pChinadoll1002 -e "SELECT VERSION();" 2>/dev/null || {
        print_error "MySQL connection failed. Checking logs..."
        docker-compose -f $DOCKER_COMPOSE_SIMPLE logs mysql
        exit 1
    }
    
    print_status "MySQL is now running successfully!"
    echo ""
    print_info "📝 Connection details:"
    print_info "   Host: localhost"
    print_info "   Port: 3307"
    print_info "   Database: we_pc"
    print_info "   Root Password: Chinadoll1002"
    print_info "   User: petclinic_user"
    print_info "   Password: petclinic_pass"
    echo ""
    print_info "🌐 Access via Adminer: http://localhost:8080"
    print_info "   Server: mysql"
    print_info "   Username: petclinic_user"
    print_info "   Password: petclinic_pass"
    print_info "   Database: we_pc"
}

# Function to show service status
show_status() {
    print_info "📊 Pet Clinic Service Status"
    echo ""
    
    print_info "Infrastructure Services:"
    docker-compose -f $DOCKER_COMPOSE_SIMPLE ps
    echo ""
    
    print_info "Microservice Status:"
    if [ -d "$LOGS_DIR" ]; then
        for service in "${SERVICES[@]}"; do
            IFS=':' read -r service_name service_path port <<< "$service"
            pid_file="$LOGS_DIR/${service_name}.pid"
            
            if [ -f "$pid_file" ]; then
                local pid=$(cat "$pid_file")
                if ps -p $pid > /dev/null 2>&1; then
                    print_status "$service_name: Running (PID: $pid, Port: $port)"
                else
                    print_warning "$service_name: Not running (stale PID file)"
                    rm "$pid_file" 2>/dev/null || true
                fi
            else
                print_warning "$service_name: Not running"
            fi
        done
    else
        print_warning "No microservices have been started yet"
    fi
}

# Function to show logs
show_logs() {
    local service_name=$1
    
    if [ -z "$service_name" ]; then
        print_error "Please specify a service name"
        print_info "Available services:"
        for service in "${SERVICES[@]}"; do
            IFS=':' read -r name path port <<< "$service"
            echo "   $name"
        done
        exit 1
    fi
    
    local log_file="$LOGS_DIR/${service_name}.log"
    
    if [ -f "$log_file" ]; then
        print_info "Showing logs for $service_name:"
        tail -f "$log_file"
    else
        print_error "No log file found for $service_name"
        exit 1
    fi
}

# Function to show help
show_help() {
    echo "Pet Clinic Service Management Script"
    echo ""
    echo "Usage: $0 [COMMAND] [OPTIONS]"
    echo ""
    echo "Commands:"
    echo "  infrastructure    Start infrastructure services (PostgreSQL, MySQL, Redis, Adminer)"
    echo "  start            Start all microservices (requires infrastructure to be running)"
    echo "  stop             Stop all microservices"
    echo "  stop-infrastructure Stop infrastructure services"
    echo "  restart          Restart all services"
    echo "  status           Show status of all services"
    echo "  logs [SERVICE]   Show logs for a specific service"
    echo "  fix-mysql        Fix MySQL configuration issues"
    echo "  help             Show this help message"
    echo ""
    echo "Examples:"
    echo "  $0 infrastructure     # Start only infrastructure"
    echo "  $0 start             # Start all microservices"
    echo "  $0 stop              # Stop all microservices"
    echo "  $0 status            # Show service status"
    echo "  $0 logs 'Auth Server' # Show logs for Auth Server"
    echo ""
    echo "Quick Start:"
    echo "  1. $0 infrastructure  # Start infrastructure first"
    echo "  2. $0 start           # Start all microservices"
    echo "  3. $0 status          # Check status"
    echo "  4. $0 stop            # Stop when done"
}

# Main script logic
case "${1:-help}" in
    "infrastructure")
        start_infrastructure
        ;;
    "start")
        start_microservices
        ;;
    "stop")
        stop_microservices
        ;;
    "stop-infrastructure")
        stop_infrastructure
        ;;
    "restart")
        stop_microservices
        sleep 2
        start_microservices
        ;;
    "status")
        show_status
        ;;
    "logs")
        show_logs "$2"
        ;;
    "fix-mysql")
        fix_mysql
        ;;
    "help"|*)
        show_help
        ;;
esac
