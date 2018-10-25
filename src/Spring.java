import accounts.Database;

public class Spring {
    public static void main(String[] args) {
        Database database = new Database("/Users/yuitora./Spring-Tweaker/res/myboiyuitora.db");
        database.create();
    }
}
    