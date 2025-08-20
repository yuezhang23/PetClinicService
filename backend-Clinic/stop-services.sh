#!/bin/bash

echo "🛑 Stopping Enhanced Pet Clinic Microservices System"
echo "==================================================="

# Stop all Java processes related to our services
echo "🔄 Stopping microservices..."
pkill -f "spring-boot:run"
pkill -f "WeePetClinic"

# Stop infrastructure services
echo "📦 Stopping infrastructure services..."
docker-compose down

echo ""
echo "✅ All services stopped!"
echo ""
echo "🧹 To clean up completely, run: docker-compose down -v"
echo "🚀 To start again, run: ./start-services.sh"
