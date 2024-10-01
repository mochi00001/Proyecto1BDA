/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package Programa.Controlador;

import Programa.Vista.Login;


/**
 *
 * @author danie
 */
public class Comidas {
    public final String URI = "mongodb+srv://daniel:6uHSokMoYltot5Em@laboratorio1.uzvc0.mongodb.net/ProyectoBDA1";
    public static boolean rol = true;
    
    public static void main(String[] args) {
        Login pantalla = new Login();
        pantalla.show(true);
        rol=true;
    }
    
    public void esInvestigador(){
        rol=true;
    }
    
    public void esColaborador(){
        rol=false;
    }
    
    
    public boolean tipoRol(){
        return rol;
    }
    
}
