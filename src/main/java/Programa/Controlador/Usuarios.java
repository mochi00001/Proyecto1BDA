/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Programa.Controlador;

import Programa.Controlador.Mongo;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;

import java.util.List;
import java.util.ArrayList;

public class Usuarios {
    private MongoDatabase database;

    // Constructor
    public Usuarios(Mongo mongoConnection) {
        this.database = mongoConnection.getDatabase();
    }

    // Método para verificar si un usuario existe y su contraseña es correcta
    public boolean verificarUsuario(String pCedula, String pContrasenna) {
        MongoCollection<Document> collection = database.getCollection("Usuarios");
        try (MongoCursor<Document> cursor = collection.find(eq("cedula", pCedula)).iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                String contraseña = doc.getString("Contraseña");
                System.out.println("Verificando usuario: " + pCedula + " con contraseña: " + pContrasenna);
                if (pContrasenna.equals(contraseña)) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.err.println("Error al verificar usuario: " + e.getMessage());
        }
        return false;
    }

    // Verificar si un usuario con la cédula dada ya existe
    public boolean existeUsuario(String pCedula) {
        MongoCollection<Document> collection = database.getCollection("Usuarios");
        Document foundUser = collection.find(eq("cedula", pCedula)).first();
        return foundUser != null;
    }

    // Crear un nuevo usuario
    public void crearUsuario(String pCedula, String pNombre, String pContrasenna, String pTipo) {
        MongoCollection<Document> collection = database.getCollection("Usuarios");
        Document newUser = new Document("cedula", pCedula)
                .append("Nombre", pNombre)
                .append("Contraseña", pContrasenna)
                .append("Tipo", pTipo);
        collection.insertOne(newUser);
    }

    // Eliminar un usuario por cédula
    public void eliminarUsuario(String pCedula) {
        MongoCollection<Document> collection = database.getCollection("Usuarios");
        collection.deleteOne(eq("cedula", pCedula));
    }

    // Modificar datos de un usuario
    public void modificarUsuario(String pCedula, String nuevoNombre, String nuevaContrasenna, String nuevoTipo) {
        MongoCollection<Document> collection = database.getCollection("Usuarios");
        collection.updateOne(eq("cedula", pCedula),
                new Document("$set", new Document("Nombre", nuevoNombre)
                        .append("Contraseña", nuevaContrasenna)
                        .append("Tipo", nuevoTipo)));
    }

    // Obtener una lista de todos los usuarios
    public List<Document> listaUsuarios() {
        MongoCollection<Document> collection = database.getCollection("Usuarios");
        List<Document> usuariosList = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                usuariosList.add(cursor.next());
            }
        } catch (Exception e) {
            System.err.println("Error al obtener lista de usuarios: " + e.getMessage());
        }
        return usuariosList;
    }

    // Buscar un usuario por cédula
    public Document buscarUsuarioPorCedula(String pCedula) {
        MongoCollection<Document> collection = database.getCollection("Usuarios");
        return collection.find(eq("cedula", pCedula)).first();
    }
}


