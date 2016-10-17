package FECHA;


import CONEXION.CONEXION;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author adm
 */
public class FECHA {
    String fecha;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public Date Fecha(){
        CONEXION mysql = new CONEXION();
        Connection cn = mysql.Conectar();
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT NOW()");
            while(rs.next()){
                fecha= rs.getString(1);
            } 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "NO SE HA CAPTURADO LA FECHA", "ERROR", 0);
        }
        try {
            return sdf.parse(fecha);
        } catch (ParseException ex) {
            Logger.getLogger(FECHA.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
