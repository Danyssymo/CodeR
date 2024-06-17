package uno.dos.tres.dao;

import uno.dos.tres.bean.AuthInfo;
import uno.dos.tres.bean.RegInfo;
import uno.dos.tres.bean.User;

public interface AuthDao {

    User checkToken(String token) throws DaoException;
    User signIn(AuthInfo authInfo) throws DaoException;
    void signUp(RegInfo regInfo) throws DaoException;
    boolean checkUserMail(RegInfo regInfo) throws DaoException;

}
