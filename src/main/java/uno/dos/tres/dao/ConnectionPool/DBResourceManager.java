package uno.dos.tres.dao.ConnectionPool;

import java.util.ResourceBundle;

public class DBResourceManager {

    private DBResourceManager() {}

    private final static DBResourceManager instance = new DBResourceManager();

    private ResourceBundle jdbcProperties = ResourceBundle.getBundle("db");

    public static DBResourceManager getInstance() {
        return instance;
    }

    public String getValue(String key) {
        return jdbcProperties.getString(key);
    }


}
