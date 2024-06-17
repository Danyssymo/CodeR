package uno.dos.tres.dao;

import uno.dos.tres.dao.impl.NewsDaoImpl;
import uno.dos.tres.dao.impl.SQLAuthDao;

public class DaoProvider {

    private static final DaoProvider instance = new DaoProvider();

    private AuthDao authDao = new SQLAuthDao();
    private NewsDao newsDao = new NewsDaoImpl();

    private DaoProvider() {}

    public AuthDao getAuthDao() {
        return authDao;
    }

    public NewsDao getNewsDao() {
        return newsDao;
    }

    public static DaoProvider getInstance() {
        return instance;
    }

}
