/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Programa.Controlador;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import java.util.List;

public class Ingredientes {

    private final String uri = "mongodb+srv://dbLuis:dbLuis12@laboratorio1.zhb0x.mongodb.net/ProyectoBDA1";
    private MongoDatabase database;

    // Constructor
    public Ingredientes() {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            this.database = mongoClient.getDatabase("ProyectoBDA1");
        } catch (Exception e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }

    // Obtener todos los ingredientes
    public List<Document> obtenerIngredientes() {
        MongoCollection<Document> collection = database.getCollection("Ingredientes");
        List<Document> ingredientesList = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                ingredientesList.add(cursor.next());
            }
        } catch (Exception e) {
            System.err.println("Error al obtener lista de ingredientes: " + e.getMessage());
        }
        return ingredientesList;
    }

    // Buscar ingrediente por nombre en español
    public Document buscarIngredientePorNombre(String nombreIngrediente) {
        MongoCollection<Document> collection = database.getCollection("Ingredientes");
        return collection.find(eq("name_spanish", nombreIngrediente)).first();
    }

    // Crear un nuevo ingrediente
    public void crearIngrediente(String nombreEspanol, String nombreIndigena, String ubicacion, String nivelConsumo, boolean existencia) {
        MongoCollection<Document> collection = database.getCollection("Ingredientes");
        Document nuevoIngrediente = new Document("name_spanish", nombreEspanol)
                .append("name_indigenous", nombreIndigena)
                .append("location", ubicacion)
                .append("consumption_level", nivelConsumo)
                .append("is_cultivated", existencia);
        collection.insertOne(nuevoIngrediente);
    }

    // Modificar un ingrediente existente
    public void modificarIngrediente(String nombreEspanol, String nuevoNombreIndigena, String nuevaUbicacion, 
                                     String nuevoNivelConsumo, boolean nuevaExistencia) {
        MongoCollection<Document> collection = database.getCollection("Ingredientes");
        Document nuevosDatos = new Document("name_indigenous", nuevoNombreIndigena)
                .append("location", nuevaUbicacion)
                .append("consumption_level", nuevoNivelConsumo)
                .append("is_cultivated", nuevaExistencia);
        collection.updateOne(eq("name_spanish", nombreEspanol), new Document("$set", nuevosDatos));
    }

    // Eliminar un ingrediente por nombre en español
    public void eliminarIngrediente(String nombreIngrediente) {
        MongoCollection<Document> collection = database.getCollection("Ingredientes");
        collection.deleteOne(eq("name_spanish", nombreIngrediente));
    }

    // Método para verificar si un ingrediente con un nombre específico existe
    public boolean verificarIngrediente(String nombreIngrediente) {
        MongoCollection<Document> collection = database.getCollection("Ingredientes");
        try {
            Document ingredienteEncontrado = collection.find(eq("name_spanish", nombreIngrediente)).first();
            return ingredienteEncontrado != null;
        } catch (Exception e) {
            System.err.println("Error al verificar ingrediente: " + e.getMessage());
            return false;
        }
    }
}

