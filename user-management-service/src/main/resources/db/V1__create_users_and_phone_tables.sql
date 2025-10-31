-- =========================================================
--  Flyway Migration: V1__create_user_phone_address.sql
--  Description: Creates tables for users, phone_numbers, and addresses
--  Includes auditing, soft delete, and optimistic locking
-- =========================================================

-- ===========================
-- USERS TABLE
-- ===========================
CREATE TABLE users (
    id UUID PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',

    -- Auditing & Versioning fields
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    version BIGINT DEFAULT 0,

    is_deleted BOOLEAN DEFAULT FALSE NOT NULL
);

-- Add index for faster lookups on email
CREATE INDEX idx_user_email ON users (email);


-- ===========================
-- PHONE NUMBERS TABLE
-- ===========================
CREATE TABLE phone_numbers (
    id UUID PRIMARY KEY,
    number VARCHAR(20) NOT NULL,
    country_code VARCHAR(10),

    -- Auditing & Versioning fields
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    version BIGINT DEFAULT 0,

    is_deleted BOOLEAN DEFAULT FALSE NOT NULL,

    -- FK to users
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE
);

-- Add index to speed up user lookup
CREATE INDEX idx_phone_user_id ON phone_numbers (user_id);


-- ===========================
-- ADDRESSES TABLE
-- ===========================
CREATE TABLE addresses (
    id UUID PRIMARY KEY,
    street VARCHAR(255) NOT NULL,
    city VARCHAR(100),
    state VARCHAR(100),
    country VARCHAR(100),
    postal_code VARCHAR(20),

    -- Auditing & Versioning fields
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    version BIGINT DEFAULT 0,

    is_deleted BOOLEAN DEFAULT FALSE NOT NULL,

    -- FK to users
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE
);

-- Add index to speed up user lookup
CREATE INDEX idx_address_user_id ON addresses (user_id);
