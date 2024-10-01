/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Programa.Controlador;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.result.UpdateResult;
import java.util.List;
import org.bson.types.ObjectId;

public class Recetas {
    
    Comidas c = new Comidas();
    String uri = c.URI;
    private MongoDatabase database;
    ObjectId objectId = new ObjectId("66f0cf3fa3b564bde83f9d7b");

    // Constructor
    public Recetas() {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            this.database = mongoClient.getDatabase("ProyectoBDA1");
        } catch (Exception e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }

    // Método para leer y retornar la lista de recetas indígenas
    public List<Document> obtenerRecetas() {
        MongoClient mongoClient = null;
        List<Document> recetas = null;

        try {
            // Conexión a MongoDB
            mongoClient = MongoClients.create(uri);
            MongoDatabase database = mongoClient.getDatabase("ProyectoBDA1");
            MongoCollection<Document> collection = database.getCollection("Recetas");

            // Buscar el documento en la base de datos
            Document documento = collection.find(new Document("_id", objectId)).first();

            if (documento != null) {
                // Obtener la lista de recetas indígenas
                recetas = (List<Document>) documento.get("indigenous_recipes");

                // Retornar la lista de recetas
                return recetas;
            } else {
                System.out.println("No se encontró el documento con el ID especificado.");
            }
        } catch (Exception e) {
            System.out.println("Error al obtener las recetas: " + e.getMessage());
        } finally {
            if (mongoClient != null) {
                mongoClient.close();
            }
        }

        return recetas; // Retorna null si no se encontraron recetas o hubo un error
    }    
    
    public Document buscarRecetaPorNombre(String recipeName) {
        MongoClient mongoClient = null;
        Document receta = null;

        try {
            // Conexión a MongoDB
            mongoClient = MongoClients.create(uri);
            MongoDatabase database = mongoClient.getDatabase("ProyectoBDA1");
            MongoCollection<Document> collection = database.getCollection("Recetas");

            // Crear el filtro para buscar por nombre y ObjectId
            Document filter = new Document("recipe_name", recipeName).append("_id", objectId);

            // Buscar la receta usando el filtro
            receta = collection.find(filter).first();

            if (receta != null) {
                System.out.println("Receta encontrada: " + recipeName);
            } else {
                System.out.println("No se encontró la receta con nombre: " + recipeName + " y el ID especificado.");
            }
        } catch (Exception e) {
            System.out.println("Error al buscar la receta: " + e.getMessage());
        } finally {
            if (mongoClient != null) {
                mongoClient.close(); // Asegurarse de cerrar la conexión
            }
        }

        return receta; // Retorna el documento de la receta si se encuentra, o null si no
    }
    
    public boolean verificarRecetaExiste(String recipeNameToCheck) {
        // Conectar a MongoDB
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("ProyectoBDA1");  // Seleccionar la base de datos
        MongoCollection<Document> collection = database.getCollection("Recetas");  // Seleccionar la colección

        // Crear la consulta para buscar una receta por su nombre
        Document query = new Document("indigenous_recipes.recipe_name", recipeNameToCheck);

        // Verificar si la receta existe
        Document recipe = collection.find(query).first();

        // Cerrar la conexión
        mongoClient.close();

        // Retornar verdadero si la receta fue encontrada, falso si no
        return recipe != null;
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
    public void eliminarReceta(String recipeNameToDelete) {
        // Conectar a la base de datos MongoDB
        MongoClient mongoClient = MongoClients.create(uri);

        // Seleccionar la base de datos y la colección
        mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("ProyectoBDA1");
        MongoCollection<Document> collection = database.getCollection("Recetas");
            
        // Crear la consulta para encontrar el documento con la receta a eliminar
        Document query = new Document("indigenous_recipes.recipe_name", recipeNameToDelete);

        // Actualización para eliminar una receta específica dentro del arreglo indigenous_recipes
        Document update = new Document("$pull", new Document("indigenous_recipes", 
            new Document("recipe_name", recipeNameToDelete)));

        // Ejecutar la actualización (eliminar la receta del arreglo)
        UpdateResult result = collection.updateOne(query, update);

        // Verificar si la receta fue eliminada
        if (result.getModifiedCount() > 0) {
            System.out.println("Receta eliminada exitosamente.");
        } else {
            System.out.println("No se encontró la receta o no fue eliminada.");
        }

        // Cerrar la conexión
        mongoClient.close();
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

