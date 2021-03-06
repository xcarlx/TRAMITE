/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIOS;

import CONEXION.CONEXION;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author CARLOS
 */
public final class CONUSLTA_DOC extends javax.swing.JDialog {
    DefaultTableModel tabla_modelo;
    Statement st;
    ResultSet rs;
    CONEXION mysql = new CONEXION();
    Connection cn = mysql.Conectar();
    /**
     * Creates new form CONUSLTA_DOC
     */
    public CONUSLTA_DOC(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        TablaDocumento("", "", "", "", "");
    }
    public void OrderTabla(){
        tblDocumento.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblDocumento.getColumnModel().getColumn(1).setPreferredWidth(80);
        tblDocumento.getColumnModel().getColumn(2).setPreferredWidth(80);
        tblDocumento.getColumnModel().getColumn(3).setPreferredWidth(80);
        tblDocumento.getColumnModel().getColumn(4).setPreferredWidth(80);
        tblDocumento.getColumnModel().getColumn(5).setPreferredWidth(200);
        tblDocumento.getColumnModel().getColumn(6).setPreferredWidth(200);
        tblDocumento.getColumnModel().getColumn(7).setPreferredWidth(500);
        tblDocumento.getColumnModel().getColumn(8).setPreferredWidth(100);
        tblDocumento.getColumnModel().getColumn(9).setPreferredWidth(80);
        
    }
    public void TablaDocumento(String nrodoc, String hoja_ruta, String remitente, 
            String area_remi, String asunto){
        String titulo[] = {"ID","TIPO DOC","NRO DOC", "HOJA RUTA", "FECHA","AREA REMITE","REMITENTE",
                           "ASUNTO", "OBSERVACIONES","ESTADO"};
        String registro[]=new String[10];
        String sql="select doc.iddoc, td.tipo, doc.nro_doc, doc.hoja_ruta, doc.fecha,doc.area_r, doc.remitente, "
                + " doc.asunto, doc.observaciones, doc.estado from documento doc inner join "
                + "tipo_doc td on doc.idtipo_doc=td.idtipo_doc "
                + "where doc.idactividad=1 and doc.nro_doc LIKE '"+nrodoc+"' OR "
                + "doc.idactividad=1 and doc.hoja_ruta LIKE '"+hoja_ruta+"' OR "
                + "doc.idactividad=1 and doc.remitente LIKE '%"+remitente+"%' OR "
                + "doc.idactividad=1 and doc.area_r LIKE '%"+area_remi+"%' OR "
                + "doc.idactividad=1 and doc.asunto like '%"+asunto+"%'";
        tabla_modelo = new DefaultTableModel(null, titulo);
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                registro[0]=rs.getString(1);
                registro[1]=rs.getString(2);
                registro[2]=rs.getString(3);
                registro[3]=rs.getString(4);
                registro[4]=rs.getString(5);
                registro[5]=rs.getString(6);
                registro[6]=rs.getString(7);
                registro[7]=rs.getString(8);
                registro[8]=rs.getString(9);
                registro[9]=rs.getString(10);
                tabla_modelo.addRow(registro);
            }
            tblDocumento.setModel(tabla_modelo);
        } catch (Exception e) {
            System.out.println(e);
        }
        OrderTabla();
    }
    public void TablaDetalle(String iddoc){
        String titulo[] = {"ID","FECHA","ASIGNADO", "OBSERVACIONES", "ESTADO", "DEPEND DESTINO",
                           "USUARIO", "NOMBRE USUARIO"};
        String registro[]=new String[8];
        String sql="select dr.idderivar, dr.fecha, dr.asignado, dr.observaciones, dr.estado, dep.dependencia, "
                + " dr.usuario, concat(p.nombres,' ',p.apellidos) as persona from derivar dr inner join "
                + "dependencias dep on dr.iddependencia_d=dep.iddependencia inner join usuarios u "
                + "on dr.usuario=u.usuario inner join personal p on u.dni=p.dni where dr.iddoc="+iddoc;
        tabla_modelo = new DefaultTableModel(null, titulo);
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                registro[0]=rs.getString(1);
                registro[1]=rs.getString(2);
                registro[2]=rs.getString(3);
                registro[3]=rs.getString(4);
                registro[4]=rs.getString(5);
                registro[5]=rs.getString(6);
                registro[6]=rs.getString(7);
                registro[7]=rs.getString(8);
                tabla_modelo.addRow(registro);
            }
            tblDetalle_doc.setModel(tabla_modelo);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel1 = new org.edisoncor.gui.panel.Panel();
        jPanel1 = new javax.swing.JPanel();
        txtNro_doc = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtHoja_Ruta = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtRemitente = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtAreaRemite = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtAsunto = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDocumento = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDetalle_doc = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CONSULTAR DOCUMENTOS");

        panel1.setColorPrimario(new java.awt.Color(255, 255, 255));
        panel1.setColorSecundario(new java.awt.Color(51, 153, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 51)), "BUSCAR", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 204))); // NOI18N

        txtNro_doc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNro_docKeyPressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel1.setText("NRO DOCUMENTO");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel2.setText("HOJA DE RUTA");

        txtHoja_Ruta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtHoja_RutaKeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel3.setText("REMITENTE");

        txtRemitente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRemitenteKeyPressed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel4.setText("AREA REMITENTE");

        txtAreaRemite.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAreaRemiteKeyPressed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel5.setText("ASUNTO");

        txtAsunto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAsuntoKeyPressed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton1.setText("LIMPIAR");
        jButton1.setToolTipText("LIMPIAR CAJAS DE TEXTO");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 51)));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONOS/actualizar.png"))); // NOI18N
        jButton2.setToolTipText("ACTUALIZAR");
        jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 51)));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(204, 0, 0));
        jButton3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("SALIR");
        jButton3.setToolTipText("SALIR DE LA VENTANA");
        jButton3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 51)));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtNro_doc, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHoja_Ruta, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(txtRemitente, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAreaRemite, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtAsunto, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNro_doc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(txtHoja_Ruta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAreaRemite, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(txtRemitente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtAsunto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(6, 6, 6))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 204)), "DOCUMENTO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 153, 204))); // NOI18N

        tblDocumento.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblDocumento.getTableHeader().setReorderingAllowed(false);
        tblDocumento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDocumentoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDocumento);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)), "MOVIMIENTOS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(204, 0, 0))); // NOI18N

        jScrollPane2.setViewportView(tblDetalle_doc);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tblDocumentoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDocumentoMouseClicked
        int fila= tblDocumento.getSelectedRow();
        if(fila>=0){
            TablaDetalle(String.valueOf(tblDocumento.getValueAt(fila, 0)));
        }
        else{
            JOptionPane.showMessageDialog(null, "NO HA SELECCIOANDO NINGUNA FILA .!!!");
        }
    }//GEN-LAST:event_tblDocumentoMouseClicked

    private void txtNro_docKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNro_docKeyPressed
//        String nro;
//        if(txtNro_doc.getText().length()>0){
//            nro=txtNro_doc.getText();
//            TablaDocumento(nro, "", null, null, null);
//        }
        
         TableRowSorter sorter = new TableRowSorter<>(tblDocumento.getModel());
               RowFilter<DefaultTableModel, Object> rf;
               //If current expression doesn't parse, don't update.
               try {
                   rf = RowFilter.regexFilter(txtNro_doc.getText().toUpperCase(), 2);
               } catch (java.util.regex.PatternSyntaxException e) {
                   return;
               }
                sorter.setRowFilter(rf);
       tblDocumento.setRowSorter(sorter);
    }//GEN-LAST:event_txtNro_docKeyPressed

    private void txtHoja_RutaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHoja_RutaKeyPressed
//        String hr;
//        if(txtHoja_Ruta.getText().length()>0){
//            hr=txtHoja_Ruta.getText();
//            TablaDocumento(null, hr, null, null, null);
//        }
        
         TableRowSorter sorter = new TableRowSorter<>(tblDocumento.getModel());
               RowFilter<DefaultTableModel, Object> rf;
               //If current expression doesn't parse, don't update.
               try {
                   rf = RowFilter.regexFilter(txtHoja_Ruta.getText().toUpperCase(), 3);
               } catch (java.util.regex.PatternSyntaxException e) {
                   return;
               }
                sorter.setRowFilter(rf);
       tblDocumento.setRowSorter(sorter);
    }//GEN-LAST:event_txtHoja_RutaKeyPressed

    private void txtRemitenteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRemitenteKeyPressed
//        String rem;
//        if(txtRemitente.getText().length()>0){
//            rem=txtRemitente.getText();
//            TablaDocumento(null, null, rem, null, null);
//        }
        TableRowSorter sorter = new TableRowSorter<>(tblDocumento.getModel());
               RowFilter<DefaultTableModel, Object> rf;
               //If current expression doesn't parse, don't update.
               try {
                   rf = RowFilter.regexFilter(txtRemitente.getText().toUpperCase(), 6);
               } catch (java.util.regex.PatternSyntaxException e) {
                   return;
               }
                sorter.setRowFilter(rf);
       tblDocumento.setRowSorter(sorter);
    }//GEN-LAST:event_txtRemitenteKeyPressed

    private void txtAreaRemiteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAreaRemiteKeyPressed
//       String remA;
//        if(txtAreaRemite.getText().length()>0){
//            remA=txtAreaRemite.getText();
//            TablaDocumento(null, null, null, remA, null);
//        }
         TableRowSorter sorter = new TableRowSorter<>(tblDocumento.getModel());
               RowFilter<DefaultTableModel, Object> rf;
               //If current expression doesn't parse, don't update.
               try {
                   rf = RowFilter.regexFilter(txtAreaRemite.getText().toUpperCase(), 5);
               } catch (java.util.regex.PatternSyntaxException e) {
                   return;
               }
                sorter.setRowFilter(rf);
       tblDocumento.setRowSorter(sorter);
    }//GEN-LAST:event_txtAreaRemiteKeyPressed

    private void txtAsuntoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAsuntoKeyPressed
//        String asunto;
//        if(txtAsunto.getText().length()>0){
//            asunto=txtAsunto.getText();
//            TablaDocumento(null, null, null, null, asunto);
//        }
        TableRowSorter sorter = new TableRowSorter<>(tblDocumento.getModel());
               RowFilter<DefaultTableModel, Object> rf;
               //If current expression doesn't parse, don't update.
               try {
                   rf = RowFilter.regexFilter(txtAsunto.getText().toUpperCase(), 7);
               } catch (java.util.regex.PatternSyntaxException e) {
                   return;
               }
                sorter.setRowFilter(rf);
       tblDocumento.setRowSorter(sorter);
       
    }//GEN-LAST:event_txtAsuntoKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        txtAreaRemite.setText("");
        txtAsunto.setText("");
        txtHoja_Ruta.setText("");
        txtNro_doc.setText("");
        txtRemitente.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        TablaDocumento("", "", "", "", "");
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(CONUSLTA_DOC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CONUSLTA_DOC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CONUSLTA_DOC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CONUSLTA_DOC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                CONUSLTA_DOC dialog = new CONUSLTA_DOC(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private org.edisoncor.gui.panel.Panel panel1;
    private javax.swing.JTable tblDetalle_doc;
    private javax.swing.JTable tblDocumento;
    private javax.swing.JTextField txtAreaRemite;
    private javax.swing.JTextField txtAsunto;
    private javax.swing.JTextField txtHoja_Ruta;
    private javax.swing.JTextField txtNro_doc;
    private javax.swing.JTextField txtRemitente;
    // End of variables declaration//GEN-END:variables
    String Usuario, Dependencia;

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String Usuario) {
        this.Usuario = Usuario;
    }

    public String getDependencia() {
        return Dependencia;
    }

    public void setDependencia(String Dependencia) {
        this.Dependencia = Dependencia;
    }
    
}
