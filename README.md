# Hotel Reservation System

## Overview
The Hotel Reservation System is a simple Java console application that manages hotel room reservations using a MySQL backend. It allows users to reserve rooms, view current reservations, query room numbers by reservation, update existing reservations, and delete reservations through an interactive command-line interface.

---

## Features
- **Reserve a Room:** Add new reservations with guest name, room number, and contact information.
- **View Reservations:** Display all current reservations with details including reservation ID, guest name, room number, contact number, and reservation date.
- **Get Room Number:** Lookup the room number using reservation ID and guest name.
- **Update Reservation:** Modify guest name, room number, and contact number for a given reservation ID.
- **Delete Reservation:** Remove a reservation by its ID.
- **Exit System:** Gracefully exit the application with a progress indicator.

---

## Technologies Used
- Java (JDK 8 or higher)
- JDBC (Java Database Connectivity)
- MySQL Database

---

## Setup Instructions

### Prerequisites
- Java Development Kit (JDK)
- MySQL Server installed and running
- MySQL Connector/J JDBC driver (already included via DriverManager in code)
- A MySQL database named `hoteldb` with the following table schema:

```sql
CREATE TABLE reservations (
    reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    guest_name VARCHAR(255) NOT NULL,
    room_number INT NOT NULL,
    contact_number VARCHAR(20) NOT NULL,
    reservation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
