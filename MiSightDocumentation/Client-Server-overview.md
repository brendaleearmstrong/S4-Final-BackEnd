
# MiSight: Mining Data Management System

## Executive Summary
Misight is an HTTP RESTful API client-server application developed to streamline mining data management, specifically for environmental monitoring and safety reporting. This project consists of a Spring Boot server providing RESTful endpoints and a Java console-based client that interacts with the server via `RestTemplate` to manage data.

## Project Context and Academic Requirements
This project was developed to meet the academic requirements of the Software Development program at Keyin College, where students demonstrate technical and practical skills in creating industry-relevant applications.

## Technical Framework
- **Server**: Spring Boot REST API exposed over HTTP
- **Client Application**: Java-based console application using `RestTemplate` for communication
- **Database**: PostgreSQL relational database
- **Version Control**: GitHub with trunk-based development flow
- **Testing**: JUnit with mock server implementations
- **Project Management**: Agile methodology using Trello

## Core Entities and Relationships
1. **Mines**: Each mine has multiple monitoring stations.
2. **MonitoringStations**: Stations gather environmental data across mining areas.
3. **EnvironmentalData**: Pollutant measurements linked to specific stations.
4. **Pollutants**: Benchmarks pollutant levels and types.

## Business Problem Deep Dive
Mining operations face challenges such as manual data collection, fragmented safety tracking, and limited access for community stakeholders. Misight addresses these issues through digitalization, real-time monitoring, and automated reporting.

## Technical Solution Architecture
### Server Components
- **Controllers**: Handle HTTP requests, mapping data for storage and retrieval.
- **Repositories**: Manage CRUD operations for each data model.
- **Services**: Handle business logic.

### Client Integration
- **HttpClient**: Interacts with the server API using `RestTemplate` to manage environmental and safety data.

## Key RESTful API Endpoints
1. **Environmental Data**: CRUD operations for pollutant records.
2. **Mines**: Manage mines and related minerals.
3. **Safety Data**: CRUD operations for safety incident tracking.

## Menu System
The client’s CLI offers role-specific menus, allowing users to manage data based on their permissions.
```plaintext
Main Menu:
1. System Administrator
2. Mine Administrator
3. Community Stakeholder
4. Exit
```

## Installation & Setup
1. **Clone the repositories**:
   ```bash
   git clone https://github.com/BrendaLeeArmstrong/S4-midterm-server.git
   git clone https://github.com/BrendaLeeArmstrong/S4-midterm-client.git
   ```
2. **Run the Server**:
   ```bash
   cd misight-server
   mvn spring-boot:run
   ```
3. **Run the Client**:
   ```bash
   cd misight-client
   mvn spring-boot:run
   ```
