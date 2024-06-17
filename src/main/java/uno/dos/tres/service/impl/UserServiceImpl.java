package uno.dos.tres.service.impl;

import uno.dos.tres.bean.AuthInfo;
import uno.dos.tres.bean.RegInfo;
import uno.dos.tres.bean.User;
import uno.dos.tres.dao.AuthDao;
import uno.dos.tres.dao.DaoException;
import uno.dos.tres.dao.DaoProvider;
import uno.dos.tres.service.ServiceException;
import uno.dos.tres.service.UserService;
import uno.dos.tres.service.validator.Validator;
import uno.dos.tres.service.validator.ValidatorException;
import uno.dos.tres.service.validator.ValidatorImpl;

public class UserServiceImpl implements UserService {

    private AuthDao authDao = DaoProvider.getInstance().getAuthDao();
    private Validator validator = new ValidatorImpl();

    @Override
    public User signIn(AuthInfo authInfo) throws ServiceException {
        try {
            return authDao.signIn(authInfo);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void signUp(RegInfo regInfo) throws ServiceException {
        try {
            validator.checkRegInfo(regInfo);
            if (authDao.checkUserMail(regInfo)) {
                throw new ServiceException("Mail is already taken");
            } else {
                authDao.signUp(regInfo);
            }
        } catch (DaoException | ValidatorException e) {
            throw new ServiceException(e.getMessage(), e);
        }

    }

    @Override
    public User rememberMe(String token) throws ServiceException {
        try {
            User user = authDao.checkToken(token);
            return user;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

}
