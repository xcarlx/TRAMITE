/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CLASSREPORT;

import CONEXION.CONEXION;
import com.mysql.jdbc.Connection;
import java.awt.HeadlessException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author CARLOS RUIZ
 */
public class DERIVACION {
     public void ejecutarReporte(String dep, String user){
        CONEXION mysql = new CONEXION();
        Connection cn = (Connection) mysql.Conectar();
        try {
            String archivo = System.getProperty("user.dir")+"/src/REPORTES/DERIVACION.jasper";//System.getProperty("user.dir")+"/src/REPORTE/Reporte.jasper"
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
            Map parametro = new HashMap();
            parametro.put("DEPENDENCIA", dep);
            parametro.put("usuario", user);
            JasperPrint jasperPrint = JasperFillManager.fillReport(masterReport, parametro, cn);
            JasperViewer jviewer = new JasperViewer(jasperPrint,false);
            jviewer.setTitle("REPORTE DE DERIVACION");
            jviewer.setVisible(true);
        } catch (HeadlessException | JRException e) {
            JOptionPane.showMessageDialog(null,"Mensaje de error"+e.getMessage());
        }
        
    }
}
