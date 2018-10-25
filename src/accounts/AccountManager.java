package accounts;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Random;

public class AccountManager {
	Database db;
	private final String PATH = "~/Spring-Tweaker/res/accounts/";
	private Map<String, String> userTmpId;
	public AccountManager(){}

	private boolean alreadyExist(String username) {
		File file = new File(PATH + username + ".db");
		return file.exists();
	}

	private String generateID() {
		String characters = "qwertyuiopasdfghjklzxcvbnm1234567890";
		Random rand = new Random();
		StringBuilder id = new StringBuilder();
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++)
				id.append(characters.charAt(rand.nextInt(36)));
			id.append("-");
		}
		return id.toString();
	}

	private String getPassword(String username) {
		db = new Database(PATH + username + ".db");

		String command = "SELECT password from info WHERE username='" + username + "'";
		ResultSet rs = db.executeResult(command);

		String pwd = null;
		try {
			pwd = rs.getString("password");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return pwd;
	}

	public void createAccount(String username, String password) {
		if (alreadyExist(username)) {
			return;
		}

		db = new Database(PATH + username + ".db");
		db.create();

		String command = "CREATE TABLE info(username text, password text, purchased integer);";
		db.executeStmt(command);

		command = "INSERT INTO info(username, password, purchased) VALUES ('"
			+ username + "', '"
			+ password + "', 0);";
		db.executeStmt(command);
	}

	public String login(String username, String password) {
		boolean correctPassword = password == getPassword(username);
		if (!correctPassword) {
			return null;
		}

		String id = generateID();
		userTmpId.put(id, username);
		return id;
	}

}
