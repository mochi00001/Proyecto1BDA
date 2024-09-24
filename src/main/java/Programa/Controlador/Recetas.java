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

public class Recetas {

    private final String uri = "mongodb+srv://dbLuis:dbLuis12@laboratorio1.zhb0x.mongodb.net/ProyectoBDA1";
    private MongoDatabase database;

    // Constructor
    public Recetas() {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            this.database = mongoClient.getDatabase("ProyectoBDA1");
        } catch (Exception e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }

    // Obtener todas las recetas
    public List<Document> obtenerRecetas() {
        MongoCollection<Document> collection = database.getCollection("Recetas");
        List<Document> recetasList = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                recetasList.add(cursor.next());
            }
        } catch (Exception e) {
            System.err.println("Error al obtener lista de recetas: " + e.getMessage());
        }
        return recetasList;
    }

    // Buscar receta por nombre
    public Document buscarRecetaPorNombre(String nombreReceta) {
        MongoCollection<Document> collection = database.getCollection("Recetas");
        return collection.find(eq("recipe_name", nombreReceta)).first();
    }

    // Crear nueva receta
    public void crearReceta(String nombre, List<Document> ingredientes, List<String> pasos, String tiempoPreparacion, 
                            String ocasion, String quienPrepara, boolean usadaFestividades) {
        MongoCollection<Document> collection = database.getCollection("Recetas");
        Document nuevaReceta = new Document("recipe_name", nombre)
                .append("ingredients", ingredientes)
                .append("preparation_steps", pasos)
                .append("preparation_time", tiempoPreparacion)
                .append("occasion", ocasion)
                .append("who_prepares", quienPrepara)
                .append("used_for_festivities", usadaFestividades);
        collection.insertOne(nuevaReceta);
    }

    // Modificar una receta existente
    public void modificarReceta(String nombre, List<Document> nuevosIngredientes, List<String> nuevosPasos, 
                                String nuevoTiempoPreparacion, String nuevaOcasion, String nuevoQuienPrepara, boolean nuevaFestividad) {
        MongoCollection<Document> collection = database.getCollection("Recetas");
        Document nuevosDatos = new Document("ingredients", nuevosIngredientes)
                .append("preparation_steps", nuevosPasos)
                .append("preparation_time", nuevoTiempoPreparacion)
                .append("occasion", nuevaOcasion)
                .append("who_prepares", nuevoQuienPrepara)
                .append("used_for_festivities", nuevaFestividad);
        collection.updateOne(eq("recipe_name", nombre), new Document("$set", nuevosDatos));
    }

    // Eliminar una receta por nombre
    public void eliminarReceta(String nombreReceta) {
        MongoCollection<Document> collection = database.getCollection("Recetas");
        collection.deleteOne(eq("recipe_name", nombreReceta));
    }
    
    // Método para verificar si una receta con un nombre específico existe
    public boolean verificarReceta(String nombreReceta) {
        // Obtener la colección de recetas
        MongoCollection<Document> collection = database.getCollection("Recetas");

        try {
            // Buscar la receta por nombre
            Document recetaEncontrada = collection.find(eq("indigenous_recipes.recipe_name", nombreReceta)).first();

            // Retornar true si la receta fue encontrada
            return recetaEncontrada != null;
        } catch (Exception e) {
            System.err.println("Error al verificar receta: " + e.getMessage());
            return false;
        }
    }
}

