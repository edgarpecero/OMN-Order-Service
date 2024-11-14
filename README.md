# Order Service Backend

Application URL: http://www.edgar-omn.com.s3-website-us-west-1.amazonaws.com/

React Frontend Repository: https://github.com/edgarpecero/OMN-Order-Service-React-App

Notification Service Repository: https://github.com/edgarpecero/OMN-Notification-Service

** Order Service Backend Backend** is the server-side implementation of the Order Management and Notification platform, built using a microservices architecture. This project is designed to manage customer orders and send notifications based on order statuses, with an emphasis on scalability, resilience, and high availability.

The backend comprises two core services: **Order Service** and **Notification Service**, both of which communicate via message queues and are deployed in containers using Docker and Kubernetes. This solution adheres to Site Reliability Engineering (SRE) best practices, ensuring the platform is robust and performant.

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Project Structure](#project-structure)
- [Installation](#installation)
- [Usage](#usage)
- [Tech Stack](#tech-stack)
- [CI/CD Pipeline](#cicd-pipeline)
- [Contributing](#contributing)
- [License](#license)

---

## Overview

The **OrderWave Backend** includes:
- **Order Service**: Manages the lifecycle of customer orders, including creation, updates, and status changes. The service generates events that are pushed to a message queue to notify the **Notification Service** of changes.
- **Notification Service**: Listens to the event stream from the **Order Service** and sends notifications (via email or SMS) based on the order’s current status.

Both services follow **SOLID principles** and are fully tested with unit tests, ensuring reliability and maintainability. The backend is deployed in a **Kubernetes** cluster and is part of an automated **CI/CD pipeline** for continuous integration and deployment.

## Features

- **Order Lifecycle Management**: Create, update, and query orders with statuses such as "Processing," "Shipped," "Delivered," and "Cancelled."
- **Event-Driven Notifications**: Send notifications to customers when their orders reach different statuses.
- **Unit Testing**: Ensure critical functionalities (order management and notification emission) are thoroughly tested.
- **Resilience**: Implement **Circuit Breaker** and **Retry** patterns to handle service failures gracefully.
- **Scalability**: Both services are containerized and can be scaled horizontally using Kubernetes.
- **Logging and Monitoring**: Centralized logging (ELK stack) and Prometheus/Grafana for monitoring system health and performance.

## Project Structure

```plaintext
src/
├── order-service/        # Order Service: manages order lifecycle and event generation
├── notification-service/ # Notification Service: listens to order events and sends notifications
├── common/               # Common code used by both services (e.g., message queue handler)
├── tests/                # Unit and integration tests for each service
└── config/               # Configuration files and environment setup
