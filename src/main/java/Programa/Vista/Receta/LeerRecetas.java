/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Programa.Vista.Receta;

import Programa.Vista.Menu.MenuLeer;
import Programa.Controlador.Recetas;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.bson.Document;

/**
 *
 * @author danie
 */
public class LeerRecetas extends javax.swing.JFrame {
    
    DefaultTableModel model = new DefaultTableModel();
    /**
     * Creates new form LeerRecetas
     */
    public LeerRecetas() {
        initComponents();
        model.addColumn("Nombre");
        model.addColumn("Ingredientes");
        model.addColumn("Preparacion");
        model.addColumn("Tiempo");
        model.addColumn("Ocasion");
        model.addColumn("Quien lo prepara");
        model.addColumn("Festividad");  
               
        jTable1.setModel(model);  
        this.setLocationRelativeTo(null);                       
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton8 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gastronomia Indigena");

        jButton8.setText("Retroceder");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton6.setText("Consultar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel3.setText("Consultar recetas");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 476, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(521, 521, 521))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(463, 463, 463))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(7, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        MenuLeer PasarVentana = new MenuLeer();
        PasarVentana.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton8ActionPerformed

    public void imprimirRecetas(List<Document> recetas) {
        String [] datos = new String [7];
        
        if (recetas != null) {
            for (Document receta : recetas) {
                                
                datos[0] = receta.getString("recipe_name");
                datos[3] = receta.getString("preparation_time");
                datos[4] = receta.getString("occasion");
                datos[5] = receta.getString("who_prepares");
                String valorVerdad = receta.getBoolean("used_for_festivities") ? "Sí" : "No";
                datos[6] = valorVerdad;
               
                StringBuilder listaIngredientes = new StringBuilder();
                
                // Obtener la lista de ingredientes
                List<Document> ingredientes = (List<Document>) receta.get("ingredients");
                for (Document ingrediente : ingredientes) {
                    listaIngredientes.append(" - ")
                                     .append(ingrediente.getString("name_spanish"))
                                     .append(" (")
                                     .append(ingrediente.getString("quantity"))
                                     .append(")"); 
                }
                datos[1] = listaIngredientes.toString();
                
                StringBuilder listaPasos = new StringBuilder();
                // Mostrar los pasos de preparación
                List<String> pasosPreparacion = (List<String>) receta.get("preparation_steps");
                for (String paso : pasosPreparacion) {
                    listaPasos.append(" - ").append(paso);
                }
                datos[2] = listaPasos.toString();
                
                model.addRow(datos);
            }
        } else {
            System.out.println("No hay recetas para imprimir.");
        }
    } 
    
    public String formatearDocument(Document doc) {
        // Convertir el Document a String
        String jsonString = doc.toJson();

        // Quitar los caracteres {}, comillas dobles, y el formateo adicional
        String resultado = jsonString.replace("{", "")   // Quitar llaves de apertura
                                    .replace("}", "")   // Quitar llaves de cierre
                                    .replace("\"", "")  // Quitar comillas dobles
                                    .trim();            // Eliminar espacios en blanco al inicio o fin

        return resultado;
    }
    
    private void cargarDatosRecetas() {
        // Instancia de la clase que maneja las recetas
        Recetas m = new Recetas(); // Asegúrate de que esta clase tenga los métodos necesarios
        String[] datos = new String[11]; // Ajusta el tamaño del arreglo según la información que desees cargar

        // Obtener la lista de recetas
        List<Document> recetas = m.obtenerRecetas();

        if (recetas != null && !recetas.isEmpty()) {
            for (Document receta : recetas) {
                datos[0] = receta.getString("recipe_name"); // Nombre de la receta

                // Obtener la lista de ingredientes de la receta
                List<Document> ingredientes = (List<Document>) receta.get("ingredients");
                if (ingredientes != null && !ingredientes.isEmpty()) {
                    StringBuilder ingredientesStr = new StringBuilder();
                    for (Document ingrediente : ingredientes) {
                        ingredientesStr.append(ingrediente.getString("name_spanish")).append(", ");
                    }
                    // Eliminar la última coma y espacio
                    if (ingredientesStr.length() > 0) {
                        ingredientesStr.setLength(ingredientesStr.length() - 2); 
                    }
                    datos[1] = ingredientesStr.toString(); // Listado de ingredientes
                } else {
                    datos[1] = "Sin ingredientes disponibles"; // Mensaje en caso de no tener ingredientes
                }
                
                // Obtener y formatear los pasos de preparación
                List<String> preparationSteps = (List<String>) receta.get("preparation_steps");
                if (preparationSteps != null && !preparationSteps.isEmpty()) {
                    StringBuilder stepsStr = new StringBuilder();
                    for (String step : preparationSteps) {
                        stepsStr.append(step).append("; ");
                    }
                    // Eliminar el último punto y coma y espacio
                    if (stepsStr.length() > 0) {
                        stepsStr.setLength(stepsStr.length() - 2);
                    }
                    datos[2] = stepsStr.toString(); // Pasos de preparación
                } else {
                    datos[2] = "Sin pasos de preparación disponibles"; // Mensaje en caso de no tener pasos
                }
                
                

                datos[3] = receta.getString("preparation_time"); // Tiempo de preparación
                datos[4] = receta.getString("occasion"); // Ocasión para la que se prepara

                // Información sobre quién prepara
                datos[5] = receta.getString("who_prepares");
                datos[6] = receta.getBoolean("used_for_festivities") ? "Sí" : "No"; // Usado para festividades

                

                // Añadir la fila al modelo de la tabla
                model.addRow(datos);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró la información de recetas", "Error al cargar recetas", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        cargarDatosRecetas();
    }//GEN-LAST:event_jButton6ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LeerRecetas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LeerRecetas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LeerRecetas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LeerRecetas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LeerRecetas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
