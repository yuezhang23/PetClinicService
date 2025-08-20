-- MySQL initialization script for Pet Clinic Service
-- This script runs when the MySQL container starts for the first time

-- Create the database if it doesn't exist
CREATE DATABASE IF NOT EXISTS we_pc CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Use the database
USE we_pc;

-- Create a dedicated user for the application with proper permissions
CREATE USER IF NOT EXISTS 'petclinic_user'@'%' IDENTIFIED BY 'petclinic_pass';
GRANT ALL PRIVILEGES ON we_pc.* TO 'petclinic_user'@'%';
GRANT ALL PRIVILEGES ON we_pc.* TO 'petclinic_user'@'localhost';

-- Create additional user for root access from any host
CREATE USER IF NOT EXISTS 'root'@'%' IDENTIFIED BY 'Chinadoll1002';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION;

-- Flush privileges to apply changes
FLUSH PRIVILEGES;

-- Set proper SQL mode for compatibility
SET GLOBAL sql_mode = 'STRICT_TRANS_TABLES,NO_ZERO_DATE,NO_ZERO_IN_DATE,ERROR_FOR_DIVISION_BY_ZERO';

-- Create basic tables structure if they don't exist
-- These are placeholder tables that will be created by your Spring Boot applications

-- Example table structure (adjust based on your actual needs)
CREATE TABLE IF NOT EXISTS user_accounts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Show created databases and users
SHOW DATABASES;
SELECT User, Host FROM mysql.user WHERE User IN ('petclinic_user', 'root');
