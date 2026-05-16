# Fast Delivery System

A Java-based enterprise logistics and delivery management system designed to streamline and optimize delivery operations, track parcels, and manage warehouses.

## Project Overview

This project is built utilizing a modern Enterprise Java stack:
* **Java 21 LTS** - Core backend application language
* **Jakarta EE 10** - Enterprise specifications (CDI, EJB, JAX-RS, JPA)
* **Jakarta Faces (JSF) 4.0** - Server-side rendering and UI views
* **Open Liberty** - Micro-container and application server
* **H2 Database** - In-memory relational database
* **Material Design for Bootstrap (MDB)** - Frontend styling

## Prerequisites

Before you begin, ensure you have the following installed on your system (Windows, Linux, or macOS):

* **Java Development Kit (JDK) 21 LTS** (Strict requirement for Lombok and compiler compatibility)
* **Apache Maven 3.8.x** or higher
* **Git** (for cloning the repository)

## Installation & Setup

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/kyrczak/fast-delivery-system.git](https://github.com/kyrczak/fast-delivery-system.git)
   cd fast-delivery-system
   ```

2. **Verify Java Installation:**
   Ensure your active environment is using Java 21:
   ```bash
   java -version
   javac -version
   ```

3. **Build the Project:**
   Compile the source code, generate JPA metamodels, and process Lombok annotations:
   ```bash
   mvn clean compile
   ```

## Running the Application

Unlike standalone Spring Boot `.jar` applications, this project is deployed as a `.war` archive into an Open Liberty container.

**Start the server in Development Mode (Hot-Reloading):**
```bash
mvn liberty:dev
```

Once the console outputs `CWWKF0011I: The defaultServer server is ready to run a smarter planet`, the application is live.

**Access Points:**
* **Web Interface (JSF):** http://localhost:9080/fast-delivery/
* **REST API Base:** http://localhost:9080/fast-delivery/api/
* **Remote Debugging:** Port `7777`

*To stop the server, type `q` and press Enter in the terminal running Liberty, or press `Ctrl+C`.*

## Configuration

Application configuration is handled via standard Jakarta EE and Open Liberty XML files, rather than `.properties` or `.yml` files:

1. **Server Configuration (`src/main/liberty/config/server.xml`):**
   * Manages server features (servlet, faces, cdi, restfulWS).
   * Defines the `basicRegistry` for user authentication and roles (e.g., `admin-service`, `testuser`).
   * Configures the H2 Database connection pool.
   * Maps the external cross-platform file server for image uploads (`/uploads`).

2. **Database Persistence (`src/main/resources/META-INF/persistence.xml`):**
   * Manages Hibernate ORM settings, entity scanning, and database generation strategies.

## Project Structure

```text
fast-delivery-system/
├── src/
│   ├── main/
│   │   ├── java/pl/pg/kyrczak/jakarta/ # Java source code (Controllers, EJBs, Entities)
│   │   ├── liberty/config/             # Open Liberty server configuration (server.xml)
│   │   ├── resources/                  # I18n bundles and persistence.xml
│   │   └── webapp/                     # JSF Facelets (.xhtml), CSS, JS, and web.xml
│   └── test/                           # Test suites
├── pom.xml                             # Maven configuration and dependencies
└── README.md                           # This file
```

## Cross-Platform File Storage

The system handles binary file uploads (e.g., parcel images) using an OS-agnostic approach. Files are not saved directly into the application's source code. Instead, they are routed to the host machine's user home directory:
* **Windows:** `C:\Users\<Username>\fast-delivery-system\uploads\images\`
* **Linux/macOS:** `/home/<Username>/fast-delivery-system/uploads/images/`

Open Liberty safely exposes this directory to the web layer via the `/uploads/` URL path.

## Troubleshooting

* **`TypeTag :: UNKNOWN` Compilation Error:** This occurs if you are compiling the project with a JDK version newer than 21 (e.g., JDK 26) which conflicts with Lombok. Downgrade your `JAVA_HOME` to JDK 21.
* **Port 9080 is already in use:** Another application (or a zombie Liberty process) is using the port. Kill the process or change the `httpPort` in `server.xml`.
* **`SESN0008E` Unauthorized Session Error:** Clear your browser cookies for `localhost`. This happens when transitioning from an anonymous session to an authenticated session during development.
* **Missing Images:** Ensure the directory mapped in `server.xml` matches your actual OS user directory path.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License - see the LICENSE file for details (if available).
