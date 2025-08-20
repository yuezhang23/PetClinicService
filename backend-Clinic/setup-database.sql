-- Pet Clinic Database Setup for Simplified Infrastructure
-- This script creates the necessary schemas and tables for all services

-- Connect to the petclinic_db database
\c petclinic_db;

-- Create schemas for different services
CREATE SCHEMA IF NOT EXISTS auth;
CREATE SCHEMA IF NOT EXISTS clinic;
CREATE SCHEMA IF NOT EXISTS donor;
CREATE SCHEMA IF NOT EXISTS account;
CREATE SCHEMA IF NOT EXISTS maps;
CREATE SCHEMA IF NOT EXISTS subscription;
CREATE SCHEMA IF NOT EXISTS activities;

-- Set search path to include all schemas
SET search_path TO public, auth, clinic, donor, account, maps, subscription, activities;

-- Auth Service Schema
CREATE TABLE IF NOT EXISTS auth.users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Clinic Service Schema
CREATE TABLE IF NOT EXISTS clinic.pets (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    species VARCHAR(50),
    breed VARCHAR(100),
    birth_date DATE,
    owner_id INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS clinic.appointments (
    id SERIAL PRIMARY KEY,
    pet_id INTEGER REFERENCES clinic.pets(id),
    appointment_date TIMESTAMP NOT NULL,
    description TEXT,
    status VARCHAR(20) DEFAULT 'SCHEDULED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Donor Service Schema
CREATE TABLE IF NOT EXISTS donor.donations (
    id SERIAL PRIMARY KEY,
    donor_name VARCHAR(100) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    donation_type VARCHAR(50),
    message TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Account Service Schema
CREATE TABLE IF NOT EXISTS account.user_profiles (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES auth.users(id),
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    phone VARCHAR(20),
    address TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Maps Service Schema
CREATE TABLE IF NOT EXISTS maps.clinic_locations (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address TEXT NOT NULL,
    latitude DECIMAL(10,8),
    longitude DECIMAL(11,8),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Subscription Service Schema
CREATE TABLE IF NOT EXISTS subscription.subscriptions (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES auth.users(id),
    plan_name VARCHAR(50) NOT NULL,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    start_date DATE NOT NULL,
    end_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Pet Activities Service Schema
CREATE TABLE IF NOT EXISTS activities.pet_activities (
    id SERIAL PRIMARY KEY,
    pet_id INTEGER REFERENCES clinic.pets(id),
    activity_type VARCHAR(50) NOT NULL,
    description TEXT,
    activity_date TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_auth_users_username ON auth.users(username);
CREATE INDEX IF NOT EXISTS idx_auth_users_email ON auth.users(email);
CREATE INDEX IF NOT EXISTS idx_clinic_pets_owner ON clinic.pets(owner_id);
CREATE INDEX IF NOT EXISTS idx_clinic_appointments_pet ON clinic.appointments(pet_id);
CREATE INDEX IF NOT EXISTS idx_clinic_appointments_date ON clinic.appointments(appointment_date);
CREATE INDEX IF NOT EXISTS idx_donor_donations_date ON donor.donations(created_at);
CREATE INDEX IF NOT EXISTS idx_maps_locations_coords ON maps.clinic_locations(latitude, longitude);

-- Insert some sample data
INSERT INTO auth.users (username, email, password_hash, role) VALUES
('admin', 'admin@petclinic.com', '$2a$10$dummy.hash.for.demo', 'ADMIN'),
('user1', 'user1@example.com', '$2a$10$dummy.hash.for.demo', 'USER')
ON CONFLICT (username) DO NOTHING;

INSERT INTO clinic.pets (name, species, breed, owner_id) VALUES
('Buddy', 'Dog', 'Golden Retriever', 1),
('Whiskers', 'Cat', 'Persian', 2)
ON CONFLICT DO NOTHING;

INSERT INTO maps.clinic_locations (name, address, latitude, longitude) VALUES
('Main Pet Clinic', '123 Main St, City, State', 40.7128, -74.0060),
('Downtown Branch', '456 Downtown Ave, City, State', 40.7589, -73.9851)
ON CONFLICT DO NOTHING;

-- Grant permissions (adjust as needed for your setup)
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO postgres;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA auth TO postgres;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA clinic TO postgres;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA donor TO postgres;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA account TO postgres;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA maps TO postgres;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA subscription TO postgres;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA activities TO postgres;

-- Show created schemas and tables
\dt *.*
