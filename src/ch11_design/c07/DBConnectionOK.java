package ch11_design.c07;

public class DBConnectionOK {

    private DBConnectionOK() {
        System.out.printf("%s: Connection created.\n", Thread.currentThread().getName());
    }

    public static DBConnectionOK getConnection() {
        return LazyDBConnection.INSTANCE;
    }

    private static class LazyDBConnection {
        private static final DBConnectionOK INSTANCE = new DBConnectionOK();
    }

}
