package accounts;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AccountManager {
	Database db;
	private final String PATH = "res/accounts/";
	private Map<String, String> userTmpId = new HashMap<>();
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

	private String getMacAddress() {
		InetAddress ip;
		String result = null;
		try {
			ip = InetAddress.getLocalHost();
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);

			byte[] mac = network.getHardwareAddress();

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
			}
			result = sb.toString();
		} catch (UnknownHostException | SocketException e) {
			e.printStackTrace();
		}

		return result;
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

	public String loginAccount(String username, String password) {
		boolean correctPassword = password.equals(getPassword(username));
		if (!correctPassword) {
			return null;
		}

		String id = generateID();
		userTmpId.put(id, username);
		return id;
	}

	public boolean hasPurchased(String username) {
		db = new Database(PATH + username + ".db");

		String command = "SELECT purchased from info WHERE username='" + username + "'";
		ResultSet rs = db.executeResult(command);

		boolean check = false;
		try {
			check = rs.getInt("purchased") == 1;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return check;
	}

	public void purchase(String username, String location) {
		db = new Database(PATH + username + ".db");

		String command = "CREATE TABLE identifier(hwid text, location text);";
		db.executeStmt(command);

		String hwid = getMacAddress();
		command = "INSERT INTO identifier(hwid, location) VALUES('"
				+ hwid + "', '"
				+ location + "')";
		db.executeStmt(command);
	}



}
