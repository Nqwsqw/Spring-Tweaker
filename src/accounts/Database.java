package accounts;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	private String url;
	private Connection conn = null;

	public Database(String url) {
		this.url = "jdbc:sqlite:" + url;
		connect();
	}

	private void connect() {
		try {
			conn = DriverManager.getConnection(url);
			System.out.println("Sucessfully connected to database");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void create() {
		try {
			DatabaseMetaData meta = conn.getMetaData();
			System.out.println("Created new database " + url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
