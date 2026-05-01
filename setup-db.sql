-- Run as PostgreSQL superuser:
-- sudo -u postgres psql -f setup-db.sql

CREATE DATABASE dts_assignment;
CREATE USER dts_user WITH PASSWORD 'dts_password';
GRANT ALL PRIVILEGES ON DATABASE dts_assignment TO dts_user;

\c dts_assignment
GRANT ALL ON SCHEMA public TO dts_user;
