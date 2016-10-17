/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIOS;

import CONEXION.CONEXION;
import FECHA.FECHA;
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
public final class ATENDER extends javax.swing.JDialog {
    DefaultTableModel tabla_model;
    Statement st;
    ResultSet rs;
    PreparedStatement pst;
    CONEXION mysql = new CONEXION();
    Connection cn = mysql.Conectar();
    String Atender;
    FECHA f = new FECHA();
    
    /**
     * Creates new form ATENDER
     */
    public ATENDER(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        cmbEstado.addItem("ATENDIDO");
        cmbEstado.addItem("ARCHIVADO");
        HIAtender(false);
        HIDerivar(false);
        
    }
    public void TablaDocumento(String nrodoc,String hruta,String recep){
        String sql="select doc.iddoc, tp.tipo, doc.nro_doc, doc.hoja_ruta, doc.fecha, doc.estado from "
                + "documento doc inner join tipo_doc tp on doc.idtipo_doc=tp.idtipo_doc "
                + "inner join derivar dr on dr.iddoc=doc.iddoc "
                + "where doc.idactividad = 1 and doc.nro_doc like '"+nrodoc+"' "
                + "AND doc.estado like 'RECIBIDO' and dr.iddependencia_o="+this.getDependencia()
                +" and dr.IDDERIVAR = (select max(dr2.idderivar) from derivar dr2 where dr.iddoc=dr2.IDDOC) and DATE_FORMAT(doc.fecha,'%Y') = DATE_FORMAT(NOW(),'%Y') "
                +" OR doc.idactividad = 1  and doc.hoja_ruta like '"+hruta+"' "
                + "AND doc.estado like 'RECIBIDO' and dr.iddependencia_o="+this.getDependencia()
                +" and dr.IDDERIVAR = (select max(dr2.idderivar) from derivar dr2 where dr.iddoc=dr2.IDDOC) and DATE_FORMAT(doc.fecha,'%Y') = DATE_FORMAT(NOW(),'%Y')"
                + " OR idactividad = 1 and doc.estado like '"+recep+"' and dr.iddependencia_o="+this.getDependencia()+" "
                +" and dr.IDDERIVAR = (select max(dr2.idderivar) from derivar dr2 where dr.iddoc=dr2.IDDOC) "
                + "and dr.usuario like '"+this.getUsuario()+"' and DATE_FORMAT(doc.fecha,'%Y') = DATE_FORMAT(NOW(),'%Y') "
                + "order by doc.fecha asc";
        String titutlos [] = {"IDDOC","TIPO_DPC","NRO_DOC","HOJA_RUTA","FECHA","ESTADO"};
        String registro [] = new String[6];
        tabla_model = new DefaultTableModel(null,titutlos);
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
                tabla_model.addRow(registro);
            }
            tblDocumento.setModel(tabla_model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "ERROR EN LA TALBA DOCUMENTO \n "+e);
        }
    }
    public void TablaDetalleDoc(String iddoc){
        String sql="select idderivar, iddoc, fecha, estado, dependencia "
                + "from derivar dr inner join dependencias dep on dr.iddependencia_d = dep.iddependencia "
                + "where iddoc="+iddoc;
        String titulos []={"IDD","IDDOC","F_DERIVACION","ESTADO","DEPENDENCIA_DESTINO"};
        String registro [] = new String[5];
        tabla_model = new DefaultTableModel(null, titulos);
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {                
                registro[0]=rs.getString(1);
                registro[1]=rs.getString(2);
                registro[2]=rs.getString(3);
                registro[3]=rs.getString(4);
                registro[4]=rs.getString(5);
                tabla_model.addRow(registro);
            }
            tblDetalleDoc.setModel(tabla_model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "ERROR AL CARGAR LA TABLA DETALLE \n"+e);
        }
        
    }
    public void HIAtender(boolean a){
        cmbAsignadoA.setEnabled(a);
        cmbEstado.setEnabled(a);
        txaObervacionesAtender.setEnabled(a);
        btnAtender.setEnabled(a);
        btnCancelarA.setEnabled(a);
        txaObervacionesAtender.setText("");
        lblHojaRutaA.setText("");
        lblIddocA.setText("");
    }
    public void HIDerivar(boolean a){
        cmbAsignadoD.setEnabled(a);
        cmbDepenciaDerivar.setEnabled(a);
        txaObservacionDerivar.setEnabled(a);
        btnDerivar.setEnabled(a);
        btnCancelarD.setEnabled(a);
        txaObservacionDerivar.setText("");
        lblHRderivar.setText("");
        lblIddocDerivar.setText("");
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
        miAtender = new javax.swing.JMenuItem();
        miDerivar = new javax.swing.JMenuItem();
        miObs = new javax.swing.JMenuItem();
        panel1 = new org.edisoncor.gui.panel.Panel();
        panel2 = new org.edisoncor.gui.panel.Panel();
        jPanel8 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNroDoc = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtHojaRuta = new javax.swing.JTextField();
        cbMostrar = new javax.swing.JCheckBox();
        btnBuscar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDocumento = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDetalleDoc = new javax.swing.JTable();
        tbpAtenderDerivar = new org.edisoncor.gui.tabbedPane.TabbedPaneHeader();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblIddocA = new javax.swing.JLabel();
        lblHojaRutaA = new javax.swing.JLabel();
        cmbAsignadoA = new javax.swing.JComboBox();
        cmbEstado = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txaObervacionesAtender = new javax.swing.JTextArea();
        btnAtender = new javax.swing.JButton();
        btnCancelarA = new javax.swing.JButton();
        btnSalirA = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        cmbDepenciaDerivar = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        cmbAsignadoD = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txaObservacionDerivar = new javax.swing.JTextArea();
        btnDerivar = new javax.swing.JButton();
        btnCancelarD = new javax.swing.JButton();
        btnSalirD = new javax.swing.JButton();
        lblHRderivar = new javax.swing.JLabel();
        lblIddocDerivar = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        miAtender.setText("ATENDER DOCUMENTO");
        miAtender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miAtenderActionPerformed(evt);
            }
        });
        jPopupMenu1.add(miAtender);

        miDerivar.setText("DERIVAR");
        miDerivar.setToolTipText("");
        miDerivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miDerivarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(miDerivar);

        miObs.setText("OBSERVAR DATOS");
        miObs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miObsActionPerformed(evt);
            }
        });
        jPopupMenu1.add(miObs);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ATENCION");

        panel1.setColorPrimario(new java.awt.Color(255, 255, 255));
        panel1.setColorSecundario(new java.awt.Color(153, 204, 255));

        panel2.setColorPrimario(new java.awt.Color(255, 255, 255));
        panel2.setColorSecundario(new java.awt.Color(153, 204, 255));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 0)), "BUSCAR DOCUMENTOS RECEPCIONADOS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 204))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel1.setText("NRO DE DOCUMENTO");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel2.setText("HOJA DE RUTA");

        cbMostrar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        cbMostrar.setText("MOSTRAR TODOS");
        cbMostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMostrarActionPerformed(evt);
            }
        });

        btnBuscar.setText("BUSCAR");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jButton2.setText("ACT");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNroDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtHojaRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cbMostrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBuscar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(txtHojaRuta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbMostrar)
                            .addComponent(txtNroDoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 51)), "DATOS DEL DOCUMENTO RECEPCIONADO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 255))); // NOI18N

        tblDocumento.setComponentPopupMenu(jPopupMenu1);
        tblDocumento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDocumentoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDocumento);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)), "MOVIMIENTOS DEL DOCUMENTO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 204))); // NOI18N

        jScrollPane2.setViewportView(tblDetalleDoc);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                .addContainerGap())
        );

        tbpAtenderDerivar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tbpAtenderDerivar.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel3.setText("ID DOCUMENTO");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel4.setText("HOJA DE RUTA");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel5.setText("ASIGNADO");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel6.setText("ESTADO");

        lblIddocA.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lblIddocA.setForeground(new java.awt.Color(0, 153, 204));
        lblIddocA.setText("jLabel7");

        lblHojaRutaA.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lblHojaRutaA.setForeground(new java.awt.Color(0, 153, 204));
        lblHojaRutaA.setText("lblHojaRutaA");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel9.setText("OBSERVACIONES");

        txaObervacionesAtender.setColumns(20);
        txaObervacionesAtender.setLineWrap(true);
        txaObervacionesAtender.setRows(5);
        txaObervacionesAtender.setWrapStyleWord(true);
        jScrollPane3.setViewportView(txaObervacionesAtender);

        btnAtender.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btnAtender.setText("GUARDAR");
        btnAtender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtenderActionPerformed(evt);
            }
        });

        btnCancelarA.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btnCancelarA.setText("CANCELAR");
        btnCancelarA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarAActionPerformed(evt);
            }
        });

        btnSalirA.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btnSalirA.setText("SALIR");
        btnSalirA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirAActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnAtender)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelarA)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalirA))
                    .addComponent(lblHojaRutaA, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbAsignadoA, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblIddocA, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel9))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblIddocA)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(lblHojaRutaA))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(cmbAsignadoA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCancelarA, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                            .addComponent(btnSalirA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAtender, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        tbpAtenderDerivar.addTab("ATENDER", jPanel1);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel10.setText("DEPENDENCIA A DERIVAR");

        cmbDepenciaDerivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDepenciaDerivarActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel11.setText("ASIGNADO A");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel12.setText("OBSERVACIONES");

        txaObservacionDerivar.setColumns(20);
        txaObservacionDerivar.setLineWrap(true);
        txaObservacionDerivar.setRows(4);
        txaObservacionDerivar.setWrapStyleWord(true);
        jScrollPane4.setViewportView(txaObservacionDerivar);

        btnDerivar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btnDerivar.setText("DERIVAR");
        btnDerivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDerivarActionPerformed(evt);
            }
        });

        btnCancelarD.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btnCancelarD.setText("CANCELAR");
        btnCancelarD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarDActionPerformed(evt);
            }
        });

        btnSalirD.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btnSalirD.setText("SALIR");
        btnSalirD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirDActionPerformed(evt);
            }
        });

        lblHRderivar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lblHRderivar.setForeground(new java.awt.Color(0, 102, 204));
        lblHRderivar.setText("jLabel13");

        lblIddocDerivar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lblIddocDerivar.setForeground(new java.awt.Color(0, 102, 204));
        lblIddocDerivar.setText("jLabel13");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel7.setText("ID DOCUMENTO");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel8.setText("HOJA DE RUTA");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                            .addComponent(cmbAsignadoD, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(btnDerivar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancelarD))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblIddocDerivar)
                                    .addComponent(lblHRderivar, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(13, 13, 13))))
                    .addComponent(cmbDepenciaDerivar, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalirD)
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(cmbDepenciaDerivar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(cmbAsignadoD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addGap(0, 2, Short.MAX_VALUE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(lblHRderivar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(lblIddocDerivar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnSalirD, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDerivar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCancelarD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        tbpAtenderDerivar.addTab("DERIVAR", jPanel11);

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tbpAtenderDerivar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tbpAtenderDerivar, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void cbMostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMostrarActionPerformed
        if(cbMostrar.isSelected()){
            HIAtender(false);
            HIDerivar(false);
            TablaDocumento(null, null, "RECIBIDO");
        }
         else{
             TablaDocumento(null, null, null);
             TablaDetalleDoc(null);
             HIAtender(false);
             HIDerivar(false);
         }
    }//GEN-LAST:event_cbMostrarActionPerformed

    private void miAtenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miAtenderActionPerformed
        int filasel;
        String id,hru;
        try {
            filasel = tblDocumento.getSelectedRow();
            if(filasel == -1){
                JOptionPane.showMessageDialog(null, "no se a seleccionado niguna fila");
            }
            else{
                this.tbpAtenderDerivar.setSelectedIndex(0);
                HIAtender(true);
                HIDerivar(false);
                tabla_model = (DefaultTableModel) tblDocumento.getModel();
                id = (String) tabla_model.getValueAt(filasel, 0);
                hru = (String) tabla_model.getValueAt(filasel, 3);
                lblIddocA.setText(id);
                lblHojaRutaA.setText(hru);
                Conbo();
                Atender="ATENDER";
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_miAtenderActionPerformed

    private void miDerivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDerivarActionPerformed
        int filasel;
        String id,hru;
        try {
            filasel = tblDocumento.getSelectedRow();
            if(filasel == -1){
                JOptionPane.showMessageDialog(null, "no se a seleccionado niguna fila");
            }
            else{
                this.tbpAtenderDerivar.setSelectedIndex(1);
                HIAtender(false);
                HIDerivar(true);
                tabla_model = (DefaultTableModel) tblDocumento.getModel();
                id = (String) tabla_model.getValueAt(filasel, 0);
                hru = (String) tabla_model.getValueAt(filasel, 3);
                lblIddocDerivar.setText(id);
                lblHRderivar.setText(hru);
                ConbosDerivar();
                Atender="DERIVAR";
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_miDerivarActionPerformed

    private void miObsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miObsActionPerformed
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
                OBSERVACIONES obs = new OBSERVACIONES(null, true);
                try {
                    String sql="select max(idderivar) from derivar where iddoc="+id;
                    st = cn.createStatement();
                    rs = st.executeQuery(sql);
                    while(rs.next()){
                        obs.setIddoc(id);
                        obs.setIdervi(rs.getString(1));
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
                obs.setVisible(true);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_miObsActionPerformed

    private void tblDocumentoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDocumentoMouseClicked
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
               TablaDetalleDoc(id);
               tblDetalleDoc.getColumnModel().getColumn(0).setPreferredWidth(5);
               tblDetalleDoc.getColumnModel().getColumn(1).setPreferredWidth(5);
               tblDetalleDoc.getColumnModel().getColumn(2).setPreferredWidth(110);
               tblDetalleDoc.getColumnModel().getColumn(3).setPreferredWidth(70);
               tblDetalleDoc.getColumnModel().getColumn(4).setPreferredWidth(360);
               cmbAsignadoA.removeAllItems();
               HIAtender(false);
               HIDerivar(false);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_tblDocumentoMouseClicked

    private void cmbDepenciaDerivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDepenciaDerivarActionPerformed
        cmbAsignadoD.removeAllItems();
        String sSQL2="select concat(p.nombres,' ',p.apellidos) as PERSONAL "
        + "from personal p inner join dependencias d on p.iddependencia= "
        + "d.iddependencia where dependencia like '"
        + ""+cmbDepenciaDerivar.getSelectedItem().toString()+"'";
        try {
            Statement st1 = cn.createStatement();
            ResultSet rs1 = st1.executeQuery(sSQL2);
            while(rs1.next()){
                cmbAsignadoD.addItem(rs1.getString(1));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_cmbDepenciaDerivarActionPerformed

    private void btnSalirAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirAActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirAActionPerformed

    private void btnSalirDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirDActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirDActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String nd = null, hr = null;
        if(txtNroDoc.getText().length()>0){
            nd=txtNroDoc.getText();
            TablaDocumento(nd, hr, null);
        }
        if(txtHojaRuta.getText().length()>0){
            hr=txtHojaRuta.getText();
            TablaDocumento(nd, hr, null);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        HIAtender(false);
        HIDerivar(false);
        TablaDocumento(null, null, "RECIBIDO");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnDerivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDerivarActionPerformed
        if(txaObservacionDerivar.getText().length()>0){
            String sql1,sql2, msj;
            int iddr=0;
            SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(Atender.equals("DERIVAR")){
                sql1="INSERT DERIVAR (IDDERIVAR, IDDOC, FECHA, ASIGNADO, "
                        + "ESTADO,USUARIO, IDDEPENDENCIA_O, IDDEPENDENCIA_D) "
                        + "VALUES (?,?,?,?,?,?,?,?)";
                sql2="UPDATE DOCUMENTO SET ESTADO = ? WHERE IDDOC="+lblIddocDerivar.getText();
                msj="DOCUMENTO DERIVADO";
                try {
                    //CAPTURAR EL ULTIMO DRIVAR
                    String ssql;
                    try {
                        ssql="select count(idderivar) from derivar where iddoc="+lblIddocDerivar.getText();
                        st = cn.createStatement();
                        rs = st.executeQuery(ssql);
                        while(rs.next()){
                            iddr=rs.getInt(1);
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(rootPane, "ERROR AL CAPTURARL EL IDDERIVAR \n"+e);
                    }
                    String iddepen = null;
                        try {
                            String sql="select iddependencia from dependencias where "
                                    + "dependencia like '"+this.cmbDepenciaDerivar.getSelectedItem().toString()+"'";
                            Statement st1 = cn.createStatement();
                            ResultSet rs1 = st1.executeQuery(sql);
                            while(rs1.next()){
                                 iddepen=rs1.getString(1);
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(rootPane, "ERROR AL CAPTURAR LA DEPENDENCIA\n"+e);
                        }

                    PreparedStatement pst = cn.prepareStatement(sql1);
                    pst.setInt(1, (iddr+1));
                    pst.setString(2, lblIddocDerivar.getText());
                    pst.setString(3, sdf.format(f.Fecha()));
                    pst.setString(4, cmbAsignadoD.getSelectedItem().toString());
                    pst.setString(5, "DERIVADO");
                    pst.setString(6, this.getUsuario());
                    pst.setString(7, this.getDependencia());
                    pst.setString(8, iddepen);
                    pst.executeUpdate();
                } catch (HeadlessException | SQLException e) {
                    JOptionPane.showMessageDialog(rootPane, "ERROR AL ATENDER EL DOCUMENTO (INSERT) \n"+e);
                }
                try {
                    PreparedStatement pst = cn.prepareStatement(sql2);
                    pst.setString(1, "DERIVADO");
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(rootPane, msj);
                    TablaDocumento(null, null, "RECIBIDO");
                    HIDerivar(false);
                    cbMostrar.setSelected(false);
                } catch (SQLException | HeadlessException e) {
                    JOptionPane.showMessageDialog(rootPane, "ERROR AL ACTULIZAR EL DODUCMENTO \n"+e);
                }
                Atender="";
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "ERROR : FALTA DIGITAR EL CAMPO DE OBSERVACIONES", "ERROR", 0);
        }
    }//GEN-LAST:event_btnDerivarActionPerformed

    private void btnAtenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtenderActionPerformed
        if(txaObervacionesAtender.getText().length()>0){
            String sql1,sql2, msj;
            int iddr=0;
            SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(Atender.equals("ATENDER")){
                sql1="INSERT DERIVAR (IDDERIVAR, IDDOC, FECHA, ASIGNADO, "
                        + "ESTADO, OBSERVACIONES, USUARIO, IDDEPENDENCIA_O, IDDEPENDENCIA_D) "
                        + "VALUES (?,?,?,?,?,?,?,?,?)";
                sql2="UPDATE DOCUMENTO SET ESTADO = ? WHERE IDDOC="+lblIddocA.getText();
                msj="DOCUMENTO ATENDIDO";
                try {
                    //CAPTURAR EL ULTIMO DRIVAR
                    String ssql;
                    try {
                        ssql="select count(idderivar) from derivar where iddoc="+lblIddocA.getText();
                        st = cn.createStatement();
                        rs = st.executeQuery(ssql);
                        while(rs.next()){
                            iddr=rs.getInt(1);
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(rootPane, "ERROR AL CAPTURARL EL IDDERIVAR \n"+e);
                    }
                    PreparedStatement pst = cn.prepareStatement(sql1);
                    pst.setInt(1, (iddr+1));
                    pst.setString(2, lblIddocA.getText());
                    pst.setString(3, sdf.format(f.Fecha()));
                    pst.setString(4, cmbAsignadoA.getSelectedItem().toString());
                    pst.setString(5, cmbEstado.getSelectedItem().toString());
                    pst.setString(6, txaObervacionesAtender.getText().toUpperCase());
                    pst.setString(7, this.getUsuario());
                    pst.setString(8, this.getDependencia());
                    pst.setString(9, this.getDependencia());
                    pst.executeUpdate();
                } catch (HeadlessException | SQLException e) {
                    JOptionPane.showMessageDialog(rootPane, "ERROR AL ATENDER EL DOCUMENTO (INSERT) \n"+e);
                }
                try {
                    PreparedStatement pst = cn.prepareStatement(sql2);
                    pst.setString(1, "ATENDIDO");
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(rootPane, msj);
                    HIAtender(false);
                    TablaDocumento(null, null, "RECIBIDO");
                    TablaDetalleDoc(null);
                } catch (SQLException | HeadlessException e) {
                    JOptionPane.showMessageDialog(rootPane, "ERROR AL ACTULIZAR EL DODUCMENTO \n"+e);
                }
                cbMostrar.setSelected(false);
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "ERROR : FALTA DIGITAR EL CAMPO DE OBSERVACIONES", "ERROR", 0);
        }
    }//GEN-LAST:event_btnAtenderActionPerformed

    private void btnCancelarAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarAActionPerformed
       HIAtender(false);
    }//GEN-LAST:event_btnCancelarAActionPerformed

    private void btnCancelarDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarDActionPerformed
        HIDerivar(false);
    }//GEN-LAST:event_btnCancelarDActionPerformed

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
            java.util.logging.Logger.getLogger(ATENDER.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                ATENDER dialog = new ATENDER(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAtender;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelarA;
    private javax.swing.JButton btnCancelarD;
    private javax.swing.JButton btnDerivar;
    private javax.swing.JButton btnSalirA;
    private javax.swing.JButton btnSalirD;
    private javax.swing.JCheckBox cbMostrar;
    private javax.swing.JComboBox cmbAsignadoA;
    private javax.swing.JComboBox cmbAsignadoD;
    private javax.swing.JComboBox cmbDepenciaDerivar;
    private javax.swing.JComboBox cmbEstado;
    private javax.swing.JButton jButton2;
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
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblHRderivar;
    private javax.swing.JLabel lblHojaRutaA;
    private javax.swing.JLabel lblIddocA;
    private javax.swing.JLabel lblIddocDerivar;
    private javax.swing.JMenuItem miAtender;
    private javax.swing.JMenuItem miDerivar;
    private javax.swing.JMenuItem miObs;
    private org.edisoncor.gui.panel.Panel panel1;
    private org.edisoncor.gui.panel.Panel panel2;
    private javax.swing.JTable tblDetalleDoc;
    private javax.swing.JTable tblDocumento;
    private org.edisoncor.gui.tabbedPane.TabbedPaneHeader tbpAtenderDerivar;
    private javax.swing.JTextArea txaObervacionesAtender;
    private javax.swing.JTextArea txaObservacionDerivar;
    private javax.swing.JTextField txtHojaRuta;
    private javax.swing.JTextField txtNroDoc;
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
    public void Conbo(){
        String sql="select CONCAT(nombres,' ',apellidos) from personal "
                + "where iddependencia ="+this.getDependencia();
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                cmbAsignadoA.addItem(rs.getString(1));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "ERROR AL CARGAR EL COMBO ASIGNADO \n"+e);
        }
       
    }
    public void ConbosDerivar(){
        String sql1="select dependencia from dependencias where idprovincia=2 and iddepartamento=6";
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql1);
            while(rs.next()){
                cmbDepenciaDerivar.addItem(rs.getString(1));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "ERROR AL CARGAR EL COMBO TIPO DOC \n"+e);
        }
    }

}
