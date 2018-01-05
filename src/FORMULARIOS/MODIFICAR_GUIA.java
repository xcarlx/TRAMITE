/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIOS;

import CONEXION.CONEXION;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CARLOS
 */
public final class MODIFICAR_GUIA extends javax.swing.JDialog {
    DefaultTableModel tabla_model;
    Statement st;
    ResultSet rs;
    PreparedStatement pst;
    Connection con;
    CONEXION mysql = new CONEXION();
    String idguia;
    public String getIdtipo_guia() {
        return idtipo_guia;
    }

    public String getIdguia() {
        return idguia;
    }

    public void setIdguia(String idguia) {
        this.idguia = idguia;
    }

    public void setIdtipo_guia(String idtipo_guia) {
        this.idtipo_guia = idtipo_guia;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }
    
    String idtipo_guia;
    /**
     * Creates new form MODIFICAR_GUIA
     */
    public MODIFICAR_GUIA(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        con = mysql.Conectar();
        Combo();
        this.setLocationRelativeTo(null);
    }
    public void tabla1(){
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(50);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(300);
    }
    public void tabla2(){
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(50);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(300);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(500);
    }
    public void TablaGuiaLima(String nrog, String fechai, String fechaf){
        String sql = "select g.IDGUIA, tg.tipo, g.FECHA_ENVIO, g.NROGUIA, dep.DEPENDENCIA from guia g " +
                "inner join tipo_guia tg on g.IDTIPO_GUIA = tg.IDTIPO_GUIA " +
                "inner join dependencias dep on g.IDDEPENDENCIA = dep.IDDEPENDENCIA "
                + "WHERE g.IDTIPO_GUIA = 1 and g.nroguia='"+nrog+"' and g.estado = 'ENVIADO' "
                + "OR g.IDTIPO_GUIA = 1 and  g.FECHA_ENVIO >= '"+fechai+"' and g.FECHA_ENVIO <= '"+fechaf+"' "
                + "and g.estado = 'ENVIADO'";
        String titulos [] = {"ID", "TIPO", "FECHA", "NRO_GUIA", "DEPENDENCIA"};
        tabla_model = new DefaultTableModel(null, titulos);
        String registro [] = new String[5];
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                registro[0] = rs.getString(1);
                registro[1] = rs.getString(2);
                registro[2] = rs.getString(3);
                registro[3] = rs.getString(4);
                registro[4] = rs.getString(5);
                tabla_model.addRow(registro);
            }
            jTable1.setModel(tabla_model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR \n"+e.toString());
        }
        tabla1();
    }
    public void TablaGuiaProvincia(String nrog, String fechai, String fechaf, String depen, String responsable){
        String sql = "select g.IDGUIA, tg.tipo, g.FECHA_ENVIO, g.NROGUIA, g.responsable,g.provincia,"
                + "dep.DEPENDENCIA from guia g " +
                "inner join tipo_guia tg on g.IDTIPO_GUIA = tg.IDTIPO_GUIA " +
                "inner join dependencias dep on g.IDDEPENDENCIA = dep.IDDEPENDENCIA "
                + "WHERE g.IDTIPO_GUIA = 2 and g.nroguia='"+nrog+"' and g.estado = 'ENVIADO' "
                + "OR g.IDTIPO_GUIA = 2 and  g.FECHA_ENVIO >= '"+fechai+"' "
                + "and g.FECHA_ENVIO <= '"+fechaf+"' and g.estado = 'ENVIADO'"
                + "OR  g.IDTIPO_GUIA = 2 and g.responsable LIKE '%"+responsable+"%' and g.estado = 'ENVIADO'"
                + "OR dep.dependencia = '"+depen+"' "
                + "and g.estado = 'ENVIADO'";
        String titulos [] = {"ID", "TIPO", "FECHA", "NRO_GUIA", "RESPONSABLE","PROVINCIA","DEPENDENCIA"};
        tabla_model = new DefaultTableModel(null, titulos);
        String registro [] = new String[7];
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                registro[0] = rs.getString(1);
                registro[1] = rs.getString(2);
                registro[2] = rs.getString(3);
                registro[3] = rs.getString(4);
                registro[4] = rs.getString(5);
                registro[5] = rs.getString(6);
                registro[6] = rs.getString(7);
                tabla_model.addRow(registro);
            }
            jTable1.setModel(tabla_model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR \n"+e.toString());
        }
        tabla2();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        panel1 = new org.edisoncor.gui.panel.Panel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNroGuia = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtResponsable = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cmbProv = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        cmbDep = new javax.swing.JComboBox();
        btnBuscar = new javax.swing.JButton();
        dcFecha1 = new com.toedter.calendar.JDateChooser();
        dcFecha2 = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnBuscar1 = new javax.swing.JButton();

        jMenuItem1.setText("MODIFICAR GUIA");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panel1.setColorPrimario(new java.awt.Color(0, 102, 153));
        panel1.setColorSecundario(new java.awt.Color(0, 102, 102));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.setComponentPopupMenu(jPopupMenu1);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setForeground(new java.awt.Color(0, 51, 204));
        jLabel1.setText("NRO DE GUIA");

        txtNroGuia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNroGuiaActionPerformed(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(0, 51, 204));
        jLabel2.setText("RESPONSABLE");

        txtResponsable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtResponsableKeyPressed(evt);
            }
        });

        jLabel4.setForeground(new java.awt.Color(0, 51, 204));
        jLabel4.setText("PROVINCIA");

        cmbProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbProvActionPerformed(evt);
            }
        });

        jLabel5.setForeground(new java.awt.Color(0, 51, 204));
        jLabel5.setText("DEPENDENCIA");

        btnBuscar.setText("Bucar por Rango");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jLabel3.setForeground(new java.awt.Color(0, 51, 204));
        jLabel3.setText("FECHA_ INICIO");

        jLabel6.setForeground(new java.awt.Color(0, 51, 204));
        jLabel6.setText("FECHA_ FIN");

        btnBuscar1.setText("Bucar por dependencia");
        btnBuscar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtResponsable)
                        .addComponent(txtNroGuia, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(dcFecha1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(dcFecha2, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(btnBuscar))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(cmbProv, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscar1))
                    .addComponent(cmbDep, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNroGuia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(cmbProv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtResponsable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(cmbDep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dcFecha2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBuscar)
                    .addComponent(dcFecha1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void txtNroGuiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNroGuiaActionPerformed
        switch (getIdtipo_guia()) {
            case "1":
                TablaGuiaLima(txtNroGuia.getText(), null, null);
                break;
            case "2":
                TablaGuiaProvincia(txtNroGuia.getText(), null, null, null, null);
                break;
        }
        
    }//GEN-LAST:event_txtNroGuiaActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(dcFecha1.getDate()!=null && dcFecha2.getDate()!=null){ 
            switch (this.getIdtipo_guia()) {
                case "1":
                    TablaGuiaLima(null, sdf.format(dcFecha1.getDate()), sdf.format(dcFecha2.getDate()));
                    break;
                case "2":
                    TablaGuiaProvincia(null, sdf.format(dcFecha1.getDate()),
                            sdf.format(dcFecha2.getDate()), null, null);
                    break;
            }
        }
    }//GEN-LAST:event_btnBuscarActionPerformed
    boolean estado = false;

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        int fila = jTable1.getSelectedRow();
        String id, nro;
        if(fila != -1){
            id = jTable1.getValueAt(fila, 0).toString();
            idguia=id;
            nro = jTable1.getValueAt(fila, 3).toString();
            
            int jo = JOptionPane.showConfirmDialog(null, "DECEA MODIFICAR EL NRO DE GUIA "+nro);
            if(jo==0){
                String sql="UPDATE guia set ESTADO = ? WHERE idguia="+id+" "
                        + "and idtipo_guia ="+getIdtipo_guia()+"";
                try {
                    pst = con.prepareStatement(sql);
                    pst.setString(1, "ACTIVO");
                    int i = pst.executeUpdate();
                    if(i>0){
                        JOptionPane.showMessageDialog(null, "GUIA ACTIVADA PARA MODIFICAR");
                        this.dispose();
                        setEstado(true);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Existe un ERROR");
                    }
                    
                } catch (SQLException | HeadlessException e) {
                    JOptionPane.showMessageDialog(null, "Error \n"+e);
                }                
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Error");
        }
        
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void txtResponsableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtResponsableKeyPressed
        TablaGuiaProvincia(null, null, null, null, txtResponsable.getText());
    }//GEN-LAST:event_txtResponsableKeyPressed

    private void cmbProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbProvActionPerformed
        cmbDep.removeAllItems();
        String sSQL="select dependencia from dependencias "
                + "where iddepartamento=6 and idprovincia="+(cmbProv.getSelectedIndex()+1);
        try {
            Statement st1 = con.createStatement();
            ResultSet rs1 = st1.executeQuery(sSQL);
            while(rs1.next()){
                cmbDep.addItem(rs1.getString(1));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_cmbProvActionPerformed

    private void btnBuscar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscar1ActionPerformed
        TablaGuiaProvincia(null, null, null, cmbDep.getSelectedItem().toString(), null);
    }//GEN-LAST:event_btnBuscar1ActionPerformed

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MODIFICAR_GUIA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                MODIFICAR_GUIA dialog = new MODIFICAR_GUIA(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnBuscar;
    public javax.swing.JButton btnBuscar1;
    public javax.swing.JComboBox cmbDep;
    public javax.swing.JComboBox cmbProv;
    private com.toedter.calendar.JDateChooser dcFecha1;
    private com.toedter.calendar.JDateChooser dcFecha2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private org.edisoncor.gui.panel.Panel panel1;
    private javax.swing.JTextField txtNroGuia;
    public javax.swing.JTextField txtResponsable;
    // End of variables declaration//GEN-END:variables
 public void Combo(){
        String sSQL2="select provincia from provincia "
                + "where iddepartamento=6";
        try {
            st = con.createStatement();
            rs = st.executeQuery(sSQL2);
            while(rs.next()){
                cmbProv.addItem(rs.getString(1));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
