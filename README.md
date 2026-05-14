# Fast Delivery System

A Java-based delivery management system designed to streamline and optimize delivery operations.

## Project Overview

This project is built with:
- **Java** (75.6%) - Core backend application
- **HTML** (23.4%) - Frontend components
- **Other** (1%)

## Prerequisites

Before you begin, ensure you have the following installed:

### General Requirements
- **Java Development Kit (JDK)** 11 or higher
- **Git** (for cloning the repository)

### Windows-Specific Requirements
- Windows 10 or higher
- Maven 3.6.0 or higher (or use the Maven wrapper included in the project)

### Linux-Specific Requirements
- Ubuntu 18.04 LTS or higher (or equivalent Linux distribution)
- Maven 3.6.0 or higher (or use the Maven wrapper included in the project)

## Installation

### Windows

1. **Clone the repository:**
   ```bash
   git clone https://github.com/kyrczak/fast-delivery-system.git
   cd fast-delivery-system
   ```

2. **Verify Java Installation:**
   ```bash
   java -version
   javac -version
   ```

3. **Install Dependencies:**
   ```bash
   mvn clean install
   ```
   Or if using the Maven wrapper:
   ```bash
   .\mvnw.cmd clean install
   ```

4. **Build the Project:**
   ```bash
   mvn clean package
   ```
   Or with Maven wrapper:
   ```bash
   .\mvnw.cmd clean package
   ```

### Linux

1. **Clone the repository:**
   ```bash
   git clone https://github.com/kyrczak/fast-delivery-system.git
   cd fast-delivery-system
   ```

2. **Verify Java Installation:**
   ```bash
   java -version
   javac -version
   ```
   
   If Java is not installed, install it using:
   ```bash
   # Ubuntu/Debian
   sudo apt-get update
   sudo apt-get install default-jdk
   
   # RHEL/CentOS/Fedora
   sudo yum install java-11-openjdk-devel
   ```

3. **Install Dependencies:**
   ```bash
   mvn clean install
   ```
   Or if using the Maven wrapper:
   ```bash
   chmod +x mvnw
   ./mvnw clean install
   ```

4. **Build the Project:**
   ```bash
   mvn clean package
   ```
   Or with Maven wrapper:
   ```bash
   ./mvnw clean package
   ```

## Running the Application

### Windows

1. **Using Maven:**
   ```bash
   mvn spring-boot:run
   ```
   Or with Maven wrapper:
   ```bash
   .\mvnw.cmd spring-boot:run
   ```

2. **Using the JAR file:**
   ```bash
   java -jar target/fast-delivery-system-*.jar
   ```

### Linux

1. **Using Maven:**
   ```bash
   mvn spring-boot:run
   ```
   Or with Maven wrapper:
   ```bash
   ./mvnw spring-boot:run
   ```

2. **Using the JAR file:**
   ```bash
   java -jar target/fast-delivery-system-*.jar
   ```

3. **Running as a Background Service (Optional):**
   ```bash
   nohup java -jar target/fast-delivery-system-*.jar > delivery-system.log 2>&1 &
   ```

## Configuration

The application can be configured through the `application.properties` or `application.yml` file located in the `src/main/resources/` directory. Modify settings such as:
- Server port
- Database connection
- Logging levels
- Custom application properties

Example configuration:
```properties
server.port=8080
spring.application.name=fast-delivery-system
```

## Project Structure

```
fast-delivery-system/
├── src/
│   ├── main/
│   │   ├── java/          # Java source code
│   │   └── resources/     # Configuration files and templates
│   └── test/              # Test files
├── pom.xml                # Maven configuration
├── README.md              # This file
└── target/                # Build artifacts (generated)
```

## Building and Testing

### Run Unit Tests
```bash
# Windows
mvn test

# Linux
mvn test
```

### Run Integration Tests
```bash
# Windows and Linux
mvn verify
```

## Troubleshooting

### Windows

- **Maven not recognized:** Ensure Maven is added to your system PATH environment variable
- **Java version mismatch:** Check that JAVA_HOME environment variable points to the correct JDK installation
- **Permission denied on mvnw.cmd:** Run Command Prompt as Administrator

### Linux

- **Java not found:** Install JDK using the commands provided in the Installation section
- **mvnw: Permission denied:** Run `chmod +x mvnw` to make the wrapper executable
- **Maven cache issues:** Clear the Maven cache with `rm -rf ~/.m2/repository` and retry

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License - see the LICENSE file for details (if available).

## Support

For issues, questions, or suggestions, please open an issue on the [GitHub repository](https://github.com/kyrczak/fast-delivery-system/issues).

---

**Last Updated:** 2026-05-14
