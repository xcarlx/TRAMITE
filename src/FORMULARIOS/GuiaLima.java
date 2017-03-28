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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos
 */
public final class GuiaLima extends javax.swing.JDialog {
    DefaultTableModel tabla_modelo;
    PreparedStatement pst;
    ResultSet rs;
    Statement st;
    Connection con;
    CONEXION mysql = new CONEXION();
    String idGuia_d;
    String AgregarNuevo = "";
    /**
     * Creates new form GuiaLima
     */
    public GuiaLima(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        con = mysql.Conectar();
        HI(false);
        try {
            con.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(GuiaLima.class.getName()).log(Level.SEVERE, null, ex);
        }
        Combo();
        if(ValidarGuia()){
            TablaGuiaLima("");
            idguiaActiva();
            btnNuevo.setEnabled(false);
            btnEditar.setEnabled(false);
            HI(true);
            AgregarNuevo = "NUEVO";
            txtNroDocumento.requestFocus();
        }
        
    }
    
    public void NuevaGuia(){
        String sSQL ,msj;

        sSQL="INSERT INTO guia (IDGUIA, IDTIPO_GUIA, FECHA_ENVIO, "
        + "ENVIADOR, ESTADO, IDDEPENDENCIA, NROGUIA) VALUES(?,?,?,?,?,?,?)";

        int idguia = 0;
        try {
            String sql="select count(idguia) from guia where idtipo_guia=1";
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                idguia=rs.getInt(1);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        int nroguia = 0;
        try {
            String sql2="select count(idguia) from guia where idtipo_guia=1 AND "
                    + "DATE_FORMAT(FECHA_ENVIO,'%Y') = DATE_FORMAT(NOW(),'%Y')";
            st = con.createStatement();
            rs = st.executeQuery(sql2);
            while(rs.next()){
                nroguia=rs.getInt(1);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        SimpleDateFormat sdf11=new SimpleDateFormat("yyyy");
        String nro;
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
        if("2013".equals(sdf1.format(new FECHA.FECHA().Fecha()))){
            nro = String.valueOf((nroguia+112))+"-"+sdf11.format(new FECHA.FECHA().Fecha());
        }
        else{
            nro = String.valueOf((nroguia+1))+"-"+sdf11.format(new FECHA.FECHA().Fecha());
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
            dcFecha.setDate(new FECHA.FECHA().Fecha());
            pst=con.prepareStatement(sSQL);
            pst.setString(1, String.valueOf(idguia+1));
            pst.setString(2, "1");
            pst.setString(3, sdf.format(new FECHA.FECHA().Fecha()));
            pst.setString(4, "");
            pst.setString(5, "ACTIVO");
            pst.setString(6, "125");
            pst.setString(7, nro);            
            int n=pst.executeUpdate();
            if(n>0){
               AgregarNuevo = "NUEVO";
               txtNroGuia.setText(nro);
            }
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
    }
    
    public void HI(boolean estado){
        txtDepDestino.setEnabled(estado);
        txtDepOrigen.setEnabled(estado);
        txtDestinatario.setEnabled(estado);
        txtNroDocumento.setEnabled(estado);
        txtNroGuia.setEnabled(estado);
        txtRemitente.setEnabled(estado);
        cmbTipoDoc.setEnabled(estado);
        dcFecha.setEnabled(estado);
        btnGuardar.setEnabled(estado);
        btnGuardar.putClientProperty("JComponent.sizeVariant", "large");
        btnTerminar.setEnabled(estado);
        btnImprimir.setEnabled(estado);
    }
    public void Limpiar(){
        txtDepDestino.setText("");
        txtDepOrigen.setText("");
        txtDestinatario.setText("");
        txtNroDocumento.setText("");
        txtRemitente.setText("");
    }
    public boolean ValidarGuia(){
        boolean estado = false;
        String sql="SELECT max(idguia) from guia where idtipo_guia=1 and ESTADO LIKE 'ACTIVO'";
        try {
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
                if(rs.getString(1)!=null)
                    estado = true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "ERROR AL CAPTURAR EL cout iddgia \n"+e);
        }
        return estado;
    }
    
    public void Agregar(){
         String sSQL = null,msj;
        switch(AgregarNuevo){
            case "NUEVO":
                sSQL="INSERT INTO guia_detalle (IDGUIA_D, IDTIPO_GUIA, IDGUIA, "
                        + "NRO_DOC, DEPENDENCIA_O, CODIGO, DESTINO,REMITENTE,DESTINATARIO,IDTIPO_DOC) "
                        + "VALUES(?,?,?,?,?,?,?,?,?,?)";
                msj="DOCUMENTO INGRESADO";
                break;
            case "MODIFICAR":
                sSQL="UPDATE guia_detalle "
                        + "SET NRO_DOC = ?,"
                        + "DEPENDENCIA_O = ?,"
                        + "DESTINO = ?,"
                        + "REMITENTE = ?,"
                        + "DESTINATARIO = ?,"
                        + "IDTIPO_DOC = ? "
                        + "WHERE IDGUIA_D="+idGuia_d+" AND IDTIPO_GUIA = 1 "
                        + "AND IDGUIA= (select max(idguia) from guia where idtipo_guia=1 AND ESTADO LIKE 'ACTIVO')";
                msj="DOCUMENTO MODIFICADO";
                break;
            default:
                    msj="ERROR";
                break;
        }
        if("NUEVO".equals(AgregarNuevo)){
            String idguia = null;
            int idguia_D = 0;
            try {
                String sql="select max(idguia) from guia where idtipo_guia=1 "
                        + "and estado like 'ACTIVO'";
                st = con.createStatement();
                rs = st.executeQuery(sql);
                while(rs.next()){
                    idguia=rs.getString(1);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
            try {
                String sql="select count(idguia_d) from guia_detalle "
                        + "where idtipo_guia=1 and idguia="+idguia;
                st = con.createStatement();
                rs = st.executeQuery(sql);
                while(rs.next()){
                    idguia_D=rs.getInt(1);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
            int codigo = 0;
            try {
                String sql="select count(gd.idguia_d) "
                        + "from guia g inner join guia_detalle gd  "
                        + "on g.idguia = gd.idguia and "
                        + "g.idtipo_guia=gd.IDTIPO_GUIA where g.idtipo_guia=1 "
                        + "AND DATE_FORMAT(g.FECHA_ENVIO,'%Y') = DATE_FORMAT(NOW(),'%Y')";
                st = con.createStatement();
                rs = st.executeQuery(sql);
                while(rs.next()){
                    codigo=rs.getInt(1);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                pst = con.prepareStatement(sSQL);
                pst.setString(1, String.valueOf(idguia_D+1));
                pst.setString(2, "1");
                pst.setString(3, idguia);
                pst.setString(4, txtNroDocumento.getText().toUpperCase());
                pst.setString(5, txtDepOrigen.getText().toUpperCase());
                if("2013".equals(sdf.format(new FECHA.FECHA().Fecha()))){
                    pst.setString(6, String.valueOf(codigo+6663));
                }
                else{
                    pst.setString(6, String.valueOf(codigo+1));
                }
                pst.setString(7, txtDepDestino.getText().toUpperCase());
                pst.setString(8, txtRemitente.getText().toUpperCase());
                pst.setString(9, txtDestinatario.getText().toUpperCase());
                pst.setString(10, String.valueOf(cmbTipoDoc.getSelectedIndex()+1));
                int n=pst.executeUpdate();
                if(n>0){
                    JOptionPane.showMessageDialog(null, msj);
                    TablaGuiaLima("");
                    Limpiar();
                    txtNroDocumento.requestFocus();
                }
            } catch (SQLException | HeadlessException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        if("MODIFICAR".equals(AgregarNuevo)){
            try {
                pst = con.prepareStatement(sSQL);
                pst.setString(1, txtNroDocumento.getText().toUpperCase());
                pst.setString(2, txtDepOrigen.getText().toUpperCase());
                pst.setString(3, txtDepDestino.getText().toUpperCase());
                pst.setString(4, txtRemitente.getText().toUpperCase());
                pst.setString(5, txtDestinatario.getText().toUpperCase());
                pst.setString(6, String.valueOf(cmbTipoDoc.getSelectedIndex()+1));
                int n=pst.executeUpdate();
                if(n>0){
                    JOptionPane.showMessageDialog(null, msj);
                    TablaGuiaLima("");
                    Limpiar();
                    txtNroDocumento.requestFocus();
                }
            } catch (SQLException | HeadlessException e) {
                JOptionPane.showMessageDialog(null, e);
            } 
        }
    }

    public void OrdenarTabla(){
        jTable2.getColumnModel().getColumn(0).setPreferredWidth(35);
        jTable2.getColumnModel().getColumn(1).setPreferredWidth(100);
        jTable2.getColumnModel().getColumn(2).setPreferredWidth(100);
        jTable2.getColumnModel().getColumn(3).setPreferredWidth(75);
        jTable2.getColumnModel().getColumn(4).setPreferredWidth(250);
        jTable2.getColumnModel().getColumn(5).setPreferredWidth(250);
        jTable2.getColumnModel().getColumn(6).setPreferredWidth(250);
        jTable2.getColumnModel().getColumn(7).setPreferredWidth(250);
    }
    
    public void TablaGuiaLima(String nroDocumento){
        String titulo [] ={"Nro","TIPO","NRO","CODIGO","DEPEN_ORI","DESTINO","REMITENTE","DESTINATARIO"};
        String registro [] = new String[8];
        String sSQL="select idguia_d, tipo, nro_doc, codigo,dependencia_o, destino, REMITENTE, DESTINATARIO "
                +"from guia_detalle gd INNER join tipo_doc tc on gd.idtipo_doc=tc.idtipo_doc "
                +"INNER JOIN guia g on g.idguia=gd.idguia " 
                +"where g.idguia=(select max(idguia) from guia where idtipo_guia=1 and estado ='ACTIVO') "
                +"AND g.`IDTIPO_GUIA`=1 " 
                +"AND g.estado = 'ACTIVO' AND gd.idtipo_guia = 1 and nro_doc like '%"+nroDocumento+"%'";
        tabla_modelo = new DefaultTableModel(null, titulo){

            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        try {
            st = con.createStatement();
            rs = st.executeQuery(sSQL);
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
            jTable2.setModel(tabla_modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        OrdenarTabla();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        btnTerminar = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmbTipoDoc = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        dcFecha = new com.toedter.calendar.JDateChooser();
        txtNroGuia = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNroDocumento = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtDepOrigen = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtRemitente = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtDepDestino = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtDestinatario = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Guias Lima");
        setPreferredSize(new java.awt.Dimension(670, 580));
        setResizable(false);

        btnTerminar.setText("Terminar");
        btnTerminar.setToolTipText("");
        btnTerminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTerminarActionPerformed(evt);
            }
        });

        btnImprimir.setText("Imprimir");
        btnImprimir.setToolTipText("");
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(485, Short.MAX_VALUE)
                .addComponent(btnImprimir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTerminar)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTerminar)
                    .addComponent(btnImprimir))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3, java.awt.BorderLayout.PAGE_END);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnNuevo.setText("Nuevo");
        btnNuevo.setToolTipText("");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnEditar.setText("Editar");
        btnEditar.setToolTipText("");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnSalir.setText("Salir");
        btnSalir.setToolTipText("");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        jButton1.setText("Consultar Guia");
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
                .addComponent(btnNuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 342, Short.MAX_VALUE)
                .addComponent(btnSalir)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo)
                    .addComponent(btnEditar)
                    .addComponent(btnSalir)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Nro de guia");

        jLabel2.setText("Tipo Documento");

        jLabel3.setText("Fecha");

        txtNroGuia.setEditable(false);
        txtNroGuia.setBackground(new java.awt.Color(255, 255, 255));
        txtNroGuia.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtNroGuia.setForeground(new java.awt.Color(0, 153, 153));
        txtNroGuia.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel4.setText("Nro Documento");

        jLabel5.setText("Dependencia Orig");

        jLabel6.setText("Remitente");

        jLabel7.setText("Dependencia Dest");

        jLabel8.setText("Destinatario");

        btnGuardar.setText("Guardar");
        btnGuardar.setToolTipText("");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(dcFecha, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                                            .addComponent(cmbTipoDoc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(18, 18, Short.MAX_VALUE)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 83, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtNroGuia, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtNroDocumento, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(txtDepOrigen)
                                    .addComponent(txtRemitente, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
                                    .addComponent(txtDepDestino, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(txtDestinatario, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dcFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtNroGuia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbTipoDoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtNroDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtDepOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtRemitente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDepDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDestinatario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel5, java.awt.BorderLayout.PAGE_START);

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });
        jPanel4.add(jTextField1, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        jPanel6.setLayout(new java.awt.BorderLayout());
        jPanel1.add(jPanel6, java.awt.BorderLayout.PAGE_END);

        jTable2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable2.getTableHeader().setReorderingAllowed(false);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable2MousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jPanel1.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        if(!ValidarGuia()){
            HI(true);
            NuevaGuia();
            btnNuevo.setEnabled(false);
            btnEditar.setEnabled(false);
            btnTerminar.setEnabled(true);
            btnImprimir.setEnabled(true);
            txtNroDocumento.requestFocus();
        }
        else{
            JOptionPane.showMessageDialog(this, "Tienes una guia pendiente");
            HI(true);
            btnNuevo.setEnabled(false);
            btnEditar.setEnabled(false);
            btnTerminar.setEnabled(true);
            btnImprimir.setEnabled(true);
            AgregarNuevo = "NUEVO";
            txtNroDocumento.requestFocus();
        }
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        try {
            con.rollback();
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(GuiaLima.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if(txtNroDocumento.getText().length()==0){
            JOptionPane.showMessageDialog(this, "No ha digitado el Nro de Documento");
            txtNroDocumento.requestFocus();
        }else if(dcFecha.getDate() == null){
            JOptionPane.showMessageDialog(this, "No ha digitado la Fecha");
            dcFecha.requestFocus();
        }else if(txtDepOrigen.getText().length() == 0){
            JOptionPane.showMessageDialog(this, "No ha digitado la Depencia Origen");
            txtDepOrigen.requestFocus();
        }else if(txtRemitente.getText().length()==0){
            JOptionPane.showMessageDialog(this, "No ha digitado el Remitente");
            txtRemitente.requestFocus();       
        }else if(txtDepDestino.getText().length()==0){
            JOptionPane.showMessageDialog(this, "No ha digitado la Depencia Destino");
            txtDepDestino.requestFocus();
        }else if(txtDestinatario.getText().length()==0){
            JOptionPane.showMessageDialog(this, "No ha digitado el Destinatario");
            txtDestinatario.requestFocus();
        }else if(txtNroGuia.getText().length()==0){
            JOptionPane.showMessageDialog(this, "No hay Nro de Guia");
            txtNroGuia.requestFocus();
        }else {
            Agregar();
            AgregarNuevo = "NUEVO";
            try {
                con.commit();
                con.setAutoCommit(false);
            } catch (SQLException ex) {
                Logger.getLogger(GuiaLima.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
       CLASSREPORT.GUIA_LIMAREPORT gl= new CLASSREPORT.GUIA_LIMAREPORT();
       gl.setCon(con);
       gl.ejecutarReporte();
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void btnTerminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTerminarActionPerformed
        if(dcFecha.getDate() == null ){
            
        }else if(txtNroGuia.getText().length()==0){
        
        }else{
            try {
                Finalizar();
                con.commit();
            } catch (SQLException ex) {
                Logger.getLogger(GuiaLima.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnTerminarActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        
    }//GEN-LAST:event_jTable2MouseClicked

    private void jTable2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MousePressed
        if(evt.getClickCount()==2){
            int fila = jTable2.getSelectedRow();
            if(fila > -1){
                idGuia_d = (String) jTable2.getValueAt(fila, 0);
                txtNroDocumento.setText((String) jTable2.getValueAt(fila, 2));
                cmbTipoDoc.setSelectedItem((String) jTable2.getValueAt(fila, 1));
                txtDepOrigen.setText((String) jTable2.getValueAt(fila, 4));
                txtDepDestino.setText((String) jTable2.getValueAt(fila, 5));
                txtRemitente.setText((String) jTable2.getValueAt(fila, 6));
                txtDestinatario.setText((String) jTable2.getValueAt(fila, 7));
                AgregarNuevo = "MODIFICAR";
            }
        }
    }//GEN-LAST:event_jTable2MousePressed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed

        if(!ValidarGuia()){
            MODIFICAR_GUIA mg = new MODIFICAR_GUIA(null, true);
            mg.setCon(con);
            mg.setIdtipo_guia("1");
            mg.cmbDep.setEnabled(false);
            mg.cmbProv.setEnabled(false);
            mg.txtResponsable.setEnabled(false);
            mg.btnBuscar1.setEnabled(false);
            mg.setVisible(true);
            if(mg.isEstado()){
                TablaGuiaLima("");
                idguiaActiva();
                btnNuevo.setEnabled(false);
                btnEditar.setEnabled(false);
                HI(true);
                AgregarNuevo = "NUEVO";
                txtNroDocumento.requestFocus();
            }
        }
        else{
            JOptionPane.showMessageDialog(this, "Tienes una guia pendiente");
            HI(true);
            btnNuevo.setEnabled(false);
            btnEditar.setEnabled(false);
            btnTerminar.setEnabled(true);
            btnImprimir.setEnabled(true);
            AgregarNuevo = "NUEVO";
            txtNroDocumento.requestFocus();
        } 
           
    }//GEN-LAST:event_btnEditarActionPerformed

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
       
    }//GEN-LAST:event_jTextField1KeyPressed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
         TablaGuiaLima(jTextField1.getText());
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        BUSCAR_GUIA bg = new BUSCAR_GUIA(null, true);
        bg.setIdtipo_guia("1");
        bg.CargarTabla(null, null, null, "", null);
        bg.setVisible(true);
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
            java.util.logging.Logger.getLogger(GuiaLima.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                GuiaLima dialog = new GuiaLima(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnTerminar;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField txtDepDestino;
    private javax.swing.JTextField txtDepOrigen;
    private javax.swing.JTextField txtDestinatario;
    private javax.swing.JTextField txtNroDocumento;
    private javax.swing.JTextField txtNroGuia;
    private javax.swing.JTextField txtRemitente;
    // End of variables declaration//GEN-END:variables

     public void Combo(){
        String sSQL="select tipo from tipo_doc";
        try {
            st = con.createStatement();
            rs = st.executeQuery(sSQL);
            while(rs.next()){
                cmbTipoDoc.addItem(rs.getString(1));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
     public void Finalizar(){
        String idguia = null;
        try {
            String sql="select max(idguia) from guia where idtipo_guia=1 and Estado like 'ACTIVO'";
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                idguia=rs.getString(1);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        try {
            String sql="UPDATE guia SET ESTADO=?,FECHA_ENVIO = ? WHERE IDGUIA="+idguia+" "
                    + "AND ESTADO LIKE 'ACTIVO' and idtipo_guia=1";
            SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
            pst = con.prepareStatement(sql);
            pst.setString(1, "ENVIADO");
            pst.setString(2, sdf.format(dcFecha.getDate()));
            int n=pst.executeUpdate();
            if(n>0){
                HI(false);
                btnNuevo.setEnabled(true);
                btnEditar.setEnabled(true);
                btnTerminar.setEnabled(false);
                btnImprimir.setEnabled(false);
                txtNroGuia.setText("");
                dcFecha.setDate(null);
                Limpiar();
                TablaGuiaLima("");
                JOptionPane.showMessageDialog(null, "GUIA FINALIZADA");
            }
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, e);
        }
     }
     
    public void idguiaActiva(){
        try {
           String sql="select max(idguia), nroguia, FECHA_ENVIO from guia where idtipo_guia=1 and estado like 'ACTIVO'";
           st = con.createStatement();
           rs = st.executeQuery(sql);
           while(rs.next()){
               txtNroGuia.setText(rs.getString(2));
               dcFecha.setDate(rs.getDate(3));
           }
        } catch (Exception e) {
            System.out.println(""+e);
        }
    }
}
