package CLASSREPORT;

import java.awt.HeadlessException;
import java.sql.Connection;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author CARLOS RUIZ
 */
public class GUIA_LIMAREPORT {

    Connection con;

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }
    public void ejecutarReporte(){
        try {
            String archivo = System.getProperty("user.dir")+"/src/REPORTES/GUIAS_LIMA.jasper";//System.getProperty("user.dir")+"/src/REPORTE/Reporte.jasper"
            System.out.println("Carardo desde: " + archivo);
            if(archivo==null){
                JOptionPane.showMessageDialog(null, "NO SE ENCUENTRA EL ARCHIVO");
            }
            JasperReport masterReport=null;
            try {
                masterReport=(JasperReport) JRLoader.loadObjectFromFile(archivo);
                
            } catch (JRException e) {
                JOptionPane.showMessageDialog(null,"Error cargando el report maestro: "+ e.getMessage());
            }
            JasperPrint jasperPrint = JasperFillManager.fillReport(masterReport, null,con);
            JasperViewer jviewer = new JasperViewer(jasperPrint,false);
            jviewer.setTitle("GUIAS LIMA");
            jviewer.setVisible(true);
        } catch (HeadlessException | JRException e) {
            JOptionPane.showMessageDialog(null, "Mensaje de error"+e.getMessage());
        }
    }
}
