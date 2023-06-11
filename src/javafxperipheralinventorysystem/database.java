/*
 *ITP121 INTEGRATIVE PROGRAMMING & TECHNOLOGIES Final / Project. 
 *By: Ben Ryan Rinconada And Ian Remedio
 */
package javafxperipheralinventorysystem;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author benry
 */
public class database {
    
    public static Connection connectDb(){
        try {
             Class.forName("com.mysql.jdbc.Driver");
             Connection connect = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/peripheralinventory", "root", "");
             return connect;
        }catch(Exception e){e.printStackTrace();}
        return null;
   }
}

  
            
       
        
