/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIOS;

import CLASSREPORT.DOC_DERI;
import CONEXION.CONEXION;
import FECHA.FECHA;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
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
public final class REGISTRAR extends javax.swing.JDialog {
    DefaultTableModel tabla_model;
    CONEXION mysql = new CONEXION();
    Connection cn = mysql.Conectar();
    Statement st;
    ResultSet rs;
    PreparedStatement pst;
    String Anuevo;
    String iddoc1;
    FECHA f = new FECHA();

    /**
     * Creates new form REGISTRAR
     */
    public REGISTRAR(java.awt.Frame parent, boolean modal) {
        initComponents();
        this.setLocationRelativeTo(null);
        HI(false);
        Combo();
    }
    public void HI(boolean a){
        cmbDependencia.setEnabled(a);
        cmbAsignar.setEnabled(a);
        cmbTipo_doc.setEnabled(a);
        txtOtroAsignar.setEnabled(a);
        dcFecha.setEnabled(a);
        txtNroDoc.setEnabled(a);
        txtRemitente.setEnabled(a);
        txtAreaRemite.setEnabled(a);
        spFolios.setEnabled(a);
        txaAsunto.setEnabled(a);
        txaObservaciones.setEnabled(a);
        btnCancelar.setEnabled(a);
        btnGuardar.setEnabled(a);   
    }
    public void Limpiar(){
        txtNroDoc.setText("");
        txtRemitente.setText("");
        txtAreaRemite.setText("");
        spFolios.setValue(1);
        txaAsunto.setText("");
        txaObservaciones.setText("");
    }
    private void OrderTabla(){
        tblDocumento.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblDocumento.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblDocumento.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblDocumento.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblDocumento.getColumnModel().getColumn(4).setPreferredWidth(200);
        tblDocumento.getColumnModel().getColumn(5).setPreferredWidth(200);
        tblDocumento.getColumnModel().getColumn(6).setPreferredWidth(100);
        tblDocumento.getColumnModel().getColumn(7).setPreferredWidth(200);
    }
    public void TablaDocumento(String nrodoc, String hr, String remitente){
        String sql = "select doc.iddoc, td.tipo, doc.nro_doc, doc.hoja_ruta, doc.remitente"
                + ", doc.area_r, doc.fecha,dep.dependencia from documento doc inner join "
                + "derivar dr on doc.iddoc=dr.iddoc inner join dependencias dep "
                + "on dr.iddependencia_d = dep.iddependencia inner join tipo_doc td "
                + "on td.idtipo_doc = doc.idtipo_doc "
                + "where doc.usuario = '"+this.getUsuario()+"' and doc.estado like 'PENDIENTE' and doc.nro_doc like '"+nrodoc+"' "
                + "OR doc.usuario = '"+this.getUsuario()+"' and doc.estado like 'PENDIENTE' and doc.hoja_ruta like '"+hr+"' "
                + "OR doc.usuario = '"+this.getUsuario()+"' and doc.estado like 'PENDIENTE' and doc.remitente like '%"+remitente+"%'"
                + "group by doc.iddoc";
        String titulos [] = {"ID","TIPO DOC","NRO DOC","HOJA RUTA","REMITENTE", "AREA REMITE", "FECHA",
                             "DEPENDENCIA DESTINO"};
        tabla_model = new DefaultTableModel(null, titulos);
        String registro [] = new String[8];
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
                tabla_model.addRow(registro);
            }
            tblDocumento.setModel(tabla_model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR \n"+e, "ERROR", 0);
        }
        OrderTabla();
    }
    public void Editar(String id){
        String sql = "select td.tipo, doc.fecha, doc.nro_doc, doc.remitente"
                + ", doc.area_r, doc.folios, dep.dependencia, dr.asignado, doc.asunto, "
                + "doc.observaciones from documento doc inner join "
                + "derivar dr on doc.iddoc=dr.iddoc inner join dependencias dep "
                + "on dr.iddependencia_d = dep.iddependencia inner join tipo_doc td "
                + "on td.idtipo_doc = doc.idtipo_doc where doc.iddoc ="+id+" group by doc.iddoc";
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                cmbTipo_doc.setSelectedItem(rs.getString(1));
                dcFecha.setDate(rs.getDate(2));
                txtNroDoc.setText(rs.getString(3));
                txtRemitente.setText(rs.getString(4));
                txtAreaRemite.setText(rs.getString(5));
                spFolios.setValue(rs.getInt(6));
                cmbDependencia.setSelectedItem(rs.getString(7));
                cmbAsignar.setSelectedItem(rs.getString(8));
                txaAsunto.setText(rs.getString(9));
                txaObservaciones.setText(rs.getString(10));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR AL CARGAR DATOS \n"+e, "ERROR", 0);
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        miEditar = new javax.swing.JMenuItem();
        miImprimir = new javax.swing.JMenuItem();
        panel1 = new org.edisoncor.gui.panel.Panel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cmbDependencia = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        cmbAsignar = new javax.swing.JComboBox();
        txtOtroAsignar = new javax.swing.JTextField();
        cbOtro = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        cmbTipo_doc = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        txtNroDoc = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        dcFecha = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        txtRemitente = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtAreaRemite = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        spFolios = new javax.swing.JSpinner();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaAsunto = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txaObservaciones = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblDocumento = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        txtBuscarNroDoc = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtBuscarHR = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtBuscarRem = new javax.swing.JTextField();
        btnActualizar = new javax.swing.JButton();

        miEditar.setText("MODIFICAR");
        miEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miEditarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(miEditar);

        miImprimir.setText("IMPRIMIR");
        miImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miImprimirActionPerformed(evt);
            }
        });
        jPopupMenu1.add(miImprimir);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("REGISTRO DE DOCUMENTOS");
        setResizable(false);

        panel1.setColorPrimario(new java.awt.Color(255, 255, 255));
        panel1.setColorSecundario(new java.awt.Color(153, 204, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)), "DEPENDENCIA A DERIVAR", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 153))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel2.setText("DEPENDENCIA");

        cmbDependencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDependenciaActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel3.setText("ASIGNAR A");

        cbOtro.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        cbOtro.setText("OTRO");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(cbOtro))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbDependencia, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbAsignar, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtOtroAsignar, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbDependencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cmbAsignar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOtroAsignar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbOtro))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)), "DATOS DEL DOCUMENTO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 204))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel4.setText("TIPO DOCUMENTO");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel5.setText("NRO DOCUMENTO");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel1.setText("FECHA DE REGISTRO");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel6.setText("REMITENTE");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel7.setText("AREA REMITE");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel8.setText("FOLIOS");

        spFolios.setValue(1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtNroDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dcFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtRemitente, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmbTipo_doc, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtAreaRemite, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spFolios, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cmbTipo_doc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dcFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtNroDoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtRemitente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtAreaRemite, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(spFolios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)), "ASUNTO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 204))); // NOI18N

        txaAsunto.setColumns(20);
        txaAsunto.setLineWrap(true);
        txaAsunto.setRows(5);
        txaAsunto.setWrapStyleWord(true);
        txaAsunto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txaAsuntoKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(txaAsunto);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)), "OBSERVACIONES", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 204))); // NOI18N

        txaObservaciones.setColumns(20);
        txaObservaciones.setLineWrap(true);
        txaObservaciones.setRows(5);
        txaObservaciones.setWrapStyleWord(true);
        txaObservaciones.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txaObservacionesKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(txaObservaciones);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)), "BOTONES", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 204))); // NOI18N

        btnNuevo.setBackground(new java.awt.Color(204, 204, 204));
        btnNuevo.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btnNuevo.setText("NUEVO");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnGuardar.setBackground(new java.awt.Color(204, 204, 204));
        btnGuardar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCancelar.setBackground(new java.awt.Color(204, 204, 204));
        btnCancelar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btnCancelar.setText("CANCELAR");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnSalir.setBackground(new java.awt.Color(204, 51, 0));
        btnSalir.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btnSalir.setText("SALIR");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalir)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        tblDocumento.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblDocumento.setComponentPopupMenu(jPopupMenu1);
        jScrollPane3.setViewportView(tblDocumento);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 51, 0)), "BUSCAR", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 153, 0))); // NOI18N

        txtBuscarNroDoc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarNroDocKeyPressed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel9.setText("NRO DOC");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel10.setText("HOJA DE RUTA");

        txtBuscarHR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarHRKeyPressed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel11.setText("REMITENTE");

        txtBuscarRem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarRemKeyPressed(evt);
            }
        });

        btnActualizar.setBackground(new java.awt.Color(255, 255, 255));
        btnActualizar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btnActualizar.setForeground(new java.awt.Color(51, 153, 0));
        btnActualizar.setText("ACTUALIZAR");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtBuscarNroDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtBuscarHR, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBuscarRem, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarNroDoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(txtBuscarHR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtBuscarRem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnActualizar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel1Layout.createSequentialGroup()
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        HI(true);
        btnNuevo.setEnabled(false);
        txtOtroAsignar.setEnabled(false);
        txtNroDoc.requestFocus();
        dcFecha.setDate(f.Fecha());
        Anuevo="NUEVO";
        Limpiar();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        HI(false);
        btnNuevo.setEnabled(true);
        Anuevo="";
        Limpiar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void cmbDependenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDependenciaActionPerformed
        cmbAsignar.removeAllItems();
        String sql = "select Concat(NOMBRES,' ',APELLIDOS) as PERSONA from personal "
                + "where iddependencia = (select iddependencia from dependencias where dependencia "
                + "like '"+cmbDependencia.getSelectedItem()+"')";
        Statement st1;
        ResultSet rs1;
        try {
            st1 = cn.createStatement();
            rs1 = st1.executeQuery(sql);
            while(rs1.next()){
                cmbAsignar.addItem(rs1.getString(1));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "ERROR", 0);
        }
    }//GEN-LAST:event_cmbDependenciaActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        TablaDocumento("", "", "");
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        String sSQL = null, sSQL2 = null,msj;
        if(txtNroDoc.getText().length()>0 && txtRemitente.getText().length()>0 
                && txaObservaciones.getText().length()>0){
            switch (Anuevo){
                case "NUEVO":
                    sSQL="INSERT INTO documento (FECHA, NRO_DOC, HOJA_RUTA, AREA_R, "
                            + "REMITENTE, ASUNTO, OBSERVACIONES, FOLIOS, ESTADO,"
                            + "USUARIO,IDTIPO_DOC, IDACTIVIDAD) VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";
                    sSQL2="INSERT INTO derivar (IDDERIVAR, IDDOC,"
                            + " FECHA,ASIGNADO,ESTADO,USUARIO,IDDEPENDENCIA_O,"
                            + "IDDEPENDENCIA_D) "
                            + "VALUES (?,?,?,?,?,?,?,?);";
                    msj="DOCUEMTO REGISTRADO !!!...";
                    break;
                case "MODIFICAR":
                    sSQL = "UPDATE documento "
                            + "SET fecha = ?,"
                            + "nro_doc = ?,"
                            + "AREA_R = ?,"
                            + "remitente = ?,"
                            + "asunto = ?,"
                            + "observaciones= ?,"
                            + "folios = ?,"
                            + "idtipo_doc = ? "
                            + "where iddoc= "+iddoc1;
                    sSQL2="UPDATE derivar "
                            + "set ASIGNADO= ?,"
                            + "IDDEPENDENCIA_D=?,"
                            + "ESTADO = ?,"
                            + "USUARIO = ?"
                            + "WHERE IDDOC="+iddoc1+" AND IDDERIVAR=?";
                    msj = "DOCUMENTO MODIFICADO !!!! ... ";
                    break;
                default:
                    msj="ERROR";
                    break;
            }
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            String iddoc = null;
            if(Anuevo.equals("NUEVO")){
                try {
                    // HOJA DE RUTA
                    int nro = 0;
                    String hruta;
                    try {

                        String sSQ="select count(iddoc) from documento "
                        + "where DATE_FORMAT(fecha,'%Y') = DATE_FORMAT(NOW(),'%Y')"
                        + " AND IDACTIVIDAD=1;";
                        st = cn.createStatement();
                        rs = st.executeQuery(sSQ);
                        while(rs.next()){
                            nro=Integer.parseInt(rs.getString(1));
                        }
                    } catch (SQLException | NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                    SimpleDateFormat sdf2=new SimpleDateFormat("yyyy");
                    hruta = String.valueOf((nro+1))+"-"+sdf2.format(f.Fecha());
                    //---------------------------------------
                    pst =cn.prepareStatement(sSQL);
                    pst.setString(1, sdf.format(dcFecha.getDate()));
                    pst.setString(2, txtNroDoc.getText());
                    pst.setString(3, hruta);
                    pst.setString(4, txtAreaRemite.getText().toUpperCase());
                    pst.setString(5, txtRemitente.getText().toUpperCase());
                    pst.setString(6, txaAsunto.getText().toUpperCase());
                    pst.setString(7, txaObservaciones.getText().toUpperCase());
                    pst.setString(8, String.valueOf(spFolios.getValue()));
                    pst.setString(9, "PENDIENTE");
                    pst.setString(10, this.getUsuario().toUpperCase());
                    pst.setString(11, String.valueOf(cmbTipo_doc.getSelectedIndex()+1));
                    pst.setString(12, "1");
                    int n = pst.executeUpdate();
                    if(n>0){
                        TablaDocumento("", "", "");
                        //SACAR EL MAXIMO ID DEL DOCUMENTO
                        try {
                            String sSQL3 = "SELECT max(iddoc) from documento";
                            Statement st2 = cn.createStatement();
                            ResultSet rs2 = st2.executeQuery(sSQL3);
                            while(rs2.next()){
                                iddoc=rs2.getString(1);
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e);
                        }
                        //---------------------------------------------
                        //SACAR EL ID MAXIMO DE DERIVAR
                        int idderivar = 0;
                        try {
                            String SQL="SELECT COUNT(dc.IDDOC) FROM DOCUMENTO dc "
                            + "INNER JOIN DERIVAR dr on dc.iddoc=dr.iddoc "
                            + "WHERE dc.iddoc="+iddoc;
                            st = cn.createStatement();
                            rs = st.executeQuery(SQL);
                            while(rs.next()){
                                idderivar=Integer.parseInt(rs.getString(1));
                            }
                        } catch (SQLException | NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, e);
                        }
                        String idderi= String.valueOf(idderivar+1);
                        //-------------------------------------------------------------------
                        String iddepen = null;
                        try {
                            String sql="select iddependencia from dependencias where "
                                    + "dependencia like '"+this.cmbDependencia.getSelectedItem().toString()+"'";
                            st = cn.createStatement();
                            rs = st.executeQuery(sql);
                            while(rs.next()){
                                 iddepen=rs.getString(1);
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(rootPane, "ERROR AL CAPTURAR LA DEPENDENCIA \n"+e);
                        }
                        try {
                            SimpleDateFormat sdf1;
                            sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            pst = cn.prepareStatement(sSQL2);
                            pst.setString(1, idderi);
                            pst.setString(2, iddoc);
                            pst.setString(3, sdf1.format(f.Fecha()));
                            if(cbOtro.isSelected()==false){
                                pst.setString(4, cmbAsignar.getSelectedItem().toString());
                            }else{
                                pst.setString(4, txtOtroAsignar.getText());
                            }
                            pst.setString(5, "INICIO");
                            pst.setString(6, this.getUsuario().toUpperCase());
                            pst.setString(7, this.getDependencia());
                            pst.setString(8, iddepen);
                            int n1 = pst.executeUpdate();
                            if(n1>0){
                                JOptionPane.showMessageDialog(null, msj);
                                TablaDocumento("", "", "");
                                txtOtroAsignar.setEnabled(false);
                                txtNroDoc.requestFocus();
                            }
                        } catch (SQLException | HeadlessException e) {
                            JOptionPane.showMessageDialog(null, e);
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(rootPane, "EL DOCUMENTO NO SE HA REGISTRADO");
                    }
                } catch (HeadlessException | SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
            if(Anuevo.equals("MODIFICAR")){
                try {
                    pst = cn.prepareStatement(sSQL);
                    pst.setString(1, sdf.format(dcFecha.getDate()));
                    pst.setString(2, txtNroDoc.getText().toUpperCase());
                    pst.setString(3, txtAreaRemite.getText().toUpperCase());
                    pst.setString(4, txtRemitente.getText().toUpperCase());
                    pst.setString(5, txaAsunto.getText().toUpperCase());
                    pst.setString(6, txaObservaciones.getText().toUpperCase());
                    pst.setString(7, String.valueOf(spFolios.getValue()));
                    pst.setString(8, String.valueOf(cmbTipo_doc.getSelectedIndex()+1));
                    int n1 = pst.executeUpdate();
                    if(n1>0){
                        String iddr="";
                        try {
                            String sql="Select max(idderivar) from derivar where iddoc="+iddoc1;
                            st = cn.createStatement();
                            rs = st.executeQuery(sql);
                            while(rs.next()){
                                iddr=rs.getString(1);
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(rootPane, "ERROR AL CAPTURAR EL MAXIMO DERIVAR DE LA MODIFICACION \n"+e);
                        }
                        String iddepen = null;
                        try {
                            String sql="select iddependencia from dependencias where "
                                    + "dependencia like '"+this.cmbDependencia.getSelectedItem().toString()+"'";
                            st = cn.createStatement();
                            rs = st.executeQuery(sql);
                            while(rs.next()){
                                 iddepen=rs.getString(1);
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(rootPane, "ERROR AL CAPTURAR LA DEPENDENCIA\n"+e);
                        }
                        try {
                            PreparedStatement pst1 = cn.prepareStatement(sSQL2);
                            if(cbOtro.isSelected()==false){
                                pst1.setString(1, cmbAsignar.getSelectedItem().toString());
                            }else{
                                pst1.setString(1, txtOtroAsignar.getText().toUpperCase());
                            }
                            pst1.setString(2, iddepen);
                            pst1.setString(3, "INICIO");
                            pst1.setString(4, this.getUsuario().toUpperCase());
                            pst1.setString(5, iddr);
                            int n = pst1.executeUpdate();
                            if(n>0){
                                JOptionPane.showMessageDialog(null, msj);
                                TablaDocumento("", "", "");
                                txtOtroAsignar.setEnabled(false);
                                txtOtroAsignar.setText("");
                            }
                        } catch (SQLException | HeadlessException e) {
                            JOptionPane.showMessageDialog(null,e);
                        }
                    }

                } catch (SQLException | HeadlessException e) {
                    JOptionPane.showMessageDialog(null,e);
                }
            }
            Limpiar();
            Anuevo="NUEVO";
        }
        else{
            JOptionPane.showMessageDialog(null, "FALTAN REGISTRAR ALGUNOS CAMPOS", "ERROR", 0);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void miEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miEditarActionPerformed
        int fila = tblDocumento.getSelectedRow();
        String id;
        if(fila>=0){
            id=(String) tblDocumento.getValueAt(fila, 0);
            Editar(id);
            HI(true);
            txtOtroAsignar.setEnabled(false);
            Anuevo="MODIFICAR";
            iddoc1=id;
            btnNuevo.setEnabled(false);
        }
        else{
            JOptionPane.showMessageDialog(null, "SELECCIONES UNA FILA", "ERROR", 0);
        }
    }//GEN-LAST:event_miEditarActionPerformed

    private void txtBuscarNroDocKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarNroDocKeyPressed
        String nro;
        if(txtBuscarNroDoc.getText().length()>0){
            nro=txtBuscarNroDoc.getText();
            TablaDocumento(nro, null, null);
        }
    }//GEN-LAST:event_txtBuscarNroDocKeyPressed

    private void txtBuscarHRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarHRKeyPressed
        String hr;
        if(txtBuscarHR.getText().length()>0){
            hr=txtBuscarHR.getText();
            TablaDocumento(null, hr, null);
        }
    }//GEN-LAST:event_txtBuscarHRKeyPressed

    private void txtBuscarRemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarRemKeyPressed
        String rem;
        if(txtBuscarRem.getText().length()>0){
            rem=txtBuscarRem.getText();
            TablaDocumento(null, null, rem);
        }
    }//GEN-LAST:event_txtBuscarRemKeyPressed

    private void txaAsuntoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txaAsuntoKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
           txaAsunto.transferFocus();
        }
        if(evt.getKeyCode()==KeyEvent.VK_TAB){
            txaAsunto.transferFocus();
        }
    }//GEN-LAST:event_txaAsuntoKeyPressed

    private void txaObservacionesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txaObservacionesKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
           txaObservaciones.transferFocus();
        }
        if(evt.getKeyCode()==KeyEvent.VK_TAB){
            txaObservaciones.transferFocus();
        }
    }//GEN-LAST:event_txaObservacionesKeyPressed

    private void miImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miImprimirActionPerformed
        int filasel;
        String id;
        try {
            filasel = tblDocumento.getSelectedRow();
            if(filasel == -1){
                JOptionPane.showMessageDialog(null, "no se a seleccionado niguna fila");
            }
            else{
                tabla_model = (DefaultTableModel) tblDocumento.getModel();
                id = (String) tabla_model.getValueAt(filasel, 0);
                DOC_DERI dc = new DOC_DERI();
                dc.ejecutarReporte(id);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_miImprimirActionPerformed

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
            java.util.logging.Logger.getLogger(REGISTRAR.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                REGISTRAR dialog = new REGISTRAR(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JCheckBox cbOtro;
    private javax.swing.JComboBox cmbAsignar;
    private javax.swing.JComboBox cmbDependencia;
    private javax.swing.JComboBox cmbTipo_doc;
    private com.toedter.calendar.JDateChooser dcFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JMenuItem miEditar;
    private javax.swing.JMenuItem miImprimir;
    private org.edisoncor.gui.panel.Panel panel1;
    private javax.swing.JSpinner spFolios;
    private javax.swing.JTable tblDocumento;
    private javax.swing.JTextArea txaAsunto;
    private javax.swing.JTextArea txaObservaciones;
    private javax.swing.JTextField txtAreaRemite;
    private javax.swing.JTextField txtBuscarHR;
    private javax.swing.JTextField txtBuscarNroDoc;
    private javax.swing.JTextField txtBuscarRem;
    private javax.swing.JTextField txtNroDoc;
    private javax.swing.JTextField txtOtroAsignar;
    private javax.swing.JTextField txtRemitente;
    // End of variables declaration//GEN-END:variables

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
    String Usuario, Dependencia; 
    
    public void Combo(){
        String sql = "select dependencia from dependencias where idprovincia=2 and iddepartamento=6";
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                this.cmbDependencia.addItem(rs.getString(1));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR AL CARGAR EL COMBO DEPENDENCIA\n"+e, "ERROR", 0);
        }
        String sql1 = "select tipo from tipo_doc";
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql1);
            while(rs.next()){
                this.cmbTipo_doc.addItem(rs.getString(1));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR AL CARGAR EL COMBO DEPENDENCIA\n"+e, "ERROR", 0);
        }
        
    }


}
