import accounts.AccountManager;

public class Spring {
    public static void main(String[] args) {
	    AccountManager accountManager = new AccountManager();
	    System.out.println(accountManager.login("yuitora", "helloworld"));
    }
}
    