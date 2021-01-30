Each individual hotel has a unique id, a name, an address and multiple phone numbers.  It also has multiple features, for example: a pool, conference facilities, a spa, restaurants, etc.

Each hotel has its own set of room types: e.g., single, double, suite, penthouse, etc.  A room type has a size (in sq. meters), a capacity (max # of people), and multiple room features.  Room types are specific to a single hotel – even if 2 hotels have “single” rooms – those “single” rooms are different in each hotel (differ in size, view, furnishings, etc.)
Each individual room in a hotel has a room number, a floor (even if the floor is part of the room number, because people sometimes want or do not want a specific floor) and belongs to a single room type. 
The hotel chain keeps track of guests.  Each guest has a unique guest id, also an identification type and number (e.g. US passport & number or driving license & number), an address and a home phone number and a mobile phone number.  
A guest may hold one or more reservations for specific room type(s) in a specific hotel from a check-in to check-out date.  A guest may be currently occupying one or more specific rooms.  A guest is the person who pays, but their family members or friends may be occupying the room(s) that are assigned to the one guest.  For security reasons, the hotel must know which guest is occupying a room, and who has occupied that room in the past.

Assume the price of a room is not fixed, but instead it depends on the day of the week and the season.  There are numerous seasons that depend on the location of the hotel, for example: winter, winter holiday, beach season, etc.   Each season has a name, and a start and end date.  Seasons might be unique to a hotel, but sometimes a group of hotels that are nearby all share the same seasons.  Guests may belong to certain guest categories (e.g., VIP, government, military, etc.) that give them a discount.  A bill should be able to be generated for a guest on the day that they check out of the hotel.  The guest’s billing history should be saved in the database, so that advertising can be sent to guests based on how much money they have spent at a hotel, and the whole chain.

The hotel has employees who have EmployeeIDs along with other standard employee attributes.  Some of the types of employees are the manager of each hotel, who is the supervisor of all other employees at the same hotel, administrative staff, and cleaning staff.  The cleaning schedule is important – rooms should be cleaned daily while occupied and on the day that a new guest is supposed to arrive to stay in the room.  When a guest arrives to check in, the desk clerk must be able to find out if the guest’s room is empty and has been cleaned, or a similar empty, clean room is available.

Use your own ideas to think of other items or services that a guest can be charged for during their stay and will be added to the bill.  Some examples might be breakfast, spa services, snacks in the room, restaurant or room service, ordering movies, etc.  Handle some of these in the data model as well.

Specific Requirements

In this section, we list specific requirements for each of the proposed projects.  The requirements are written as user stories which Mike Cohn (https://www.mountaingoatsoftware.com/agile/user-stories) describes as "short, simple descriptions of a feature told from the perspective of the person who desires the new capability."  

Note that this list of requirements is not exhaustive or fully detailed.  In the course of the semester, we will develop the complete picture of the required functionality and behavior.  The requirements will change and be updated to simulate real-world contingencies.   

Hotel Chain System Management

In this project, teams will implement a system to manage operations for an international hotel chain.  The system will need to keep track of guests, bookings, occupancy, seasonal changes, exceptional/emergency events and so on.  The system will allow role-based access for the roles of guest, desk clerk, and manager.  

•	As a user, I want to access a home page that provides general information about the hotel chain, provides links to services offered by hotel web application, and give users the ability to log in
•	As a guest, I want to query the system so that I can find available rooms (by date, destination, occupancy) and create a booking
•	As a guest, I want to create a profile so that I can manage my past and upcoming bookings
•	As a desk clerk, I want to cancel, create, and change, bookings so that I can fulfill guest requests
•	As a manager, I want to review the schedules of all hotel employees so that I can make payroll and adjust hours
•	As a manager, I want to create and cancel seasonal rates and issue advisories so that I can ensure that guests and employees have up-to-date information
