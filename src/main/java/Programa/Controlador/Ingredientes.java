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
import java.util.Map;
import org.bson.types.ObjectId;

public class Ingredientes {
    
    Comidas c = new Comidas();
    String uri = c.URI;
    private MongoDatabase database;
    ObjectId documentId = new ObjectId("66f0cf78a3b564bde83f9d80"); 

    public Ingredientes() {
    }
    
    // Constructor que recibe la conexión Mongo
    public Ingredientes(Mongo mongoConnection) {
        this.database = mongoConnection.getDatabase();
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

    // Método para eliminar un ingrediente existente
    public void eliminarIngrediente(String nameSpanish) {
        MongoClient mongoClient = null;

        try {
            // Conexión a MongoDB
            mongoClient = MongoClients.create(uri);
            MongoDatabase database = mongoClient.getDatabase("ProyectoBDA1");
            MongoCollection<Document> collection = database.getCollection("Ingredientes");

            // Obtener el documento
            Document documento = collection.find(new Document("_id", documentId)).first();

            if (documento != null) {
                // Obtener la lista de ingredientes
                List<Document> ingredientes = (List<Document>) documento.get("ingredients");

                // Filtrar ingredientes para eliminar el que coincide con name_spanish
                List<Document> ingredientesActualizados = ingredientes.stream()
                        .filter(ingrediente -> !ingrediente.getString("name_spanish").equals(nameSpanish))
                        .toList(); // Usar .collect(Collectors.toList()) si estás en Java < 16

                // Verificar si se realizó alguna eliminación
                if (ingredientes.size() != ingredientesActualizados.size()) {
                    // Actualizar el documento en la base de datos
                    collection.updateOne(new Document("_id", documentId),
                            new Document("$set", new Document("ingredients", ingredientesActualizados)));
                    System.out.println("Ingrediente eliminado correctamente.");
                } else {
                    System.out.println("El ingrediente no existe.");
                }
            } else {
                System.out.println("No se encontró el documento con el ID especificado.");
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar el ingrediente: " + e.getMessage());
        } finally {
            if (mongoClient != null) {
                mongoClient.close();
            }
        }
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
        
    public boolean verificarIngredienteExistente(String nameSpanish) {
        MongoClient mongoClient = null;
        try {
            // Conexión a MongoDB
            mongoClient = MongoClients.create(uri);
            MongoDatabase database = mongoClient.getDatabase("ProyectoBDA1");
            MongoCollection<Document> collection = database.getCollection("Ingredientes");

            // Obtener el documento
            Document documento = collection.find(new Document("_id", documentId)).first();

            if (documento != null) {
                // Obtener la lista de ingredientes
                List<Document> ingredientes = (List<Document>) documento.get("ingredients");

                // Verificar si el ingrediente ya existe
                return ingredientes.stream()
                        .anyMatch(ingrediente -> ingrediente.getString("name_spanish").equals(nameSpanish));
            } else {
                System.out.println("No se encontró el documento con el ID especificado.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error al verificar el ingrediente: " + e.getMessage());
            return false;
        } finally {
            if (mongoClient != null) {
                mongoClient.close();
            }
        }
    }
    
    // Método para verificar si un ingrediente existe y devolver el Document
    public Document buscarIngrediente(String nameSpanish) {
        MongoClient mongoClient = null;
        Document resultado = null; // Variable para almacenar el resultado

        try {
            // Conexión a MongoDB
            mongoClient = MongoClients.create(uri);
            MongoDatabase database = mongoClient.getDatabase("ProyectoBDA1");
            MongoCollection<Document> collection = database.getCollection("Ingredientes");

            // Obtener el documento
            Document documento = collection.find(new Document("_id", documentId)).first();

            if (documento != null) {
                // Obtener la lista de ingredientes
                List<Document> ingredientes = (List<Document>) documento.get("ingredients");

                // Verificar si el ingrediente existe
                for (Document ingrediente : ingredientes) {
                    if (ingrediente.getString("name_spanish").equals(nameSpanish)) {
                        resultado = ingrediente; // Asignar el documento encontrado
                        break; // Salir del bucle si se encuentra el ingrediente
                    }
                }

                if (resultado == null) {
                    System.out.println("El ingrediente no existe.");
                }
            } else {
                System.out.println("No se encontró el documento con el ID especificado.");
            }
        } catch (Exception e) {
            System.out.println("Error al buscar el ingrediente: " + e.getMessage());
        } finally {
            if (mongoClient != null) {
                mongoClient.close();
            }
        }

        return resultado; // Retornar el documento encontrado o null
    }
    
    // Método para agregar un nuevo ingrediente
    public void agregarIngrediente(String nameSpanish, List<Map<String, String>> namesIndigenousLanguages,
                               String productionLocation, boolean existsToday, Map<String, Map<String, String>> consumptionByGroup) {
        MongoClient mongoClient = null;
        try {
            // Conexión a MongoDB
            mongoClient = MongoClients.create(uri);
            MongoDatabase database = mongoClient.getDatabase("ProyectoBDA1");
            MongoCollection<Document> collection = database.getCollection("Ingredientes");
           
           
            // Crear el mapa de lenguas indígenas
            Document indigenousLanguagesDoc = new Document();
            for (Map<String, String> lang : namesIndigenousLanguages) {
                lang.forEach(indigenousLanguagesDoc::append);
            }

            // Crear el nuevo ingrediente
            Document nuevoIngrediente = new Document("name_spanish", nameSpanish)
                    .append("names_indigenous_languages", indigenousLanguagesDoc)
                    .append("production_location", productionLocation)
                    .append("exists_today", existsToday)
                    .append("consumption_by_group", consumptionByGroup);
            
            Document documento = collection.find(new Document("_id", documentId)).first();

            if (documento != null) {
                // Obtener la lista de ingredientes
                List<Document> ingredientes = (List<Document>) documento.get("ingredients");

                // Agregar el nuevo ingrediente a la lista
                ingredientes.add(nuevoIngrediente);

                // Actualizar el documento en la base de datos
                collection.updateOne(new Document("_id", documentId),
                        new Document("$set", new Document("ingredients", ingredientes)));

            } else {
                System.out.println("No se encontró el documento con el ID especificado.");
            }
        } catch (Exception e) {
            System.out.println("Error al agregar el ingrediente: " + e.getMessage());
        } finally {
            if (mongoClient != null) {
                mongoClient.close();
            }
        }
    }
    
    // Método para crear el documento de nombres indígenas
    private Document crearDocumentDeNombres(List<Map<String, String>> namesIndigenousLanguages) {
        Document indigenousLanguagesDoc = new Document();
        for (Map<String, String> lang : namesIndigenousLanguages) {
            lang.forEach(indigenousLanguagesDoc::append);
        }
        return indigenousLanguagesDoc;
    }
    
    public void actualizarIngrediente(String nameSpanish, List<Map<String, String>> namesIndigenousLanguages,
                                       String productionLocation, boolean existsToday, 
                                       Map<String, Map<String, String>> consumptionByGroup) {
        MongoClient mongoClient = null;

        try {
            // Conexión a MongoDB
            mongoClient = MongoClients.create(uri);
            MongoDatabase database = mongoClient.getDatabase("ProyectoBDA1");
            MongoCollection<Document> collection = database.getCollection("Ingredientes");

            // Obtener el documento
            Document documento = collection.find(new Document("_id", documentId)).first();

            if (documento != null) {
                // Obtener la lista de ingredientes
                List<Document> ingredientes = (List<Document>) documento.get("ingredients");

                // Buscar el ingrediente por name_spanish
                for (Document ingrediente : ingredientes) {
                    if (ingrediente.getString("name_spanish").equals(nameSpanish)) {
                        // Actualizar los campos del ingrediente
                        ingrediente.put("names_indigenous_languages", crearDocumentDeNombres(namesIndigenousLanguages));
                        ingrediente.put("production_location", productionLocation);
                        ingrediente.put("exists_today", existsToday);
                        ingrediente.put("consumption_by_group", consumptionByGroup);
                        
                        // Actualizar el documento en la base de datos
                        collection.updateOne(new Document("_id", documentId),
                                new Document("$set", new Document("ingredients", ingredientes)));

                        System.out.println("Ingrediente actualizado correctamente.");
                        return; // Salir del método después de actualizar
                    }
                }

                System.out.println("El ingrediente no existe.");
            } else {
                System.out.println("No se encontró el documento con el ID especificado.");
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar el ingrediente: " + e.getMessage());
        } finally {
            if (mongoClient != null) {
                mongoClient.close();
            }
        }
    }
   
    public Document obtenerIngredientePorId() {
        MongoClient mongoClient = null;
        Document documentoIngrediente = null;  // Variable para almacenar el documento del ingrediente
        try {
            // Conexión a MongoDB
            mongoClient = MongoClients.create(uri);           
            MongoDatabase database = mongoClient.getDatabase("ProyectoBDA1");
            MongoCollection<Document> collection = database.getCollection("Ingredientes");

            // Obtener el documento por su ID
            documentoIngrediente = collection.find(new Document("_id", documentId)).first();

            if (documentoIngrediente == null) {
                System.out.println("No se encontró el documento con el ID especificado.");
            }
        } catch (Exception e) {
            System.out.println("Error al obtener el ingrediente: " + e.getMessage());
        } finally {
            if (mongoClient != null) {
                mongoClient.close();
            }
        }
        return documentoIngrediente;  // Retornar el documento del ingrediente
    }
}
