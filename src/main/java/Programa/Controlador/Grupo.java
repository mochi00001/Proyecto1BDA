/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Programa.Controlador;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.model.Filters;

public class Grupo {
    
    private MongoDatabase database;

    public Grupo() {
        // Conectar a MongoDB
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        this.database = mongoClient.getDatabase("gastronomia_indigena"); // Cambia a tu base de datos
    }
    
    /**
     * Método para insertar un nuevo grupo indígena
     */
    public void insertarGrupoIndigena(String nombre, String ubicacion, String provincia, String cantidad, 
                                      String lenguas, String clanes, String liderazgo, String practicas) {
        MongoCollection<Document> collection = database.getCollection("Grupos");
        
        // Crear el nuevo documento para el grupo indígena
        Document nuevoGrupo = new Document("name", nombre)
                .append("location", new Document("geographic_area", ubicacion)
                        .append("province", provincia))
                .append("population", new Document("year", 2024)
                        .append("number", Integer.parseInt(cantidad)))
                .append("languages_spoken", List.of(lenguas.split(", ")))
                .append("social_structure", new Document("clans", List.of(clanes.split(", ")))
                        .append("leadership", liderazgo)
                        .append("cultural_practices", practicas));
        
        // Insertar el grupo en la colección
        collection.insertOne(nuevoGrupo);
    }
    
    /**
     * Método para modificar un grupo indígena
     */
    public void modificarGrupo(String nombre, Document nuevosDatos) {
        MongoCollection<Document> collection = database.getCollection("Grupos");
        collection.updateOne(Filters.eq("name", nombre), new Document("$set", nuevosDatos));
    }
    
    /**
     * Método para eliminar un grupo indígena
     */
    public void eliminarGrupo(String nombre) {
        MongoCollection<Document> collection = database.getCollection("Grupos");
        collection.deleteOne(Filters.eq("name", nombre));
    }
    
    /**
     * Método para consultar un grupo indígena
     */
    public Document consultarGrupo(String nombre) {
        MongoCollection<Document> collection = database.getCollection("Grupos");
        return collection.find(Filters.eq("name", nombre)).first();
    }
    
    /**
     * Método para verificar si un grupo indígena existe
     */
    public boolean grupoIndigenaExiste(String nombre) {
        MongoCollection<Document> collection = database.getCollection("Grupos");
        return collection.find(Filters.eq("name", nombre)).first() != null;
    }
    
    /**
     * Método para listar todos los grupos indígenas
     */
    public List<Document> listaGrupos() {
        MongoCollection<Document> collection = database.getCollection("Grupos");
        return collection.find().into(new ArrayList<>());
    }
}

