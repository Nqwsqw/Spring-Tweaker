import accounts.AccountManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class Spring {

	private static final String LOGIN_REQUEST = "1";
	private static final String CREATE_REQUEST = "2";
	private static final String HASPURCHASED_REQUEST = "3";
	private static final String PURCHASE_REQUEST = "4";
	private static final String HOME_PATH = System.getProperty("user.home") + "/Spring-Tweaker/";

	private static void writeFile(String text, String filepath) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(new File(HOME_PATH + filepath));
			os.write(text.getBytes(), 0, text.length());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

    public static void main(String[] args) {
	    AccountManager accountManager = new AccountManager();
	    String filepath;
	    boolean check;
	    String username = args[1];

	    switch (args[0]) {
			case LOGIN_REQUEST:
				String identifier = accountManager.loginAccount(username, args[2]);
				filepath = "res/communication/tmpID/" + username;
				writeFile(identifier, filepath);
				break;

			case CREATE_REQUEST:
				check = accountManager.createAccount(username, args[2]);
				filepath = "res/communication/checkCreated/" + username;
				writeFile(String.valueOf(check), filepath);
				break;

		    case HASPURCHASED_REQUEST:
		    	check = accountManager.hasPurchased(username);
		    	filepath = "res/communication/hasPurchased/" + username;
		    	writeFile(String.valueOf(check), filepath);
		    	break;

		    case PURCHASE_REQUEST:
		    	check = accountManager.purchase(username, args[2], args[3]); // args[2] here is hwid, args[3] is location
				filepath = "res/communication/purchaseCheck/" + username;
				writeFile(String.valueOf(check), filepath);
		}
    }
}
