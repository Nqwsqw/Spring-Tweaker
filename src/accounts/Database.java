package accounts;

import java.sql.*;

public class Database {
	private String url;
	private final String PREFIX = "jdbc:sqlite:";
	private Connection conn = null;

	public Database(String url) {
		this.url = PREFIX + url;
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

	public void executeStmt(String command) {
		try {
			Statement stmt = conn.createStatement();
			stmt.execute(command);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public ResultSet executeResult(String command) {
		ResultSet rs = null;
		try {
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(command);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return rs;
	}
}
