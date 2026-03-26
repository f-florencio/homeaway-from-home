# HomeAway From Home

Command-line application in Java for managing student services and locations, developed for the **Data Structures and Algorithms** course at NOVA FCT (2025).

---

## About

This application simulates a student-oriented service management system for a campus town. It allows registering eating, lodging and leisure services within a defined geographic area, managing students of different types and their movements, and querying services by location, rating and relevance.

All data structures (lists, queues, etc.) were implemented from scratch — use of `java.util` was not permitted.

---

## Features

- Define a geographic bounding box for a campus area
- Register services of three types: **eating**, **lodging** and **leisure**
- Register students of three types with different behaviours:
  - **Bookish** — tracks visited leisure services
  - **Outgoing** — tracks all visited services
  - **Thrifty** — always seeks the cheapest option
- Move students between services and lodgings
- Evaluate services with a star rating (1–5) and a text description
- Search for services by proximity using **Manhattan Distance**
- Search services by tag in reviews
- List services ranked by average rating
- Save and load geographic areas from files

---

## Commands

| Command | Description |
|--------|-------------|
| `bounds` | Defines the geographic bounding rectangle |
| `save` | Saves the current area to a file |
| `load` | Loads a saved area from a file |
| `service` | Adds a new service (eating, lodging or leisure) |
| `services` | Lists all registered services |
| `student` | Adds a new student |
| `students` | Lists students (all or by country) |
| `leave` | Removes a student from the system |
| `go` | Moves a student to an eating or leisure service |
| `move` | Changes a student's home lodging |
| `users` | Lists students at a given service |
| `where` | Shows the current location of a student |
| `visited` | Lists locations visited by a student |
| `star` | Evaluates a service |
| `ranking` | Lists all services by star rating |
| `ranked` | Finds closest services with a given rating |
| `tag` | Lists services with a specific word in their reviews |
| `find` | Finds the most relevant service for a student |
| `help` | Shows available commands |
| `exit` | Terminates the program |

---

## How to Run

**Compile:**
```bash
javac *.java
```

**Run:**
```bash
java Main
```

> Make sure the `data/` folder exists in the same directory as the executable, for saving and loading areas.

---

## Technologies

- Java
- Custom data structures (no `java.util`)
- Git

---

## Notes

The original project was developed under strict restrictions on the use of `java.util` and substring methods. This version has been modified to include parts of `java.util` where it made practical sense, as the restrictions were specific to the academic context and not to the logic of the application itself.

---

## Course

