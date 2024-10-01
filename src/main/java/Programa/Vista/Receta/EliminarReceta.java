/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Programa.Vista.Receta;

import Programa.Controlador.Ingredientes;
import Programa.Vista.Menu.MenuEliminar;
import Programa.Controlador.Recetas;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.bson.Document;

/**
 *
 * @author danie
 */
public class EliminarReceta extends javax.swing.JFrame {

    DefaultTableModel mt = new DefaultTableModel();
    DefaultTableModel mt1 = new DefaultTableModel();
    /**
     * Creates new form EliminarReceta
     */
    public EliminarReceta() {
        initComponents();
        String ids []={"Nombre en español", "Nombres indigenas","Cantidad"};
        
        mt.setColumnIdentifiers(ids);
        jTable2.setModel(mt);
        
        String ids2 []={"Pasos"};
        
        mt1.setColumnIdentifiers(ids2);
        jTable3.setModel(mt1);
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

        jLabel3 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        nombreText = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        tiempoLabel = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        ocasionLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        quienLoPreparaLabel = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        festividadesLabel = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gastronomia Indigena");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel3.setText("Eliminar receta");

        jButton8.setText("Retroceder");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton6.setText("Eliminar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel11.setText("Información:");

        jLabel14.setText("Ingredientes:");

        nombreText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreTextActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(jTable2);

        jLabel7.setText("Ocasión:");

        jButton9.setText("Buscar");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        tiempoLabel.setText("Tiempo de preparación");

        jLabel8.setText("Tiempo de preparación:");

        ocasionLabel.setText("Ocasión");

        jLabel1.setText("Nombre:");

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(jTable3);

        jLabel17.setText("Pasos de preparación:");

        quienLoPreparaLabel.setText("Quien lo prepara");

        jLabel9.setText("Quien lo prepara:");

        festividadesLabel.setText("Uso en festividades");

        jLabel10.setText("Uso en festividades:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel14)
                                .addGap(116, 116, 116))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(108, 108, 108)
                                .addComponent(jLabel17))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(230, 230, 230)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(78, 78, 78)
                                .addComponent(jLabel3))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(106, 106, 106)
                                .addComponent(jLabel11))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(nombreText, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(tiempoLabel))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(80, 80, 80)
                                        .addComponent(jLabel7))
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(quienLoPreparaLabel)
                                    .addComponent(ocasionLabel)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel10)
                                .addGap(18, 18, 18)
                                .addComponent(festividadesLabel)))
                        .addGap(0, 193, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(244, 244, 244)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nombreText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(tiempoLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(ocasionLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(quienLoPreparaLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(festividadesLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        MenuEliminar PasarVentana = new MenuEliminar();
        PasarVentana.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        Recetas m = new Recetas();
        
        if(!m.verificarRecetaExiste(nombreText.getText())){
            JOptionPane.showMessageDialog(this, "Ingrediente no existe", "Error al eliminar", JOptionPane.WARNING_MESSAGE);
        }else{
            m.eliminarReceta(nombreText.getText());
            tiempoLabel.setText("Tiempo de preparación");
            ocasionLabel.setText("Ocasión");
            quienLoPreparaLabel.setText("Quien lo prepara");
            festividadesLabel.setText("Uso en festividades");
            nombreText.setText("");
            mt.getDataVector().removeAllElements();
            jTable2.setModel(mt);
            mt1.getDataVector().removeAllElements();
            jTable3.setModel(mt1);
            JOptionPane.showMessageDialog(this, "Información eliminada", "Datos Eliminados", JOptionPane.INFORMATION_MESSAGE);
        } 
    }//GEN-LAST:event_jButton6ActionPerformed

    private void nombreTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreTextActionPerformed

    
    public void leerContenidoReceta(Document receta) {
        if (receta != null) {
            // Leer los datos básicos de la receta
            String recipeName = receta.getString("recipe_name");
            String preparationTime = receta.getString("preparation_time");
            String occasion = receta.getString("occasion");
            Boolean usedForFestivities = receta.getBoolean("used_for_festivities");
            String whoPrepares = receta.getString("who_prepares");

            System.out.println("Nombre de la receta: " + recipeName);
            System.out.println("Tiempo de preparación: " + preparationTime);
            System.out.println("Ocasión: " + occasion);
            System.out.println("Usada para festividades: " + (usedForFestivities ? "Sí" : "No"));
            System.out.println("¿Quién la prepara?: " + whoPrepares);

            // Leer los ingredientes
            List<Document> ingredientes = (List<Document>) receta.get("ingredients");
            System.out.println("Ingredientes:");

            for (Document ingrediente : ingredientes) {
                String nombreEsp = ingrediente.getString("name_spanish");
                Document nombresIndigenas = (Document) ingrediente.get("names_indigenous_languages");
                String cantidad = ingrediente.getString("quantity");

                System.out.println("- Ingrediente: " + nombreEsp + " (" + nombresIndigenas.toJson() + ")");
                System.out.println("  Cantidad: " + cantidad);
            }

            // Leer los pasos de preparación
            List<String> pasosPreparacion = (List<String>) receta.get("preparation_steps");
            System.out.println("Pasos de preparación:");

            for (String paso : pasosPreparacion) {
                System.out.println("- " + paso);
            }
        } else {
            System.out.println("No se encontró la receta para mostrar los detalles.");
        }
    }
    
    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        Recetas m = new Recetas();

        mt.getDataVector().removeAllElements();
        mt1.getDataVector().removeAllElements();
        String[] datos = new String[11]; 
      
        List<Document> recetas = m.obtenerRecetas();
        
        if (recetas != null && !recetas.isEmpty()) {
            for (Document receta : recetas) {
                if(receta.getString("recipe_name").equals(nombreText.getText())){
                    
                    tiempoLabel.setText(receta.getString("preparation_time"));
                    ocasionLabel.setText(receta.getString("occasion"));
                    
                    quienLoPreparaLabel.setText( receta.getString("who_prepares"));
                    festividadesLabel.setText(receta.getBoolean("used_for_festivities") ? "Sí" : "No");
                    
                    
                    List<String> preparationSteps = (List<String>) receta.get("preparation_steps");
                    if (preparationSteps != null && !preparationSteps.isEmpty()) {
                        for (String step : preparationSteps) {
                            // Agregar cada paso como una fila individual en la tabla
                            mt1.addRow(new Object[]{step});
                        }
                    } else {
                        // Si no hay pasos de preparación, agregar una fila con un mensaje
                        mt1.addRow(new Object[]{"Sin pasos de preparación disponibles"});
                    }
                    
                   List<Document> ingredientes = (List<Document>) receta.get("ingredients");
                    if (ingredientes != null && !ingredientes.isEmpty()) {
                        for (Document ingrediente : ingredientes) {
                            // Obtener los nombres en idiomas indígenas como un Document
                            Document namesIndigenousLanguages = (Document) ingrediente.get("names_indigenous_languages");

                            // Verificar si el documento de nombres indígenas no es null
                            StringBuilder namesIndigenousStr = new StringBuilder();
                            if (namesIndigenousLanguages != null) {
                                for (String key : namesIndigenousLanguages.keySet()) {
                                    String value = namesIndigenousLanguages.getString(key);
                                    namesIndigenousStr.append(key).append(": ").append(value).append(", ");
                                }

                                // Eliminar la última coma y espacio extra
                                if (namesIndigenousStr.length() > 0) {
                                    namesIndigenousStr.setLength(namesIndigenousStr.length() - 2);
                                }
                            } else {
                                namesIndigenousStr.append("Sin nombres indígenas disponibles");
                            }

                            // Agregar el ingrediente a la tabla con el nombre en español, nombres indígenas y cantidad
                            mt.addRow(new Object[]{
                                ingrediente.getString("name_spanish"),
                                namesIndigenousStr.toString(),
                                ingrediente.getString("quantity")
                            });
                        }
                    } else {
                        mt.addRow(new Object[]{"Sin ingredientes disponibles"});
                    }
                }
/*
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
                
                

               

                

                // Añadir la fila al modelo de la tabla
                model.addRow(datos);*/
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró la información de recetas", "Error al cargar recetas", JOptionPane.WARNING_MESSAGE);
        }
        
        
        /*
        if(!m.verificarRecetaExiste(nombreText.getText())){
            JOptionPane.showMessageDialog(this, "El grupo no existe", "Error al modificar", JOptionPane.WARNING_MESSAGE);
        }else{
            
            if (nombreText.getText().isEmpty() || nombreText.getText().isBlank()){
                JOptionPane.showMessageDialog(this, "Escriba lo que se le solicita", "No se a completado los datos", JOptionPane.WARNING_MESSAGE);
            } else {
                Document receta = m.buscarRecetaPorNombre(nombreText.getText());
                
                if (receta != null) {
                    
                    // Leer los datos básicos de la receta
                    String recipeName = receta.getString("recipe_name");
                    String preparationTime = receta.getString("preparation_time");
                    String occasion = receta.getString("occasion");
                    Boolean usedForFestivities = receta.getBoolean("used_for_festivities");
                    String whoPrepares = receta.getString("who_prepares");

                    // Mostrar información básica de la receta
                    
                    

                    // Leer los ingredientes
                    List<Document> ingredientes = (List<Document>) receta.get("ingredients");
                    System.out.println("Ingredientes:");

                    for (Document ingrediente : ingredientes) {
                        String nombreEsp = ingrediente.getString("name_spanish");
                        Document nombresIndigenas = (Document) ingrediente.get("names_indigenous_languages");
                        String cantidad = ingrediente.getString("quantity");

                        System.out.println("- Ingrediente: " + nombreEsp + " (" + nombresIndigenas.toJson() + ")");
                        System.out.println("  Cantidad: " + cantidad);
                    }

                    // Leer los pasos de preparación
                    List<String> pasosPreparacion = (List<String>) receta.get("preparation_steps");
                    System.out.println("Pasos de preparación:");

                    for (String paso : pasosPreparacion) {
                        System.out.println("- " + paso);
                    }
                } else {
                    System.out.println("No se encontró la receta para mostrar los detalles.");
                }
            }
                JOptionPane.showMessageDialog(this, "Información encontrada", "Información del ingrediente", JOptionPane.INFORMATION_MESSAGE);
            }
        */
    }//GEN-LAST:event_jButton9ActionPerformed

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
            java.util.logging.Logger.getLogger(EliminarReceta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EliminarReceta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EliminarReceta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EliminarReceta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EliminarReceta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel festividadesLabel;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField nombreText;
    private javax.swing.JLabel ocasionLabel;
    private javax.swing.JLabel quienLoPreparaLabel;
    private javax.swing.JLabel tiempoLabel;
    // End of variables declaration//GEN-END:variables
}

