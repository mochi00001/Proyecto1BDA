/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Programa.Controlador;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import com.mongodb.client.model.Updates;
import java.util.Arrays;
import java.util.List;
import org.bson.types.ObjectId;

public class Grupo {
    
    Comidas c = new Comidas();
    String uri = c.URI;
    private MongoDatabase database;
    ObjectId documentId = new ObjectId("66f0cf69a3b564bde83f9d7e");

    
    public Grupo() {
    }
    
    // Constructor que recibe la conexión de Mongo
    public Grupo(Mongo mongoConnection) {
        this.database = mongoConnection.getDatabase();
    }

    // Método para modificar un grupo indígena
    public void modificarGrupo(String nombreGrupo, Document nuevosDatos) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("ProyectoBDA1");
            MongoCollection<Document> collection = database.getCollection("Grupos");

            // Actualizar el grupo específico
            collection.updateOne(
                new Document("_id", documentId).append("indigenous_groups.name", nombreGrupo),
                new Document("$set", new Document("indigenous_groups.$", nuevosDatos))
            );

            System.out.println("Grupo modificado exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al modificar el grupo: " + e.getMessage());
        }
    }

    // Método para consultar un grupo indígena
    public Document consultarGrupo(String nombreGrupo) {
        // Conexión a MongoDB
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("ProyectoBDA1");
        MongoCollection<Document> collection = database.getCollection("Grupos");
        
        // Obtener el documento
        Document document = collection.find(new Document("_id", documentId)).first();

        // Inicializar el grupo encontrado como null
        Document grupoEncontrado = null;

        if (document != null) {
            // Obtener la lista de grupos indígenas
            List<Document> indigenousGroups = (List<Document>) document.get("indigenous_groups");

            // Buscar el grupo por nombre
            for (Document grupo : indigenousGroups) {
                if (grupo.getString("name").equalsIgnoreCase(nombreGrupo)) {
                    grupoEncontrado = grupo;
                    break; // Salir del bucle al encontrar el grupo
                }
            }
        }

        // Cerrar la conexión
        mongoClient.close();

        return grupoEncontrado;   
    
    }

    public List<Document> listaGrupos() {
        // Conexión a MongoDB
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("ProyectoBDA1");
        MongoCollection<Document> collection = database.getCollection("Grupos");

        // Obtener el documento
        Document document = collection.find(new Document("_id", documentId)).first();

        // Obtener la lista de grupos indígenas y retornarla como un arreglo de Documentos
        List<Document> indigenousGroups = (List<Document>) document.get("indigenous_groups");

        // Cerrar la conexión
        mongoClient.close();

        return indigenousGroups;   
    } 
    
    public void eliminarGrupo(String nombreGrupo) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("ProyectoBDA1");
            MongoCollection<Document> collection = database.getCollection("Grupos");

            // Eliminar el grupo específico
            collection.updateOne(
                new Document("_id", documentId),
                new Document("$pull", new Document("indigenous_groups", new Document("name", nombreGrupo)))
            );
        } catch (Exception e) {
            System.out.println("Error al eliminar el grupo: " + e.getMessage());
        }
    }
    
    public boolean grupoIndigenaExiste( String nombreGrupo) {
        // Conexión a MongoDB
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("ProyectoBDA1");
        MongoCollection<Document> collection = database.getCollection("Grupos");

        // Verificar si el grupo indígena ya existe
        Document existingGroup = collection.find(
                Filters.and(
                        Filters.eq("_id", documentId),
                        Filters.elemMatch("indigenous_groups", Filters.eq("name", nombreGrupo))
                )
        ).first();

        if (existingGroup == null) {
            return false ;
        }
        // Cerrar la conexión
        mongoClient.close();
        
        return true;
    }
    
    public void insertarGrupoIndigena(String pNombre, String pUbicacion, String pProvincia,String pCantidad,
                        String[] pLenguas,String[] pClanes, String pLiderazgo,String pPracticas) {
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("ProyectoBDA1");
        MongoCollection<Document> collection = database.getCollection("Grupos"); 

        // Definir el nuevo grupo indígena a agregar
        Document newGroup = new Document("name", pNombre)
                .append("location", new Document("geographic_area", pUbicacion)
                        .append("province", pProvincia))
                .append("population", new Document("year", 2024)
                        .append("number", Integer.parseInt(pCantidad)))
                .append("languages_spoken", Arrays.asList(pLenguas))
                .append("social_structure", new Document("clans", Arrays.asList(pClanes))
                        .append("leadership", pLiderazgo)
                        .append("cultural_practices", pPracticas));

        // Actualizar el array indigenous_groups agregando el nuevo grupo
        collection.updateOne(Filters.eq("_id", documentId),
                Updates.addToSet("indigenous_groups", newGroup));

        // Cerrar la conexión
        mongoClient.close();
    }
}