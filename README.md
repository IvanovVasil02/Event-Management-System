# Event Management System

This project is a web application that allows users to view a list of events, book seats to attend available events, and provides event management functionality for organizers.

## Features

- **Usere Registration**: Users can register and log in to the system with distinct roles for "Normal User" and "Event Organizer".

- **Event Management for Organizers:**:

  - **Creation of New Events**: Organizers can create new events by specifying title, description, date, location, and number of available seats.
  - **Modification and Deletion of Events**: Organizers can modify and delete existing events.
    
- **Event Viewing and Booking for Users**:

  - **Viewing Event List**: Users can view a list of available events with details such as title, date, and number of available seats.
  - **Seat Booking**: Users can book seats to attend available events.
  - **Booking Management**: Users can view events they have booked seats for and cancel the booking if necessary.
 
## Technologies Used

- **Java**
- **Spring Boot**
- **Spring Security**
- **Spring Data JPA**
- **PostgreSQL**
- **JUnit 5**

## Setup

1. **Clone the Repository**: Clone the repository to a local directory using the following command:

***git clone https://github.com/tuo-username/event-management-system.git***

2. **Configure Environment Variables**:

- Create a file named `env.properties` in the project's main directory.
- Add the following variables to the `env.properties` file and replace the variable values with your credentials:

***CLOUDINARY_NAME=cloudinary_name
CLOUDINARY_KEY=cloudinary_key
CLOUDINARY_SECRET=cloudinary_secret
DB_PASSWORD=db_password
JWT_SECRET=jwt_secret***

3. **Configure the Database**: Set up a PostgreSQL instance and insert the password into the `env.properties` file as DB_PASSWORD.

4. **Run the Backend**: Run the backend application using an IDE or from the command line with Maven and Spring Boot.
   
5. **Test the Endpoints**: Test the application endpoints using Postman or any other HTTP client.

## Endpoints

### Event Endpoints

- `POST /events`: Add a new event. (Access required: Manager)
- `POST /events/{event_id}/bookMe`: Book a seat to attend an event.
- `GET /events`: Get all available events.
- `GET /events/{id}`: Get details of a specific event.
- `PUT /events/{id}`: Edit an existing event. (Access required: Manager)
- `DELETE /events/{id}`: Delete an event. (Access required: Manager)
- `GET /events/myBookings`: Get bookings of the current user.
- `DELETE /events/{event_id}/cancelBooking`: Cancel a booking for an event.
- `GET /events/{event_id}/bookedUsers`: Get users who have booked an event. (Access required: Manager)
  
### User Endpoints
- `GET /users`: Get all users. (Access required: Manager)
- `GET /users/{id}`: Get details of a specific user.
- `GET /users/me`: Get the profile of the current user.
- `PUT /users/{id}`: Edit an existing user. (Access required: Manager)
- `PUT /users/me`: Edit the profile of the current user.
- `DELETE /users/{id}`: Delete a user. (Access required: Manager)
- `DELETE /users/me`: Delete the profile of the current user.
- `DELETE /users/me/{prenotation_id}`: Delete a booking of the current user.

### Authentication Endpoints
- `POST /authentication/register`: Register a new user.
- `POST /authentication/login`: Log in as a registered user.

## Thank You for Visiting!

We appreciate your interest in our project. If you have any questions or suggestions, feel free to reach out. Have a great day!
