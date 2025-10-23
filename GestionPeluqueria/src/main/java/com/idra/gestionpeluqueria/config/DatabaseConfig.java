package com.idra.gestionpeluqueria.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig {
    private static final String URL = "jdbc:mysql://localhost:3306/peluqueria_db";
    private static final String USER = "root";
    private static final String PASSWORD = "9406";
    
    private static DatabaseConfig instance;
    private Connection connection;
    
    // Constructor privado para Singleton
    private DatabaseConfig() {
        try {
            // Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Propiedades adicionales para la conexión
            Properties properties = new Properties();
            properties.setProperty("user", USER);
            properties.setProperty("password", PASSWORD);
            properties.setProperty("useSSL", "false");
            properties.setProperty("serverTimezone", "UTC");
            properties.setProperty("allowPublicKeyRetrieval", "true");
            
            // Crear la conexión
            this.connection = DriverManager.getConnection(URL, properties);
            System.out.println("Conexión a la base de datos establecida correctamente");
            
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el driver de MySQL");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Método estático para obtener la instancia (Singleton)
    public static DatabaseConfig getInstance() {
        if (instance == null) {
            synchronized (DatabaseConfig.class) {
                if (instance == null) {
                    instance = new DatabaseConfig();
                }
            }
        }
        return instance;
    }
    
    // Obtener la conexión
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Reconectar si la conexión está cerrada
                Properties properties = new Properties();
                properties.setProperty("user", USER);
                properties.setProperty("password", PASSWORD);
                properties.setProperty("useSSL", "false");
                properties.setProperty("serverTimezone", "UTC");
                properties.setProperty("allowPublicKeyRetrieval", "true");
                
                connection = DriverManager.getConnection(URL, properties);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener conexión: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }
    
    // Cerrar la conexión
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexión a la base de datos cerrada");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Método para probar la conexión
    public static boolean testConnection() {
        try (Connection conn = getInstance().getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Error en test de conexión: " + e.getMessage());
            return false;
        }
    }
}