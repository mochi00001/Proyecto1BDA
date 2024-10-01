/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Programa.Controlador;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.ConnectionString;

public class Mongo {
    Comidas c = new Comidas();
    String URI = c.URI;
    private MongoClient mongoClient;
    private MongoDatabase database;

    // Método para iniciar la conexión
    public Mongo() {
        try {
            MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(URI))
                .applyToSslSettings(builder -> builder.enabled(true)) // Configuración SSL si es necesario
                .build();
            
            mongoClient = MongoClients.create(settings);
            database = mongoClient.getDatabase("ProyectoBDA1");
            System.out.println("Conexión exitosa a la base de datos");
        } catch (Exception e) {
            System.err.println("Error al conectar a MongoDB: " + e.getMessage());
        }
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    // Método para cerrar la conexión si es necesario
    public void cerrarConexion() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("Conexión a MongoDB cerrada.");
        }
    }
}



