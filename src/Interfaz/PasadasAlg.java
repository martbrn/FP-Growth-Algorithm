
package Interfaz;

import Algoritmo.ItemsetsFrecuentes.Comparacion;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

public class PasadasAlg extends javax.swing.JDialog {
  
    // Constructor de la clase
    public PasadasAlg(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
       this.setSize(jPanel1.getSize());
       this.setTitle("Inteligencia Artificial - Pasadas del algoritmo");
       this.setLocationRelativeTo(null);
    }

    // Metodo que carga los resultados de cada corrida del algoritmo en una tabla 
    public void CargarComparacion (ArrayList <Comparacion> comparaciones){
            DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Soporte (%)");
        modelo.addColumn("Confianza (%)");
        modelo.addColumn("Tiempo de Procesamiento (Segundos)");
        modelo.addColumn("Cantidad de reglas");
         JTableHeader th;  
        th = jTable1.getTableHeader();
        Font fuente= new Font ("Century Gothic", Font.PLAIN, 24);
        th.setFont(fuente);
        for (int i=0; i < comparaciones.size(); i++){ // Recorre la lista de comparaciones
        Comparacion comp= comparaciones.get(i); // Obtiene la comparación "i"
          String uno= comp.getMinSup() + "";
          String dos= comp.getMinConf() + "";
          String tres= comp.getTiempo() + "";
          String cuatro= comp.getRegla() + "";         
        modelo.addRow(new String [] {uno,dos,tres,cuatro}); // Agrega una fila a la tabla con los datos de la comparación
        }       
        jTable1.setModel(modelo);
                TableColumnModel columnModel = jTable1.getColumnModel();
             columnModel.getColumn(0).setPreferredWidth(40);
             columnModel.getColumn(1).setPreferredWidth(60);
               columnModel.getColumn(2).setPreferredWidth(350);
                columnModel.getColumn(3).setPreferredWidth(170);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 53, 100));
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 39)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Pasadas del algoritmo");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(380, 10, 450, 49);

        jTable1 = new javax.swing.JTable(){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        jTable1.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.setRowHeight(30);
        jTable1.setRowMargin(0);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(15, 77, 1120, 266);

        jButton1.setBackground(new java.awt.Color(0, 53, 100));
        jButton1.setFont(new java.awt.Font("Century Gothic", 1, 19)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/if_Close_Box_Red_34217.png"))); // NOI18N
        jButton1.setText("Cerrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(50, 10, 160, 50);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/hdblue.jpg"))); // NOI18N
        jLabel2.setText("jLabel2");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(0, 0, 1170, 420);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(PasadasAlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PasadasAlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PasadasAlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PasadasAlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                PasadasAlg dialog = new PasadasAlg(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
