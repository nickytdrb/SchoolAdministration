
package privateschooljdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PrivateSchoolJDBC {

   
    public static void main(String[] args) throws SQLException {
        
       Connection conn = createConnection();
       
       MainMenu.mainMenu(conn);
    }
    
    

    public static Connection createConnection() {
        String url = "jdbc:mysql://localhost:3306/privateschoolproject1";
        String username = "root";
        String password = "*********************";
        Connection con = null;
        try {
            con = (Connection) DriverManager.getConnection(url, username, password);
            System.out.println("Connection Ok!");
        } catch (SQLException ex) {
            System.out.println("MSQL Connection failed, please check your settings.");
        }
        return con;
    }
    
}
