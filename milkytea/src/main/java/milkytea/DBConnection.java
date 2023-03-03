package milkytea;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
   private static Connection instance;

   private DBConnection(){}

   public static Connection getInstance(){
      if(instance == null) {
        try{  
            Class.forName("com.mysql.cj.jdbc.Driver");  
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/milktea","root","");   
            instance = con;
            
            return con;
        } catch(Exception e){ 
            System.out.println(e);
            return null;
        }          
      } 
       
      return instance;
   }
}

