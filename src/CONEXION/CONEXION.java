/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CONEXION;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class CONEXION {
    public String db = "tramite_documentario";
    public String url = "jdbc:mysql://192.168.16.205:3306/"+db;
    //public String url = "jdbc:mysql://192.168.0.4:3306/"+db;
//    public String url = "jdbc:mysql://localhost:3306/"+db;
    public String user = "FISCALIA";
//    public String user = "root";
//    public String pass = "123456";   
    public String pass = "F&sc@l&@2013";   
    public CONEXION(){
 
   }
    public Connection Conectar(){
        Connection link = null;
        try 
        {
         Class.forName("com.mysql.jdbc.Driver");
         link = DriverManager.getConnection(this.url, this.user, this.pass);
         }
        catch (ClassNotFoundException | SQLException e){
            JOptionPane.showMessageDialog(null, e);
            System.exit(0);
        }
        return link;
    }
}
