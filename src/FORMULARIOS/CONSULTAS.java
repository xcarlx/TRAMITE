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
 * @author CARLOS RUIZ
 */
public final class CONSULTAS extends javax.swing.JDialog {
    DefaultTableModel Tabla_modelo;
    CONEXION mysql = new CONEXION();
    Connection cn = mysql.Conectar();
    Statement st;
    ResultSet rs;
    PreparedStatement pst;
    /**
     * Creates new form CONSULTAS
     */
    public CONSULTAS() {
        initComponents();
        TablaIngresos(null, null, null, null,"");
        TablaEmitidos(null, null, null, null);
        TablaGuiaL(null, null, null);
        TablaGuiaP(null, null, null);
        this.setLocationRelativeTo(null);
        HI1();
        HI2();
        HI3();
        HI4();
        cmbEstado.addItem("ATENDIDO");
        cmbEstado.addItem("PENDIENTE");
        cmbEstado.addItem("RECIBIDO");
        cmbEstado.addItem("RECEPCIONADO");
        cmbEstado.addItem("REGISTRADO");
        cmbEstadoGL.addItem("ACTIVO");
        cmbEstadoGL.addItem("ENVIADO");
        cmbEstadoGP.addItem("ACTIVO");
        cmbEstadoGP.addItem("ENVIADO");
    }
    public void HI1(){
        txtHruta.setEnabled(false);
        txtNrodoc.setEnabled(false);
        cmbEstado.setEnabled(false);
        dcFecha.setEnabled(false);
        txtHruta.setText(null);
        txtNrodoc.setText(null);
        dcFecha.setDate(null);
        txtAsunto.setEnabled(false);
        txtAsunto.setText(null);
    }
    public void HI2(){
        txtNroDocEmi.setText("");
        txtNroDocEmi.setEnabled(false);
        txtLugar.setText("");
        txtLugar.setEnabled(false);
        dcFechaEmi.setDate(null);
        dcFechaEmi.setEnabled(false);
        txtDirigido.setText("");
        txtDirigido.setEnabled(false);
    }
    public void HI3(){
        txtNroGL.setText("");
        txtNroGL.setEnabled(false);
        dcFechaGL.setDate(null);
        dcFechaGL.setEnabled(false);
        cmbEstadoGL.setEnabled(false);
    }
    public void HI4(){
        txtNroGP.setText("");
        txtNroGP.setEnabled(false);
        dcFechaGP.setDate(null);
        dcFechaGP.setEnabled(false);
        cmbEstadoGP.setEnabled(false);
    }
    public void TablaIngresos(String hr, String nro, String fecha, String estado, String asunto){
        String titulos [] = {"ID","TIPO DOC","NRO DOC", " HOJA RUTA", "REMITENTE", "ESTADO","ASUNTO"};
        String registro [] = new String[7];
        Tabla_modelo = new DefaultTableModel(null, titulos);
        String sql="select doc.iddoc,tp.tipo, doc.nro_doc, doc.hoja_ruta, "
                + "doc.remitente, doc.estado, doc.asunto " +
                "from documento doc inner join tipo_doc tp on doc.idtipo_doc = tp.idtipo_doc " +
                "where doc.hoja_ruta = '"+hr+"' and doc.idactividad=1 " +
                "OR doc.nro_doc = '"+nro+"' and doc.idactividad=1 " +
                "OR doc.fecha = '"+fecha+"' and doc.idactividad=1 " +
                "OR doc.estado = '"+estado+"' and doc.idactividad=1 "+
                "OR doc.asunto like '%"+asunto+"%' and doc.idactividad=1";
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
                Tabla_modelo.addRow(registro);
            }
            tblDocIngreso.setModel(Tabla_modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "ERROR AL CARGAR LA TABLA DOCUMENTO DE INGRESOS \n"+e);
        }
    }
    public void TablaDetalleIngresos(String iddoc){
        String titulos [] = {"FECHA","DEPEN_ORIGEN", "DEPEN_DESTINO", "ASIGANDO", "ESTADO"};
        String registro [] = new String[5];
        Tabla_modelo = new DefaultTableModel(null, titulos);
        String sql="select d.FECHA, dp1.dependencia, dp2.dependencia, d.ASIGNADO, d.ESTADO "
                + "from DERIVAR d inner join DEPENDENCIAS dp1 on d.iddependencia_o=dp1.iddependencia "
                + "inner join DEPENDENCIAS dp2 on d.iddependencia_d = dp2.iddependencia "
                + "where d.iddoc="+iddoc;
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                registro[0]=rs.getString(1);
                registro[1]=rs.getString(2);
                registro[2]=rs.getString(3);
                registro[3]=rs.getString(4);
                registro[4]=rs.getString(5);
                Tabla_modelo.addRow(registro);
            }
            tblDetalleIngresos.setModel(Tabla_modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "ERROR AL CARGAR LA TABLA DETALLE DEL DOCUMENTO DE INGRESOS\n"+e);
        }
    }
    public void TablaEmitidos(String nro, String lugar, String dirigido, String fecha){
        String titulos [] = {"ID","TIPO DOC","NRO DOC", "FECHA", "DIRIGIDO", "LUGAR","DEPENDENCIA"};
        String registro [] = new String[7];
        Tabla_modelo = new DefaultTableModel(null, titulos);
        String sql="select iddoc,tp.tipo, doc.nro_doc, doc.fecha, doc.dirigido_a, doc.lugar ,doc.dependencia"
                + " from documento doc inner join tipo_doc tp on doc.idtipo_doc=tp.idtipo_doc"
                + " where doc.nro_doc like '%"+nro+"%' and doc.idactividad=2"
                + " or doc.lugar like '%"+lugar+"%' and doc.idactividad=2"
                + " or doc.dirigido_a like '%"+dirigido+"%' and doc.idactividad=2"
                + " or doc.fecha = '"+fecha+"' and doc.idactividad=2";
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
                Tabla_modelo.addRow(registro);
            }
            tblEmitidos.setModel(Tabla_modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "ERROR AL CARGAR LA TABLA DOCUMENTO DE EMITIDOS \n"+e);
        }
    }
    public void TablaGuiaL(String idguia, String fecha, String estado){
        String titulos [] = {"ID_GUIA", "FECHA", "ENVIADOR","DEPENDENCIA","ESTADO"};
        String registro [] = new String[5];
        Tabla_modelo = new DefaultTableModel(null, titulos);
        String sql="select idguia,fecha_envio, enviador, dependencia,estado "
                + "from guia g inner join dependencias dep on g.iddependencia=dep.iddependencia "
                + "where idguia like '"+idguia+"' and idtipo_guia=1 "
                + "or fecha_envio = '"+fecha+"' and idtipo_guia=1 "
                + "or estado like '"+estado+"' and idtipo_guia=1";
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                registro[0]=rs.getString(1);
                registro[1]=rs.getString(2);
                registro[2]=rs.getString(3);
                registro[3]=rs.getString(4);
                registro[4]=rs.getString(5);
                Tabla_modelo.addRow(registro);
            }
            tblGuiaL.setModel(Tabla_modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "ERROR AL CARGAR LA TABLA GUIA LIMA  \n"+e);
        }
    }
    public void TablaGuiaLDetalle(String idguia){
        String titulos [] = {"TIPO","NRO_DOC", "DEPEN_ORIGEN", "CODIGO","DESTINO"};
        String registro [] = new String[5];
        Tabla_modelo = new DefaultTableModel(null, titulos);
        String sql="select tipo, nro_doc, dependencia_o, codigo, destino "
                + "from guia_detalle gd inner join tipo_doc tp on gd.idtipo_doc=tp.idtipo_doc "
                + "where idtipo_guia=1 and idguia="+idguia;
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                registro[0]=rs.getString(1);
                registro[1]=rs.getString(2);
                registro[2]=rs.getString(3);
                registro[3]=rs.getString(4);
                registro[4]=rs.getString(5);
                Tabla_modelo.addRow(registro);
            }
            tblGuiaLDetalle.setModel(Tabla_modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "ERROR AL CARGAR LA TABLA DETALLE GUIA LIMA \n"+e);
        }
    }
    public void TablaGuiaP(String idguia, String fecha, String estado){
        String titulos [] = {"ID_GUIA", "FECHA", "RESPONSABLE","DEPENDENCIA","PROVINCIA","ESTADO"};
        String registro [] = new String[6];
        Tabla_modelo = new DefaultTableModel(null, titulos);
        String sql="select idguia,fecha_envio, responsable, dependencia,provincia,estado "
                + "from guia g inner join dependencias dep on g.iddependencia=dep.iddependencia "
                + "where idguia like '"+idguia+"' and idtipo_guia=2 "
                + "or fecha_envio = '"+fecha+"' and idtipo_guia=2 "
                + "or estado like '"+estado+"' and idtipo_guia=2";
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
                Tabla_modelo.addRow(registro);
            }
            tblGuaP.setModel(Tabla_modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "ERROR AL CARGAR LA TABLA GUIA PROVINCIA \n"+e);
        }
    }
    public void TablaGuiaPDetalle(String idguia){
        String titulos [] = {"NRO","TIPO","NRO_DOC", "DEPEN_ORIGEN"};
        String registro [] = new String[4];
        Tabla_modelo = new DefaultTableModel(null, titulos);
        String sql="select gd.idguia_d,tp.tipo, gd.nro_doc, gd.dependencia_o "
                + "from guia_detalle gd inner join tipo_doc tp on gd.idtipo_doc=tp.idtipo_doc "
                + "where idtipo_guia=2 and gd.idguia="+idguia;
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                registro[0]=rs.getString(1);
                registro[1]=rs.getString(2);
                registro[2]=rs.getString(3);
                registro[3]=rs.getString(4);
                Tabla_modelo.addRow(registro);
            }
            tblGuiaPDetalle.setModel(Tabla_modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "ERROR AL CARGAR LA TABLA DETALLE GUIA PROVINCIA \n"+e);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        panel1 = new org.edisoncor.gui.panel.Panel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        txtNrodoc = new javax.swing.JTextField();
        btnBuscarIngresos = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        rbHR = new javax.swing.JRadioButton();
        rbND = new javax.swing.JRadioButton();
        rbF = new javax.swing.JRadioButton();
        dcFecha = new com.toedter.calendar.JDateChooser();
        txtHruta = new javax.swing.JTextField();
        rbEs = new javax.swing.JRadioButton();
        cmbEstado = new javax.swing.JComboBox();
        rbAsunto = new javax.swing.JRadioButton();
        txtAsunto = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDocIngreso = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDetalleIngresos = new javax.swing.JTable();
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel6 = new javax.swing.JPanel();
        rbNroDocEm = new javax.swing.JRadioButton();
        rbLugarEmi = new javax.swing.JRadioButton();
        rbDirigido = new javax.swing.JRadioButton();
        txtNroDocEmi = new javax.swing.JTextField();
        rbFechaEmi = new javax.swing.JRadioButton();
        txtLugar = new javax.swing.JTextField();
        txtDirigido = new javax.swing.JTextField();
        dcFechaEmi = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblEmitidos = new javax.swing.JTable();
        jSplitPane3 = new javax.swing.JSplitPane();
        jPanel8 = new javax.swing.JPanel();
        rbNroGL = new javax.swing.JRadioButton();
        rbFehcaGL = new javax.swing.JRadioButton();
        rbEstadoGL = new javax.swing.JRadioButton();
        txtNroGL = new javax.swing.JTextField();
        cmbEstadoGL = new javax.swing.JComboBox();
        dcFechaGL = new com.toedter.calendar.JDateChooser();
        jButton8 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblGuiaL = new javax.swing.JTable();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblGuiaLDetalle = new javax.swing.JTable();
        jSplitPane4 = new javax.swing.JSplitPane();
        jPanel15 = new javax.swing.JPanel();
        rbNroGP = new javax.swing.JRadioButton();
        rbFechaGP = new javax.swing.JRadioButton();
        rbEstadoGP = new javax.swing.JRadioButton();
        txtNroGP = new javax.swing.JTextField();
        cmbEstadoGP = new javax.swing.JComboBox();
        dcFechaGP = new com.toedter.calendar.JDateChooser();
        jButton11 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblGuaP = new javax.swing.JTable();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblGuiaPDetalle = new javax.swing.JTable();

        jMenuItem1.setText("ANULAR DOCUMENTO");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CONSULTAS");
        setResizable(false);

        panel1.setColorPrimario(new java.awt.Color(255, 255, 255));
        panel1.setColorSecundario(new java.awt.Color(153, 204, 255));

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        jTabbedPane1.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 51)), "BUSCAR", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 204))); // NOI18N

        btnBuscarIngresos.setText("BUSCAR");
        btnBuscarIngresos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarIngresosActionPerformed(evt);
            }
        });

        jButton3.setText("ACTUALIZAR");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbHR);
        rbHR.setText("HOJA_RUTA");
        rbHR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbHRActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbND);
        rbND.setText("NRO DOC");
        rbND.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbNDActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbF);
        rbF.setText("FECHA");
        rbF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbFActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbEs);
        rbEs.setText("ESTADO");
        rbEs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbEsActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbAsunto);
        rbAsunto.setText("ASUNTO");
        rbAsunto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbAsuntoActionPerformed(evt);
            }
        });

        txtAsunto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAsuntoKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbND)
                    .addComponent(rbF)
                    .addComponent(rbHR))
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dcFecha, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                    .addComponent(txtNrodoc)
                    .addComponent(txtHruta))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(rbEs)
                        .addGap(2, 2, 2)
                        .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(rbAsunto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAsunto, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscarIngresos, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnBuscarIngresos, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtHruta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rbEs)))
                            .addComponent(rbHR))
                        .addGap(3, 3, 3)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNrodoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rbND)
                            .addComponent(rbAsunto)
                            .addComponent(txtAsunto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbF)
                            .addComponent(dcFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSplitPane1.setLeftComponent(jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0)), "DOCUMENTO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Times New Roman", 3, 12), new java.awt.Color(0, 51, 255))); // NOI18N

        tblDocIngreso.setComponentPopupMenu(jPopupMenu1);
        tblDocIngreso.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDocIngresoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDocIngreso);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 777, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 51)), "MOVIMIENTO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 153, 204))); // NOI18N

        jScrollPane2.setViewportView(tblDetalleIngresos);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane1.setRightComponent(jPanel3);

        jTabbedPane1.addTab("RECIBIDOS", jSplitPane1);

        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        buttonGroup2.add(rbNroDocEm);
        rbNroDocEm.setText("NRO DOC");
        rbNroDocEm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbNroDocEmActionPerformed(evt);
            }
        });

        buttonGroup2.add(rbLugarEmi);
        rbLugarEmi.setText("LUGAR");
        rbLugarEmi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbLugarEmiActionPerformed(evt);
            }
        });

        buttonGroup2.add(rbDirigido);
        rbDirigido.setText("DIRIGIDO A");
        rbDirigido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbDirigidoActionPerformed(evt);
            }
        });

        buttonGroup2.add(rbFechaEmi);
        rbFechaEmi.setText("FECHA");
        rbFechaEmi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbFechaEmiActionPerformed(evt);
            }
        });

        jButton1.setText("BUSCAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton5.setText("ACTUALIZAR");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbNroDocEm)
                            .addComponent(rbLugarEmi))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtLugar, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                            .addComponent(txtNroDocEmi)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(rbDirigido)
                        .addGap(30, 30, 30)
                        .addComponent(txtDirigido, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dcFechaEmi, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbFechaEmi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(146, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rbNroDocEm)
                            .addComponent(txtNroDocEmi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rbFechaEmi))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(rbLugarEmi)
                                .addComponent(txtLugar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(dcFechaEmi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rbDirigido)
                            .addComponent(txtDirigido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSplitPane2.setLeftComponent(jPanel6);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255)), "EMITIDOS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Times New Roman", 1, 14), new java.awt.Color(255, 0, 0))); // NOI18N

        jScrollPane3.setViewportView(tblEmitidos);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 777, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane2.setRightComponent(jPanel7);

        jTabbedPane1.addTab("EMITIDOS", jSplitPane2);

        jSplitPane3.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        buttonGroup3.add(rbNroGL);
        rbNroGL.setText("NRO GUIA");
        rbNroGL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbNroGLActionPerformed(evt);
            }
        });

        buttonGroup3.add(rbFehcaGL);
        rbFehcaGL.setText("FECHA");
        rbFehcaGL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbFehcaGLActionPerformed(evt);
            }
        });

        buttonGroup3.add(rbEstadoGL);
        rbEstadoGL.setText("ESTADO");
        rbEstadoGL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbEstadoGLActionPerformed(evt);
            }
        });

        jButton8.setText("ACTUALIZAR");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton10.setText("BUSCAR");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbEstadoGL)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbNroGL)
                            .addComponent(rbFehcaGL))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmbEstadoGL, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNroGL)
                            .addComponent(dcFechaGL, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 168, Short.MAX_VALUE)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(171, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                        .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rbNroGL)
                            .addComponent(txtNroGL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbFehcaGL)
                            .addComponent(dcFechaGL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rbEstadoGL)
                            .addComponent(cmbEstadoGL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jSplitPane3.setLeftComponent(jPanel8);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)), "GUIAS LIMA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Times New Roman", 3, 14), new java.awt.Color(51, 51, 255))); // NOI18N

        tblGuiaL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGuiaLMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblGuiaL);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 777, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
        );

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 204)), "DETALLE", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Times New Roman", 3, 14), new java.awt.Color(255, 102, 0))); // NOI18N

        jScrollPane5.setViewportView(tblGuiaLDetalle);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane3.setRightComponent(jPanel9);

        jTabbedPane1.addTab("GUIAS LIMA", jSplitPane3);

        jSplitPane4.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));

        buttonGroup4.add(rbNroGP);
        rbNroGP.setText("NRO GUIA");
        rbNroGP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbNroGPActionPerformed(evt);
            }
        });

        buttonGroup4.add(rbFechaGP);
        rbFechaGP.setText("FECHA");
        rbFechaGP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbFechaGPActionPerformed(evt);
            }
        });

        buttonGroup4.add(rbEstadoGP);
        rbEstadoGP.setText("ESTADO");
        rbEstadoGP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbEstadoGPActionPerformed(evt);
            }
        });

        jButton11.setText("ACTUALIZAR");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton13.setText("BUSCAR");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbEstadoGP)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbNroGP)
                            .addComponent(rbFechaGP))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmbEstadoGP, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNroGP)
                            .addComponent(dcFechaGP, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 168, Short.MAX_VALUE)
                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(171, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                        .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rbNroGP)
                            .addComponent(txtNroGP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbFechaGP)
                            .addComponent(dcFechaGP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rbEstadoGP)
                            .addComponent(cmbEstadoGP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jSplitPane4.setLeftComponent(jPanel15);

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 51)), "GUIAS PROVINCIA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Times New Roman", 3, 14), new java.awt.Color(0, 0, 255))); // NOI18N

        tblGuaP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGuaPMouseClicked(evt);
            }
        });
        tblGuaP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblGuaPKeyPressed(evt);
            }
        });
        jScrollPane6.setViewportView(tblGuaP);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 777, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
        );

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204)), "DETALLE", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Times New Roman", 3, 14), new java.awt.Color(153, 153, 255))); // NOI18N

        tblGuiaPDetalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane7.setViewportView(tblGuiaPDetalle);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane4.setRightComponent(jPanel16);

        jTabbedPane1.addTab("GUIAS PROVINCIA", jSplitPane4);

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
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

    private void tblDocIngresoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDocIngresoMouseClicked
       int filasel;
       String id;
       try {
           filasel = tblDocIngreso.getSelectedRow();
           if(filasel == -1){
               JOptionPane.showMessageDialog(null, "no se a seleccionado niguna fila");
           }
           else{
               Tabla_modelo = (DefaultTableModel) tblDocIngreso.getModel();
               id = (String) Tabla_modelo.getValueAt(filasel, 0);
               TablaDetalleIngresos(id);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_tblDocIngresoMouseClicked

    private void tblGuiaLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGuiaLMouseClicked
       int filasel;
       String id;
       try {
           filasel = tblGuiaL.getSelectedRow();
           if(filasel == -1){
               JOptionPane.showMessageDialog(null, "no se a seleccionado niguna fila");
           }
           else{
               Tabla_modelo = (DefaultTableModel) tblGuiaL.getModel();
               id = (String) Tabla_modelo.getValueAt(filasel, 0);
               TablaGuiaLDetalle(id);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_tblGuiaLMouseClicked

    private void tblGuaPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblGuaPKeyPressed
       
    }//GEN-LAST:event_tblGuaPKeyPressed

    private void tblGuaPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGuaPMouseClicked
       int filasel;
       String id;
       try {
           filasel = tblGuaP.getSelectedRow();
           if(filasel == -1){
               JOptionPane.showMessageDialog(null, "no se a seleccionado niguna fila");
           }
           else{
               Tabla_modelo = (DefaultTableModel) tblGuaP.getModel();
               id = (String) Tabla_modelo.getValueAt(filasel, 0);
               TablaGuiaPDetalle(id);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_tblGuaPMouseClicked

    private void rbEsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbEsActionPerformed
        HI1();
        cmbEstado.setEnabled(true);
    }//GEN-LAST:event_rbEsActionPerformed

    private void btnBuscarIngresosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarIngresosActionPerformed
        String hr,nro,fecha = null,estado, asunto;
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(txtHruta.isEnabled()){
            hr=txtHruta.getText();
        }else {hr=null;}
        if(txtNrodoc.isEnabled()){
            nro=txtNrodoc.getText();
        }else {nro=null;}
        if(dcFecha.isEnabled()){
            if(dcFecha.getDate()==null){
                JOptionPane.showMessageDialog(rootPane, "ERROR NO HA SELECCIONADO LA FECHA");
            }else{
                fecha=sdf.format(dcFecha.getDate());
            }
        }else {fecha=null;}
        if(cmbEstado.isEnabled()){
            estado=(String) cmbEstado.getSelectedItem();
        }else {estado=null;}
        if(txtAsunto.isEnabled()){
            asunto=txtAsunto.getText();
        }else{asunto=null;}
        TablaIngresos(hr, nro, fecha, estado, asunto);
    }//GEN-LAST:event_btnBuscarIngresosActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        TablaIngresos(null, null, null, null, null);
        REPORTE_GRAFICO1 re = new REPORTE_GRAFICO1(null, true);
        re.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String nro, lugar, dirigido, fecha = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(txtNroDocEmi.isEnabled()){
            nro=txtNroDocEmi.getText();
        }else{nro=null;}
        if(txtLugar.isEnabled()){
            lugar=txtLugar.getText();
        }else{lugar=null;}
        if(txtDirigido.isEnabled()){
            dirigido=txtDirigido.getText();
        }else{dirigido=null;}
        if(dcFechaEmi.isEnabled()){
            if(dcFechaEmi.getDate()==null){
                JOptionPane.showMessageDialog(rootPane, "ERROR NO HA SELECCIONADO LA FECHA");
            }else{
                fecha=sdf.format(dcFechaEmi.getDate());
            }
        }else{fecha=null;}
        TablaEmitidos(nro, lugar, dirigido, fecha);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void rbNroDocEmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbNroDocEmActionPerformed
        if(rbNroDocEm.isSelected()){
            HI2();
            txtNroDocEmi.setEnabled(true);
        }
    }//GEN-LAST:event_rbNroDocEmActionPerformed

    private void rbLugarEmiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbLugarEmiActionPerformed
        if(rbLugarEmi.isSelected()){
            HI2();
            txtLugar.setEnabled(true);
        }
    }//GEN-LAST:event_rbLugarEmiActionPerformed

    private void rbDirigidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbDirigidoActionPerformed
        if(rbDirigido.isSelected()){
            HI2();
            txtDirigido.setEnabled(true);
        }
    }//GEN-LAST:event_rbDirigidoActionPerformed

    private void rbFechaEmiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbFechaEmiActionPerformed
        if(rbFechaEmi.isSelected()){
            HI2();
            dcFechaEmi.setEnabled(true);
        }
    }//GEN-LAST:event_rbFechaEmiActionPerformed

    private void rbNroGLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbNroGLActionPerformed
        if(rbNroGL.isSelected()){
            HI3();
            txtNroGL.setEnabled(true);
        }
    }//GEN-LAST:event_rbNroGLActionPerformed

    private void rbFehcaGLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbFehcaGLActionPerformed
        if(rbFehcaGL.isSelected()){
            HI3();
            dcFechaGL.setEnabled(true);
        }
    }//GEN-LAST:event_rbFehcaGLActionPerformed

    private void rbEstadoGLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbEstadoGLActionPerformed
        if(rbEstadoGL.isSelected()){
            HI3();
            cmbEstadoGL.setEnabled(true);
        }
    }//GEN-LAST:event_rbEstadoGLActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        String idguia, fecha = null, estado;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        if(txtNroGL.isEnabled()){
            idguia=txtNroGL.getText();
        }else{idguia=null;}
        if(dcFechaGL.isEnabled()){
            if(dcFechaGL.getDate()==null){
                JOptionPane.showMessageDialog(rootPane, "ERROR NO HA SELECCIONADO LA FECHA");
            }else{
                fecha=sdf.format(dcFechaGL.getDate());
            }
        }else{fecha=null;}
        if(cmbEstadoGL.isEnabled()){
            estado=cmbEstadoGL.getSelectedItem().toString();
        }else{estado=null;}
        TablaGuiaL(idguia, fecha, estado);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void rbNroGPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbNroGPActionPerformed
        if(rbNroGP.isSelected()){
            HI4();
            txtNroGP.setEnabled(true);
        }
    }//GEN-LAST:event_rbNroGPActionPerformed

    private void rbFechaGPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbFechaGPActionPerformed
        if(rbFechaGP.isSelected()){
            HI4();
            dcFechaGP.setEnabled(true);
        }
    }//GEN-LAST:event_rbFechaGPActionPerformed

    private void rbEstadoGPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbEstadoGPActionPerformed
        if(rbEstadoGP.isSelected()){
            HI4();
            cmbEstadoGP.setEnabled(true);
        }
    }//GEN-LAST:event_rbEstadoGPActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        String idguia, fecha = null, estado;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        if(txtNroGP.isEnabled()){
            idguia=txtNroGP.getText();
        }else{idguia=null;}
        if(dcFechaGP.isEnabled()){
            if(dcFechaGP.getDate()==null){
                JOptionPane.showMessageDialog(rootPane, "ERROR NO HA SELECCIONADO LA FECHA");
            }else{
                fecha=sdf.format(dcFechaGP.getDate());
            }
        }else{fecha=null;}
        if(cmbEstadoGP.isEnabled()){
            estado=cmbEstadoGP.getSelectedItem().toString();
        }else{estado=null;}
        TablaGuiaP(idguia, fecha, estado);
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        TablaGuiaP(null, null, null);
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        TablaGuiaL(null, null, null);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        TablaEmitidos(null, null, null, null);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void rbFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbFActionPerformed
        if(rbF.isSelected()){
            HI1();
            dcFecha.setEnabled(true);
        }
    }//GEN-LAST:event_rbFActionPerformed

    private void rbNDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbNDActionPerformed
        if(rbND.isSelected()){
            HI1();
            txtNrodoc.setEnabled(true);
        }
    }//GEN-LAST:event_rbNDActionPerformed

    private void rbHRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbHRActionPerformed
        if(rbHR.isSelected()){
            HI1();
            txtHruta.setEnabled(true);
        }
    }//GEN-LAST:event_rbHRActionPerformed

    private void rbAsuntoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbAsuntoActionPerformed
        if(rbAsunto.isSelected()){
            HI1();
            txtAsunto.setEnabled(true);
        }
    }//GEN-LAST:event_rbAsuntoActionPerformed

    private void txtAsuntoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAsuntoKeyTyped
        String asunto = null;
        if(txtAsunto.isEnabled()){
            asunto=txtAsunto.getText();
        }
        TablaIngresos(null, null, null, null, asunto);
    }//GEN-LAST:event_txtAsuntoKeyTyped

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        int fila = this.tblDocIngreso.getSelectedRow();
        String id;
        if(fila >= 0){
            Tabla_modelo = (DefaultTableModel) tblDocIngreso.getModel();
            id = (String) Tabla_modelo.getValueAt(fila, 0);
            int i= JOptionPane.showConfirmDialog(null, "ESTA SEGURO DE ANULAR EL DOCUMENTO", "ALERTA", 2);
            if(i==0){
                String sql="UPDATE documento set estado = ?, observaciones = ? where iddoc="+id;
                try {
                    pst = cn.prepareStatement(sql);
                    pst.setString(1, "ANULADO");
                    pst.setString(2, JOptionPane.showInputDialog("REGISTRE MOTIVO DE ANULACION"));
                    pst.executeUpdate();
                } catch (SQLException | HeadlessException e) {
                    JOptionPane.showMessageDialog(null, "ERROR AL EJECUTAR LA CONSULTA \n "+e, "ERROR", 0);   
                }
                JOptionPane.showMessageDialog(null, "DOCUMENTO ANULADO !!!");
            }
            else{
                JOptionPane.showMessageDialog(null, "NO SE HA ANULADO EL DOCUMENTO");
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "NO HA SELECCIONADO UNA FILA", "ERROR", 0);
        }
        
        
    }//GEN-LAST:event_jMenuItem1ActionPerformed

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
            java.util.logging.Logger.getLogger(CONSULTAS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CONSULTAS().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscarIngresos;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.JComboBox cmbEstado;
    private javax.swing.JComboBox cmbEstadoGL;
    private javax.swing.JComboBox cmbEstadoGP;
    private com.toedter.calendar.JDateChooser dcFecha;
    private com.toedter.calendar.JDateChooser dcFechaEmi;
    private com.toedter.calendar.JDateChooser dcFechaGL;
    private com.toedter.calendar.JDateChooser dcFechaGP;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton8;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JSplitPane jSplitPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private org.edisoncor.gui.panel.Panel panel1;
    private javax.swing.JRadioButton rbAsunto;
    private javax.swing.JRadioButton rbDirigido;
    private javax.swing.JRadioButton rbEs;
    private javax.swing.JRadioButton rbEstadoGL;
    private javax.swing.JRadioButton rbEstadoGP;
    private javax.swing.JRadioButton rbF;
    private javax.swing.JRadioButton rbFechaEmi;
    private javax.swing.JRadioButton rbFechaGP;
    private javax.swing.JRadioButton rbFehcaGL;
    private javax.swing.JRadioButton rbHR;
    private javax.swing.JRadioButton rbLugarEmi;
    private javax.swing.JRadioButton rbND;
    private javax.swing.JRadioButton rbNroDocEm;
    private javax.swing.JRadioButton rbNroGL;
    private javax.swing.JRadioButton rbNroGP;
    private javax.swing.JTable tblDetalleIngresos;
    private javax.swing.JTable tblDocIngreso;
    private javax.swing.JTable tblEmitidos;
    private javax.swing.JTable tblGuaP;
    private javax.swing.JTable tblGuiaL;
    private javax.swing.JTable tblGuiaLDetalle;
    private javax.swing.JTable tblGuiaPDetalle;
    private javax.swing.JTextField txtAsunto;
    private javax.swing.JTextField txtDirigido;
    private javax.swing.JTextField txtHruta;
    private javax.swing.JTextField txtLugar;
    private javax.swing.JTextField txtNroDocEmi;
    private javax.swing.JTextField txtNroGL;
    private javax.swing.JTextField txtNroGP;
    private javax.swing.JTextField txtNrodoc;
    // End of variables declaration//GEN-END:variables
}
