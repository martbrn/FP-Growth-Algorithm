package Interfaz;

import Algoritmo.GeneracionReglas.Regla;
import Algoritmo.ItemsetsFrecuentes.Comparacion;
import Algoritmo.ItemsetsFrecuentes.Frecuente;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

public class VentanaFinal extends javax.swing.JFrame {

    private String MinConf; // Umbral de confianza
    private String MinSup; // Umbral de soporte
    private String RutaFinal; // Ruta final
    private ArrayList<Frecuente> ConjunFrecuentes; // Conjunto de frecuentes
    private ArrayList<Regla> COnjunReglas; // Conjunto de reglas
    private double tiempo; // Tiempo de procesamiento
    private ArrayList<Comparacion> Comparaciones; // Conjunto de comparaciones
    private ArrayList<Regla> MayorConfianza; // Conjunto de reglas ordenadas por las confianza
    private ArrayList<Regla> MayorSoporte; // Conjunto de reglas ordenadas por el soporte
    private File path; // Archivo
     private int numtransc; // Numero de transacciones

    // Constructor de la clase
    public VentanaFinal() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setTitle("Inteligencia Artificial - Algoritmo FP-Growth");
        this.setResizable(false);
        this.setSize(jPanel1.getSize());
        jComboBox1.removeAllItems();
        jComboBox2.removeAllItems();
        jButton1.setEnabled(false);
        this.setComparaciones(new ArrayList<Comparacion>());
         jButton1.setHorizontalTextPosition(SwingConstants.LEFT);
          setIconImage(new ImageIcon(getClass().getResource("/Imagenes/icons-180x180.png")).getImage());
    }

     //Getters y Setter de la clase
    public ArrayList<Comparacion> getComparaciones() {
        return Comparaciones;
    }

    public void setComparaciones(ArrayList<Comparacion> Comparaciones) {
        this.Comparaciones = Comparaciones;
    }

    public ArrayList<Regla> getMayorConfianza() {
        return MayorConfianza;
    }

    public void setMayorConfianza(ArrayList<Regla> MayorConfianza) {
        this.MayorConfianza = MayorConfianza;
    }

    public ArrayList<Regla> getMayorSoporte() {
        return MayorSoporte;
    }

    public void setMayorSoporte(ArrayList<Regla> MayorSoporte) {
        this.MayorSoporte = MayorSoporte;
    }

    public File getPath() {
        return path;
    }

    public void setPath(File path) {
        this.path = path;
    }

    public int getNumtransc() {
        return numtransc;
    }

    public void setNumtransc(int numtransc) {
        this.numtransc = numtransc;
    }

    public double getTiempo() {
        return tiempo;
    }

    public String getRutaFinal() {
        return RutaFinal;
    }

    public void setRutaFinal(String RutaFinal) {
        this.RutaFinal = RutaFinal;
    }

    public void setTiempo(double tiempo) {
        this.tiempo = tiempo;
    }

    public String getMinConf() {
        return MinConf;
    }

    public void setMinConf(String MinConf) {
        this.MinConf = MinConf;
    }

    public String getMinSup() {
        return MinSup;
    }

    public void setMinSup(String MinSup) {
        this.MinSup = MinSup;
    }

    public ArrayList<Frecuente> getConjunFrecuentes() {
        return ConjunFrecuentes;
    }

    public void setConjunFrecuentes(ArrayList<Frecuente> ConjunFrecuentes) {
        this.ConjunFrecuentes = ConjunFrecuentes;
    }

    public ArrayList<Regla> getCOnjunReglas() {
        return COnjunReglas;
    }

    public void setCOnjunReglas(ArrayList<Regla> COnjunReglas) {
        this.COnjunReglas = COnjunReglas;
    }

    // Metodo que carga las reglas al JTable 
    public void CargarReglas() {

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Reglas");
        modelo.addColumn("Soporte (%)");
        modelo.addColumn("Confianza (%)");
        JTableHeader th;
        th = jTable1.getTableHeader();
        Font fuente = new Font("Century Gothic", Font.PLAIN, 24);
        th.setFont(fuente);
        int cont = 1;
        jLabel10.setText("" + this.getCOnjunReglas().size());
        for (int i = 0; i < this.getCOnjunReglas().size(); i++) {//Se recorre el conjunto de reglas
            Regla regla = this.getCOnjunReglas().get(i);
            String premisa = "";
            String conclusion = "";
            for (int x = 0; x < regla.getPremsisa().size(); x++) {//Se concatenan los elementos de la premisa
                if (x != regla.getPremsisa().size() - 1) {
                    premisa = premisa + regla.getPremsisa().get(x) + " , ";
                } else {
                    premisa = premisa + regla.getPremsisa().get(x) + "  ";
                }
            }
            for (int x = 0; x < regla.getConclusion().size(); x++) {// Se concatenan los elementos de la conclusión
                if (x != regla.getConclusion().size() - 1) {
                    conclusion = conclusion + regla.getConclusion().get(x) + " , ";
                } else {
                    conclusion = conclusion + regla.getConclusion().get(x) + "  ";
                }
            }
            String reg = cont + ".      " + premisa + " --> " + conclusion; // Se concatenan la premisa y conclusión junto con el simbolo "-->"
            cont = cont + 1;
            String confi = regla.getConfianza() + "";
            String sup = regla.getSoporte() + "";
            modelo.addRow(new String[]{reg,sup,confi}); // Carga el string resultante en una fila de la tabla
        }
        jTable1.setModel(modelo);
        TableColumnModel columnModel = jTable1.getColumnModel();
             columnModel.getColumn(0).setPreferredWidth(720);
             columnModel.getColumn(1).setPreferredWidth(3);
               columnModel.getColumn(2).setPreferredWidth(20);
    }

     // Carga del ComboBox para seleccionar el frecuente en el que se quiera vizualisar sus itemsets
    public void CargarCombo(int numero) {
        int cont = 1;
        String item;
        jComboBox1.addItem("Seleccionar Numero de Frecuente");
        while (cont <= numero) {
            item = Integer.toString(cont);
            jComboBox1.addItem(item);
            cont = cont + 1;
        }
        jLabel4.setText(this.getMinSup() + " %");
        jLabel6.setText(this.getMinConf() + " %");
        String time = this.getTiempo() + " Segundos ";
        jLabel8.setText(time);
    }

     // Carga del ComboBox para ordenar las reglas segun la confianza o soporte
    public void CargarCombo2() {
        jComboBox2.addItem("Ordenar por");
        jComboBox2.addItem("Mayor Confianza");
        jComboBox2.addItem("Mayor Soporte");
    }
    
     //Metodo encargado de ordenar las reglas segun el soporte y la confianza
    public void OrdenarReglas() {
        ArrayList<Regla> reglas = new ArrayList<Regla>();
        for (int i = 0; i < this.getCOnjunReglas().size(); i++) {
            reglas.add(this.getCOnjunReglas().get(i));
        }

        for (int i = 0; i < reglas.size(); i++) {
            reglas.get(i).setBandera(false);
        }
        Collections.sort(reglas);
        this.setMayorConfianza(reglas);

        ArrayList<Regla> reglas2 = new ArrayList<Regla>();
        for (int i = 0; i < this.getCOnjunReglas().size(); i++) {
            reglas2.add(this.getCOnjunReglas().get(i));
        }
        for (int i = 0; i < reglas2.size(); i++) {
            reglas2.get(i).setBandera(true);
        }
        Collections.sort(reglas2);
        this.setMayorSoporte(reglas2);

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
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 53, 100));
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Resultados Algoritmo FP-Growth");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(350, 20, 560, 54);

        jButton1.setBackground(new java.awt.Color(0, 53, 100));
        jButton1.setFont(new java.awt.Font("Century Gothic", 1, 19)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/if_tests_60211.png"))); // NOI18N
        jButton1.setText("Mostrar reglas");
        jButton1.setToolTipText("Muestra todas las reglas generadas");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(15, 12, 250, 70);

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 27)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(jLabel4);
        jLabel4.setBounds(840, 200, 124, 40);

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 27)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(jLabel6);
        jLabel6.setBounds(840, 160, 100, 31);

        jLabel8.setFont(new java.awt.Font("Century Gothic", 0, 27)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(jLabel8);
        jLabel8.setBounds(370, 160, 259, 31);

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 27)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Mostrar Frecuente numero:");
        jLabel2.setToolTipText("Muestra los itemsets del frecuente seleccionado");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(10, 110, 351, 34);

        jComboBox1.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jComboBox1.setForeground(new java.awt.Color(255, 255, 255));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox1);
        jComboBox1.setBounds(370, 110, 400, 40);

        jButton2.setBackground(new java.awt.Color(0, 53, 100));
        jButton2.setFont(new java.awt.Font("Century Gothic", 1, 19)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/back2.png"))); // NOI18N
        jButton2.setText("Comenzar de nuevo");
        jButton2.setToolTipText("Ir a la ventana principal");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(1000, 10, 290, 70);

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 27)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Soporte:");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(720, 200, 120, 34);

        jTable1 = new javax.swing.JTable(){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        jTable1.setBorder(new javax.swing.border.MatteBorder(null));
        jTable1.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable1.setGridColor(new java.awt.Color(0, 53, 100));
        jTable1.setRowHeight(30);
        jTable1.setRowMargin(0);
        jTable1.setSelectionBackground(new java.awt.Color(0, 53, 100));
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(30, 260, 1260, 410);

        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 27)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Tiempo de procesamiento: ");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(10, 160, 358, 34);

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 27)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Confianza:");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(690, 160, 150, 34);

        jLabel9.setFont(new java.awt.Font("Century Gothic", 1, 27)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Cantidad de reglas generadas:");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(10, 200, 413, 34);

        jLabel10.setFont(new java.awt.Font("Century Gothic", 0, 27)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(jLabel10);
        jLabel10.setBounds(430, 200, 94, 31);

        jButton3.setBackground(new java.awt.Color(0, 53, 100));
        jButton3.setFont(new java.awt.Font("Century Gothic", 1, 19)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/if_statistics_59311.png"))); // NOI18N
        jButton3.setText("Ver comparaciones");
        jButton3.setToolTipText("Ver los resultados de cada corrida realizada");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);
        jButton3.setBounds(1030, 180, 260, 70);

        jLabel12.setFont(new java.awt.Font("Century Gothic", 1, 27)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Ordenar reglas por:");
        jLabel12.setToolTipText("Ordena las reglas según el parámetro seleccionado");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(800, 110, 270, 40);

        jComboBox2.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jComboBox2.setForeground(new java.awt.Color(255, 255, 255));
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox2);
        jComboBox2.setBounds(1070, 110, 220, 40);

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/hdblue.jpg"))); // NOI18N
        jLabel11.setText("jLabel11");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(0, 0, 1340, 740);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 734, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
 // Evento que se activa cuando se selecciona un elemento del ComboBox , cargando los itemsets del frecuente seleccinado en la tabla
    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        if (jComboBox1.getSelectedItem() != "Seleccionar Numero de Frecuente") {
            jComboBox1.removeItem("Seleccionar Numero de Frecuente");
            jButton1.setEnabled(true);
            DefaultTableModel modelo = new DefaultTableModel();
            modelo.addColumn("ItemSets");
            modelo.addColumn("Soporte (%)");
            final SwingWorker worker2 = new SwingWorker() {
                @Override
                protected Object doInBackground() throws Exception {
                    int item = Integer.valueOf((String) jComboBox1.getSelectedItem());
                    Frecuente frec = VentanaFinal.this.getConjunFrecuentes().get(item - 1); // Se obtiene el frecuente seleccionado

                    for (int i = 0; i < frec.getItemsets().size(); i++) { // Recorre los itemsets del frecuente 
                        String sis;
                        String sup;
                        sis = "{ ";
                        for (int x = 0; x < frec.getItemsets().get(i).getElementos().size(); x++) {
                            if (x != (frec.getItemsets().get(i).getElementos().size() - 1)) {
                                sis = sis + frec.getItemsets().get(i).getElementos().get(x) + " , ";
                            } else {
                                sis = sis + frec.getItemsets().get(i).getElementos().get(x);
                            }
                        }
                        sis = sis + " } ";
                        sup = ((frec.getItemsets().get(i).getFrecuencia()*100)/VentanaFinal.this.getNumtransc()) + "";
                        modelo.addRow(new String[]{sis,sup}); // Carga el itemset a la tabla
                    }
                    jTable1.setModel(modelo); // Se setea la tabla con todos los itemsets del frecuente
                    TableColumnModel columnModel = jTable1.getColumnModel();
             columnModel.getColumn(0).setPreferredWidth(800);
             columnModel.getColumn(1).setPreferredWidth(10);
                    return null;
                }
            };
            worker2.execute();
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        this.CargarReglas();
        jButton1.setEnabled(false);
    }//GEN-LAST:event_jButton1ActionPerformed
 // Evento que se ejecuta cuando se selecciona el boton "Comenzar de nuevo"
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        final SwingWorker worker = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                VentanaPrincipal Vent = new VentanaPrincipal(); // Se carga una nueva ventana de inicio
                Vent.setComparaciones(VentanaFinal.this.getComparaciones());
                Vent.setPath(path);
                Vent.setjTextPane3(VentanaFinal.this.getMinSup());
                Vent.setjTextPane4(VentanaFinal.this.getMinConf());
                Vent.setjTextPane1(VentanaFinal.this.getRutaFinal());
                Vent.setVisible(true);
                VentanaFinal.this.dispose();// Se cierra la ventana final
                return null;
            }
        };
        worker.execute();
    }//GEN-LAST:event_jButton2ActionPerformed

    // Evento que se activa cuando se selecciona "Ver Comparaciones"
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        PasadasAlg pasadas = new PasadasAlg(this, true);
        final SwingWorker worker = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                pasadas.CargarComparacion(VentanaFinal.this.getComparaciones()); // Se cargan los resultados de cada corrida en la tabla  
                pasadas.setVisible(true);
                return null;
            }
        };
        worker.execute();
    }//GEN-LAST:event_jButton3ActionPerformed

    //Evento que se activa cuando se selecciona un ordenamiento 
    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        jButton1.setEnabled(false);
        if (jComboBox2.getSelectedItem() != "Ordenar por") {
            jComboBox2.removeItem("Ordenar por");
            if (jComboBox2.getSelectedItem() == "Mayor Confianza") {
                this.CargarReglas2(1);
            } else if (jComboBox2.getSelectedItem() == "Mayor Soporte") {
                this.CargarReglas2(2);
            }

        }
    }//GEN-LAST:event_jComboBox2ActionPerformed
    
     // Metodo utilizado para poder cargar las reglas ordenadas segun el soporte o confianza
    public void CargarReglas2(int numero) {
        ArrayList<Regla> reglas = null;
        if (numero == 1) {
            reglas = this.getMayorConfianza();
        } else {
            reglas = this.getMayorSoporte();
        }

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Reglas");
        modelo.addColumn("Soporte (%)");
        modelo.addColumn("Confianza (%)");
        JTableHeader th;
        th = jTable1.getTableHeader();
        Font fuente = new Font("Century Gothic", Font.PLAIN, 24);
        th.setFont(fuente);
        int cont = 1;
        jLabel10.setText("" + this.getCOnjunReglas().size());
        for (int i = 0; i < reglas.size(); i++) { //Se recorre el conjunto de reglas
            Regla regla = reglas.get(i);
            String premisa = "";
            String conclusion = "";
            for (int x = 0; x < regla.getPremsisa().size(); x++) { //Se concatenan los elementos de la premisa
                if (x != regla.getPremsisa().size() - 1) {
                    premisa = premisa + regla.getPremsisa().get(x) + " , ";
                } else {
                    premisa = premisa + regla.getPremsisa().get(x) + "  ";
                }
            }
            for (int x = 0; x < regla.getConclusion().size(); x++) { // Se concatenan los elementos de la conclusión
                if (x != regla.getConclusion().size() - 1) {
                    conclusion = conclusion + regla.getConclusion().get(x) + " , ";
                } else {
                    conclusion = conclusion + regla.getConclusion().get(x) + "  ";
                }
            }
            String reg = cont + ".      " + premisa + " --> " + conclusion; // Se concatenan la premisa y conclusión junto con el simbolo "-->"
            cont = cont + 1;
            String confi = regla.getConfianza() + "";
            String sup = regla.getSoporte() + "";
            modelo.addRow(new String[]{reg, sup, confi}); // Carga el string resultante en una fila de la tabla
        }
        jTable1.setModel(modelo);
         TableColumnModel columnModel = jTable1.getColumnModel();
             columnModel.getColumn(0).setPreferredWidth(720);
             columnModel.getColumn(1).setPreferredWidth(10);
               columnModel.getColumn(2).setPreferredWidth(20);

    }
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
            java.util.logging.Logger.getLogger(VentanaFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaFinal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaFinal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
