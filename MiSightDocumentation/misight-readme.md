# MiSight - Mine Data Management System

## Overview Summary
MiSight is an enterprise-grade mine data management system designed to streamline the tracking of minerals, mine projects, environmental metrics, and safety standards. The system provides comprehensive tools for monitoring air quality, safety metrics, and operational data through a Spring Boot REST API backend and a Java console client interface.

Drawing from my experience as Principal Advisor of Communications at a major iron ore mining operation in Labrador, where I was responsible for collaborating with environmental, safety, and operations teams to compile internal and external reports, I witnessed firsthand the daily struggles of managing critical operational data. The countless hours spent manually collecting environmental data, coordinating safety reports, and responding to community concerns about dust levels highlighted a critical industry need.

Through my transition to software development at Keyin College, I recognized the opportunity to transform these challenges into a digital solution. MiSight was born from this unique perspective, designed to transform fragmented data collection into a streamlined, efficient system that serves all stakeholders.

## Business Problem Analysis
### Key Challenges
1. **Data Collection Inefficiencies**: Manual data collection leads to delays and inconsistencies.
2. **Environmental Monitoring Gaps**: Lack of real-time data on pollutants and dust levels.
3. **Safety Management Issues**: Fragmented incident reporting.
4. **Stakeholder Communication Barriers**: Limited access to environmental data.

### Mining operations currently rely on:
- Paper-based environmental monitoring logs
- Spreadsheet-based safety incident tracking
- Manual data compilation for reports
- Email-based stakeholder communications
- Disconnected data storage systems

## Proposed Solution
Misight provides digitalized data collection, role-based access control, and automated reporting. This system centralizes data management, helping mining operations streamline environmental monitoring and safety tracking.

## Core Features & Benefits
1. **Environmental Monitoring**:
    - Real-time dust tracking and threshold alerts.
2. **Safety Management**:
    - Standardized incident reporting and trend analysis.
3. **Stakeholder Engagement**:
    - Public dashboards for transparency.

## Impact Analysis
### Before Misight
- Manual data collection, delayed reporting, limited transparency.

### After Misight
- Automated data collection, real-time reporting, and improved stakeholder access.

## Project Overview
The system consists of two main components:
- **Server**: A RESTful API built with Spring Boot, handling data management and business logic
- **Client**: A Java console application providing user interface and API interaction

### Key Features
- Mine and mineral tracking
- Environmental monitoring and compliance
- Safety incident reporting and tracking
- Role-based access control
- Real-time data visualization
- Comprehensive reporting system

## Tech Stack

### Backend (Server)
- Java 17
- Spring Boot 3.2.1
- Spring Data JPA
- Spring Security
- PostgreSQL Database
- Maven

### Frontend (Client)
- Java Console Application
- Spring RestTemplate
- JSON for data exchange

## Database Schema

### Core Entities and Relationships

#### Provinces
- One-to-Many with Mines
- One-to-Many with MonitoringStations

#### Mines
- Many-to-One with Provinces
- Many-to-Many with Minerals (via mine_minerals)
- One-to-Many with SafetyData
- One-to-Many with EnvironmentalData

#### Minerals
- Many-to-Many with Mines (via mine_minerals)

#### MonitoringStations
- Many-to-One with Provinces
- Many-to-Many with Pollutants (via station_pollutants)
- One-to-Many with EnvironmentalData

#### Pollutants
- Many-to-Many with MonitoringStations (via station_pollutants)
- One-to-Many with EnvironmentalData

#### EnvironmentalData
- Many-to-One with Mines
- Many-to-One with MonitoringStations
- Many-to-One with Pollutants

#### SafetyData
- Many-to-One with Mines

#### Users
- Many-to-Many with Privileges (via user_privileges)

#### Privileges
- Many-to-Many with Users (via user_privileges)

### Table Relationships
```sql
mines -> provinces (many-to-one)
monitoring_stations -> provinces (many-to-one)
mine_minerals -> mines, minerals (many-to-many junction)
station_pollutants -> monitoring_stations, pollutants (many-to-many junction)
environmental_data -> mines, monitoring_stations, pollutants (many-to-one)
safety_data -> mines (many-to-one)
user_privileges -> users, privileges (many-to-many junction)
```

## Example API Endpoints (Not all API endpoints are listed)

### Users
```
GET    /api/users
POST   /api/users
PUT    /api/users/{id}
DELETE /api/users/{id}
```

### Mines
```
GET    /api/mines
POST   /api/mines
PUT    /api/mines/{id}
DELETE /api/mines/{id}
GET    /api/mines/{id}/environmental-data
GET    /api/mines/{id}/safety-data
GET    /api/mines/withMinerals
GET    /api/mines/mineral/{mineralId}
```

### Minerals
```
GET    /api/minerals
POST   /api/minerals
PUT    /api/minerals/{id}
DELETE /api/minerals/{id}
GET    /api/minerals/mine/{mineId}
GET    /api/minerals/search?name={name}
```

### Environmental Data
```
GET    /api/environmental-data
POST   /api/environmental-data
PUT    /api/environmental-data/{id}
DELETE /api/environmental-data/{id}
GET    /api/environmental-data/mine/{mineId}
GET    /api/environmental-data/station/{stationId}
GET    /api/environmental-data/date-range
GET    /api/environmenta
