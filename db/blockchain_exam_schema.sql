
-- Create database (optional, based on your setup)
CREATE DATABASE IF NOT EXISTS exam_blockchain;
USE exam_blockchain;

-- Users table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role ENUM('admin', 'student') NOT NULL
);

-- Insert sample users
INSERT INTO users (username, password, role) VALUES
('admin1', 'adminpass', 'admin'),
('student1', 'studentpass', 'student');

-- Blocks table
CREATE TABLE IF NOT EXISTS blocks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_name VARCHAR(100),
    subject VARCHAR(100),
    marks INT,
    previous_hash VARCHAR(255),
    hash VARCHAR(255)
);
