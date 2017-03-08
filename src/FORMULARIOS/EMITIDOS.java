/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIOS;

import CONEXION.CONEXION;
import FECHA.FECHA;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SONY
 */
public final class EMITIDOS extends javax.swing.JDialog {
    CONEXION mysql = new CONEXION();
    Connection cn = mysql.Conectar();
    DefaultTableModel tabla_Emitidos;
    FECHA f = new FECHA();
    String idemitidos;
    String Anuevo;
    boolean b;
    
    /**
     * Creates new form EMITIDOS
     */
    public EMITIDOS(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        TablaEmitidos("", "", "", "");
        Combos();
        HI(false);
        this.setLocationRelativeTo(null);
        btnGuardar.setEnabled(false);
        btnCancelar.setEnabled(false);
        dcFecha.setDate(f.Fecha());
        txtDirigido.setEnabled(false);
        txtLugar.setEnabled(false);
        txtNro.setEnabled(false);
        txtOtroLugar.setEnabled(false);
        txtDep.setEnabled(false);
        ordetable();
    }
    public void ordetable(){
        tblEmitidos.getColumnModel().getColumn(0).setPreferredWidth(20);
        tblEmitidos.getColumnModel().getColumn(1).setPreferredWidth(60);
        tblEmitidos.getColumnModel().getColumn(2).setPreferredWidth(50);
        tblEmitidos.getColumnModel().getColumn(3).setPreferredWidth(80);
        tblEmitidos.getColumnModel().getColumn(4).setPreferredWidth(100);
        tblEmitidos.getColumnModel().getColumn(5).setPreferredWidth(100);
        tblEmitidos.getColumnModel().getColumn(6).setPreferredWidth(200);
        tblEmitidos.getColumnModel().getColumn(7).setPreferredWidth(400);
    }
    public void TablaEmitidos(String nro, String dirigido, String lugar,String dependencia){
        String titulos [] = {"ID", "TIPO_DOC","NRO_DOC", "FECHA_E","DIRIGIDO","LUGAR", 
            "DEPENDENCIA","ASUNTO"};
        String registro [] = new String[8];
        String sSQL="select de.iddoc, tdoc.tipo, CONVERT(de.nro_doc, UNSIGNED INTEGER) AS nro, "
                + "de.fecha, de.dirigido_a, de.lugar, de.dependencia, de.asunto "
                + "from documento de inner join tipo_doc tdoc on "
                + "de.idtipo_doc=tdoc.idtipo_doc where de.nro_doc "
                + "like '%"+nro+"%' AND idactividad=2 AND DATE_FORMAT(fecha,'%Y') = DATE_FORMAT(NOW(),'%Y') "
                + "OR de.dirigido_a like '%"+dirigido+"%' AND idactividad=2 AND DATE_FORMAT(fecha,'%Y') = DATE_FORMAT(NOW(),'%Y') "
                + "OR de.lugar like '%"+lugar+"%' AND idactividad=2 AND DATE_FORMAT(fecha,'%Y') = DATE_FORMAT(NOW(),'%Y') "
                + "OR de.dependencia like '%"+dependencia+"%' AND idactividad=2  AND DATE_FORMAT(fecha,'%Y') = DATE_FORMAT(NOW(),'%Y')"
                + "order by nro asc";
        tabla_Emitidos = new DefaultTableModel(null,titulos);
        
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            while(rs.next()){
                registro[0]=rs.getString(1);
                registro[1]=rs.getString(2);
                registro[2]=rs.getString(3);
                registro[3]=rs.getString(4);
                registro[4]=rs.getString(5);
                registro[5]=rs.getString(6);
                registro[6]=rs.getString(7);
                registro[7]=rs.getString(8);
                tabla_Emitidos.addRow(registro);
            }
            tblEmitidos.setModel(tabla_Emitidos);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
    }
    public void EditarEmitidos(String id){
        String sSQL="select nro_doc, fecha, asunto, dirigido_a, "
                + "dependencia, lugar ,idtipo_doc from documento where iddoc="+id;
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            while(rs.next()){
                txtnroDoc.setText(rs.getString(1));
                dcFecha.setDate(Date.valueOf(rs.getString(2)));
                txaAsunto.setText(rs.getString(3));
                txtDirigido_A.setText(rs.getString(4));
                txtDependencia.setText(rs.getString(5));
                cmbProv.setSelectedItem(rs.getString(6));
                cmbTipoDoc.setSelectedIndex(rs.getInt(7)-1);
            }
            idemitidos=id;
        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    public void HI(boolean a){
    cmbTipoDoc.setEnabled(a);
    txtnroDoc.setEnabled(a);
    dcFecha.setEnabled(a);
    txtDirigido_A.setEnabled(a);
    txaAsunto.setEnabled(a);
    cmbDepart.setEnabled(a);
    cmbProv.setEnabled(a);
    txtDirigido_A.setText("");
    txtnroDoc.setText("");
    txaAsunto.setText("");
    txtDependencia.setEnabled(a);
    txtDependencia.setText("");
    btnGuardar.setEnabled(a);
    btnCancelar.setEnabled(a);
    
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
        jmModificar = new javax.swing.JMenuItem();
        panel1 = new org.edisoncor.gui.panel.Panel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cmbTipoDoc = new javax.swing.JComboBox();
        txtnroDoc = new javax.swing.JTextField();
        cmbDepart = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        txaAsunto = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        dcFecha = new com.toedter.calendar.JDateChooser();
        txtDirigido_A = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cmbProv = new javax.swing.JComboBox();
        txtDependencia = new javax.swing.JTextField();
        cbOLugar = new javax.swing.JCheckBox();
        txtOtroLugar = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEmitidos = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        cbDirigido = new javax.swing.JCheckBox();
        cbDependencia = new javax.swing.JCheckBox();
        cbLugar = new javax.swing.JCheckBox();
        cbNroDoc = new javax.swing.JCheckBox();
        btnBuscar = new javax.swing.JButton();
        txtDirigido = new javax.swing.JTextField();
        txtDep = new javax.swing.JTextField();
        txtLugar = new javax.swing.JTextField();
        txtNro = new javax.swing.JTextField();

        jmModificar.setText("MODIFICAR");
        jmModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmModificarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jmModificar);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("EMITIDOS");
        setResizable(false);

        panel1.setColorPrimario(new java.awt.Color(255, 255, 255));
        panel1.setColorSecundario(new java.awt.Color(153, 204, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0)), "REGISTRO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 153))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel1.setText("TIPO DOCUMENTO");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel2.setText("NRO DOCUMENTO");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel3.setText("FECHA DE EMISIÓN");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel4.setText("DEPENDENCIA");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel5.setText("DIRIGIDO A");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel6.setText("LUGAR");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel7.setText("ASUNTO");

        txtnroDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnroDocActionPerformed(evt);
            }
        });

        cmbDepart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDepartActionPerformed(evt);
            }
        });

        txaAsunto.setColumns(20);
        txaAsunto.setLineWrap(true);
        txaAsunto.setRows(5);
        txaAsunto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txaAsuntoKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(txaAsunto);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        btnNuevo.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btnNuevo.setText("NUEVO");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnGuardar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCancelar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btnCancelar.setText("CANCELAR");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnSalir.setBackground(new java.awt.Color(255, 0, 51));
        btnSalir.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btnSalir.setText("SALIR");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 3, Short.MAX_VALUE))
                    .addComponent(btnSalir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        txtDirigido_A.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDirigido_AActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel8.setText("DEPARTAMENTO");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel9.setText("PROVINCIA");

        txtDependencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDependenciaActionPerformed(evt);
            }
        });

        cbOLugar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        cbOLugar.setText("OTRO LUGAR");
        cbOLugar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbOLugarActionPerformed(evt);
            }
        });

        jButton1.setText("BUSCAR OTROS DOCUMENTOS");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbTipoDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtnroDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDependencia, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane2)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dcFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDirigido_A, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel8)
                                    .addComponent(cbOLugar))
                                .addGap(35, 35, 35)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(cmbDepart, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbProv, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtOtroLugar, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(dcFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDirigido_A, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(cmbTipoDoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtnroDoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(txtDependencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6)))
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cbOLugar)
                                    .addComponent(txtOtroLugar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(cmbDepart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cmbProv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0)), "DOCUMENTOS REGISTRADOS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 204))); // NOI18N

        tblEmitidos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblEmitidos.setComponentPopupMenu(jPopupMenu1);
        tblEmitidos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(tblEmitidos);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(153, 0, 51)), "BUSCAR DOCUMENTOS EMITIDOS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 204))); // NOI18N

        cbDirigido.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        cbDirigido.setText("DIRIGIDO A");
        cbDirigido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbDirigidoActionPerformed(evt);
            }
        });

        cbDependencia.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        cbDependencia.setText("DEPENDENCIA");
        cbDependencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbDependenciaActionPerformed(evt);
            }
        });

        cbLugar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        cbLugar.setText("LUGAR");
        cbLugar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbLugarActionPerformed(evt);
            }
        });

        cbNroDoc.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        cbNroDoc.setText("NRO DOC");
        cbNroDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbNroDocActionPerformed(evt);
            }
        });

        btnBuscar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btnBuscar.setText("BUSCAR");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbDirigido)
                    .addComponent(cbDependencia))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtDep, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                    .addComponent(txtDirigido))
                .addGap(1, 1, 1)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(cbNroDoc))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(cbLugar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtLugar, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(txtNro))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbDirigido)
                            .addComponent(txtDirigido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbLugar)
                            .addComponent(txtLugar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbDependencia)
                            .addComponent(txtDep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbNroDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 2, Short.MAX_VALUE))
                    .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        b=true;
        HI(b);
        Anuevo="NUEVO";
        btnGuardar.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnNuevo.setEnabled(false);
        txtnroDoc.requestFocus();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        b=false;
        HI(b);
        btnGuardar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnNuevo.setEnabled(true);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void cmbDepartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDepartActionPerformed
        cmbProv.removeAllItems();
        String sSQL="select provincia from provincia where "
                + "iddepartamento = "+String.valueOf(cmbDepart.getSelectedIndex()+1);
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            while(rs.next()){
                cmbProv.addItem(rs.getString(1));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
    }//GEN-LAST:event_cmbDepartActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
       String sSQL="", msj;
       switch (Anuevo) {
           case "NUEVO":
               sSQL="INSERT INTO documento(NRO_DOC, FECHA, "
                       + "DIRIGIDO_A, LUGAR, DEPENDENCIA,ASUNTO, ESTADO,USUARIO,"
                       + "IDTIPO_DOC,IDACTIVIDAD)"
                       + "VALUES(?,?,?,?,?,?,?,?,?,?)";
               msj="DOCUMENTO AGREGADO";
               break;
           case "MODIFICAR":
               sSQL="UPDATE documento SET NRO_DOC = ?, FECHA = ?, DIRIGIDO_A = ?,"
                       + "LUGAR = ?, DEPENDENCIA = ?,ASUNTO = ?, ESTADO=?,USUARIO=?, "
                       + "IDTIPO_DOC = ?, IDACTIVIDAD = ? "
                       + "WHERE IDDOC="+idemitidos;
               msj="DOCUMENTO MODIFICADO";
               break;
           default:
               msj="ERROR";
               break;
       }
       SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
       try {
           PreparedStatement pst = cn.prepareStatement(sSQL);
           pst.setString(1, txtnroDoc.getText().toUpperCase());
           pst.setString(2, sdf.format(dcFecha.getDate()));
           pst.setString(3, txtDirigido_A.getText().toUpperCase());
           if(txtOtroLugar.isEnabled()){
               pst.setString(4, txtOtroLugar.getText().toUpperCase());
           }
           else{
               pst.setString(4, cmbProv.getSelectedItem().toString().toUpperCase());
           }
           pst.setString(5, txtDependencia.getText().toUpperCase());
           pst.setString(6, txaAsunto.getText().toUpperCase());
           pst.setString(7, "ENVIADO");
           pst.setString(8, this.getUsuario());
           pst.setString(9, String.valueOf(cmbTipoDoc.getSelectedIndex()+1));
           pst.setString(10, "2");
           int n = pst.executeUpdate();
           if(n>0){
               JOptionPane.showMessageDialog(null, msj);
               TablaEmitidos("", "", "", "");
               HI(true);
               cbOLugar.setSelected(false);
               txtOtroLugar.setEnabled(false);
               txtOtroLugar.setText("");
               Anuevo="NUEVO";
               ordetable();
           }
       } catch (SQLException | HeadlessException e) {
           JOptionPane.showMessageDialog(null, e);
       }
        
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void jmModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmModificarActionPerformed
        int filasel;
        String id;
        HI(true);
        try {     
            filasel = tblEmitidos.getSelectedRow();
           if(filasel == -1){
               JOptionPane.showMessageDialog(null, "no se a seleccionado niguna fila");
           }
           else{
               btnGuardar.setEnabled(true);
               btnCancelar.setEnabled(true);
               tabla_Emitidos = (DefaultTableModel) tblEmitidos.getModel();
               id = (String) tabla_Emitidos.getValueAt(filasel, 0);
               this.EditarEmitidos(id);
               Anuevo="MODIFICAR";
               idemitidos=id;
           }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jmModificarActionPerformed

    private void cbDirigidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbDirigidoActionPerformed
        if(cbDirigido.isSelected()){
            txtDirigido.setEnabled(true);
        }
        else{
            txtDirigido.setEnabled(false);
            txtDirigido.setText("");
        }
        
    }//GEN-LAST:event_cbDirigidoActionPerformed

    private void cbDependenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbDependenciaActionPerformed
        if(cbDependencia.isSelected()){
            txtDep.setEnabled(true);
        }
        else{
            txtDep.setEnabled(false);
            txtDep.setText("");
        }
    }//GEN-LAST:event_cbDependenciaActionPerformed

    private void cbLugarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbLugarActionPerformed
        if(cbLugar.isSelected()){
            txtLugar.setEnabled(true);
        }
        else{
            txtLugar.setEnabled(false);
            txtLugar.setText("");
        }
    }//GEN-LAST:event_cbLugarActionPerformed

    private void cbNroDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbNroDocActionPerformed
        if(cbNroDoc.isSelected()){
            txtNro.setEnabled(true);
        }
        else{
            txtNro.setEnabled(false);
            txtNro.setText("");
        }
    }//GEN-LAST:event_cbNroDocActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String dirigido,nrdoc,dependencia,lugar;
        if(txtDirigido.isEnabled()==true){  
            dirigido=txtDirigido.getText();
        }
        else{dirigido=null;}
        if( txtNro.isEnabled()==true){
            nrdoc=txtNro.getText();
        } else{
            nrdoc=null;
        }
        if(txtDep.isEnabled()==true) {
            dependencia=txtDep.getText();
        }else{dependencia=null;}
        if(txtLugar.isEnabled()==true){
            lugar=txtLugar.getText();
        } else{
            lugar=null;
        }
        TablaEmitidos(nrdoc, dirigido, lugar, dependencia);
        ordetable();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void cbOLugarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbOLugarActionPerformed
        if(cbOLugar.isSelected()){
            txtOtroLugar.setEnabled(true);
            cmbDepart.setEnabled(false);
            cmbProv.setEnabled(false);
        }
        else{
            txtOtroLugar.setEnabled(false);
            cmbDepart.setEnabled(true);
            cmbProv.setEnabled(true);
            
        }
    }//GEN-LAST:event_cbOLugarActionPerformed

    private void txtnroDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnroDocActionPerformed
        txtnroDoc.transferFocus();
    }//GEN-LAST:event_txtnroDocActionPerformed

    private void txtDirigido_AActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDirigido_AActionPerformed
        txtDirigido_A.transferFocus();
    }//GEN-LAST:event_txtDirigido_AActionPerformed

    private void txtDependenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDependenciaActionPerformed
        txtDependencia.transferFocus();
    }//GEN-LAST:event_txtDependenciaActionPerformed

    private void txaAsuntoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txaAsuntoKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
           txaAsunto.transferFocus();
        }
        if(evt.getKeyCode()==KeyEvent.VK_TAB){
            txaAsunto.transferFocus();
        }
    }//GEN-LAST:event_txaAsuntoKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new BUSCAR_EMITIDOS(null, true).setVisible(true);
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EMITIDOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                EMITIDOS dialog = new EMITIDOS(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JCheckBox cbDependencia;
    private javax.swing.JCheckBox cbDirigido;
    private javax.swing.JCheckBox cbLugar;
    private javax.swing.JCheckBox cbNroDoc;
    private javax.swing.JCheckBox cbOLugar;
    private javax.swing.JComboBox cmbDepart;
    private javax.swing.JComboBox cmbProv;
    private javax.swing.JComboBox cmbTipoDoc;
    private com.toedter.calendar.JDateChooser dcFecha;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenuItem jmModificar;
    private org.edisoncor.gui.panel.Panel panel1;
    private javax.swing.JTable tblEmitidos;
    private javax.swing.JTextArea txaAsunto;
    private javax.swing.JTextField txtDep;
    private javax.swing.JTextField txtDependencia;
    private javax.swing.JTextField txtDirigido;
    private javax.swing.JTextField txtDirigido_A;
    private javax.swing.JTextField txtLugar;
    private javax.swing.JTextField txtNro;
    private javax.swing.JTextField txtOtroLugar;
    private javax.swing.JTextField txtnroDoc;
    // End of variables declaration//GEN-END:variables
    public void Combos(){
        String sSQL2="select tipo from tipo_doc";     
        String sSQL3="select departamento from departamento";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL2);
            while(rs.next()){
                cmbTipoDoc.addItem(rs.getString(1));                 
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL3);
            while(rs.next()){
                cmbDepart.addItem(rs.getString(1));                 
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    String Usuario;
    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String Usuario) {
        this.Usuario = Usuario;
    }
}
