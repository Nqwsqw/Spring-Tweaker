import accounts.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Spring {
    public static void main(String[] args) {
        Database database = new Database("/Users/yuitora./Spring-Tweaker/res/myboiyuitora.db");

        String command = "SELECT * FROM test";
        ResultSet rs = database.executeResult(command);

        try {
	        while (rs.next()) {
				System.out.println(rs.getString("name"));
	        }
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
        }
    }
}
    