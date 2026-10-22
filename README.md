# CRM Starter (Spring Boot + MySQL + React)

This repository now includes a ready-to-run starter project for a CRM application built with:

- Java 17 + Spring Boot 3
- MySQL
- Spring Security + JWT
- React + Vite + TypeScript

The starter is placed in the `crm-starter/` folder so it does not disturb the existing project structure.

## Structure

- `crm-starter/backend` — Spring Boot API
- `crm-starter/frontend` — React dashboard UI

## Backend run steps

```bash
cd crm-starter/backend
mvn clean install
mvn spring-boot:run
```

## Frontend run steps

```bash
cd crm-starter/frontend
npm install
npm run dev
```

## Default login

- Username: `admin`
- Password: `admin123`

The backend creates this default admin user at startup automatically if it does not already exist.

## MySQL setup

Create the database and user:

```sql
CREATE DATABASE crm_db;
CREATE USER 'crmuser'@'%' IDENTIFIED BY 'crmpass';
GRANT ALL PRIVILEGES ON crm_db.* TO 'crmuser'@'%';
FLUSH PRIVILEGES;
```

Then update credentials in `crm-starter/backend/src/main/resources/application.yml` if needed.

## Notes

This starter is intentionally simple, modular, and easy to extend. It gives you the foundation for:

- customer management
- leads and opportunities
- dashboard KPIs
- JWT-based auth
- React UI
