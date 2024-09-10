# Java Swing + PostgreSQL Client Management App

This is a simple Java Swing application that connects to a PostgreSQL database for managing clients. You can register, edit, delete, and list clients through a graphical interface.

## Prerequisites

Before running the application, ensure you have the following:

- **Java Development Kit (JDK)**: JDK 8 or higher
- **PostgreSQL**: Install and configure PostgreSQL, ensuring a database and user are set up.
- **PostgreSQL JDBC Driver**: Download the PostgreSQL JDBC driver (`postgresql-42.2.5.jar`) from the [PostgreSQL website](https://jdbc.postgresql.org/download.html).
- **Java IDE (Optional)**: IntelliJ IDEA, Eclipse, or any Java IDE.
- **Git**: To clone the repository.

---

## 1. Clone the Repository

First, clone the repository from GitHub to your local machine. Run the following command in your terminal:

```bash
git clone https://github.com/yourusername/your-repo-name.git
cd your-repo-name
```
Replace yourusername and your-repo-name with your actual GitHub username and repository name.

## 2. Set Up PostgreSQL Database
Before you run the application, make sure you have PostgreSQL running and a database created. Follow these steps:

### Create a Database: Open the PostgreSQL terminal (psql) and create a database.

```sql
CREATE DATABASE myappdb;
```
### Create a Table: Create the table that will store the client data. You can do this by running the following SQL script:


```sql
CREATE TABLE Client(
    id SERIAL PRIMARY KEY,
    document INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    lastName VARCHAR(100) NOT NULL,
    gener VARCHAR(10),
    number INT,
    email VARCHAR(100),
    address VARCHAR(200)
);
```
### Create a User: Create a PostgreSQL user and grant them permissions to the database.


```sql
CREATE USER myappuser WITH PASSWORD 'mypassword';
GRANT ALL PRIVILEGES ON DATABASE myappdb TO myappuser;
```

## 3. Configure the Application
### Update the database connection details in the Java application. Open the RegistroCliente.java file and modify the conectar() method to match your PostgreSQL configuration:


```code
private static Connection conectar() {
    Connection conexion = null;
    try {
        String url = "jdbc:postgresql://localhost:5432/myappdb";
        String usuario = "myappuser";
        String password = "mypassword";
        conexion = DriverManager.getConnection(url, usuario, password);
        System.out.println("Conexión exitosa");
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return conexion;
}
```
Make sure to replace **myappdb**, **myappuser**, and **mypassword** with your actual database name, username, and password.
