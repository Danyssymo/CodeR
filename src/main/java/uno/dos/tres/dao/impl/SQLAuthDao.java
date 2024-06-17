package uno.dos.tres.dao.impl;

import uno.dos.tres.bean.AuthInfo;
import uno.dos.tres.bean.RegInfo;
import uno.dos.tres.bean.User;
import uno.dos.tres.bean.UserRoles;
import uno.dos.tres.dao.AuthDao;
import uno.dos.tres.dao.ConnectionPool.ConnectionPool;
import uno.dos.tres.dao.ConnectionPool.ConnectionPoolException;
import uno.dos.tres.dao.DaoException;

import java.sql.*;


public class SQLAuthDao implements AuthDao {

    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public User checkToken(String token) throws DaoException {
        /*
		try {

		}catch(SQLException e) {
			throw new DaoException(e);
		}
		*/
        return new User("Dany", UserRoles.ADMIN);
    }

    private final String sqlForSignIn = "SELECT u.idusers, u.username, u.password, u.mail, u.status, r.idroles, r.title " +
            "FROM users u " +
            "JOIN users_has_roles ur ON u.idusers = ur.users_idusers " +
            "JOIN roles r ON ur.roles_idroles = r.idroles " +
            "WHERE u.mail = ? AND u.password = ?";

    @Override
    public User signIn(AuthInfo authInfo) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String mail = authInfo.getLogin();
        String password = authInfo.getPassword();
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(sqlForSignIn);
            statement.setString(1, mail);
            statement.setString(2, password);
            resultSet = statement.executeQuery();
            try {
                if (resultSet.next()) {
                    String userMail = resultSet.getString("mail");
                    String username = resultSet.getString("username");
                    UserRoles role = UserRoles.valueOf(resultSet.getString("title"));
                    System.out.println(username + role);
                    return new User(userMail, username, role);
                } else {
                    throw new DaoException("No such user");
                }
            } catch (SQLException e) {
                throw new DaoException(e);
            }

        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("BD Error", e);
        } finally {
            connectionPool.closeConnection(resultSet, statement, connection);
        }
    }


    private final String sqlForSignUp = "INSERT INTO users (username, password, mail) VALUES (?, ?, ?)";
    private final String roleQuery = "SELECT idroles FROM roles WHERE title = ?";
    private final String insertUserRoleSQL = "INSERT INTO users_has_roles (users_idusers, roles_idroles) VALUES (?, ?)";

    @Override
    public void signUp(RegInfo regInfo) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement pstmtUserRole = null;
        ResultSet generatedKeys = null;
        int roleId = -1;
        int userId = -1;
        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            String username = regInfo.getUserName();
            String password = regInfo.getPassword();
            String mail = regInfo.getEmail();
            UserRoles roleName = regInfo.getRole();
            preparedStatement = connection.prepareStatement(sqlForSignUp, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, mail);
            preparedStatement.executeUpdate();
            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                userId = generatedKeys.getInt(1);
            } else {
                throw new DaoException("Creating user failed, no ID obtained.");
            }

            PreparedStatement psRole = connection.prepareStatement(roleQuery);
            psRole.setString(1, String.valueOf(roleName));
            ResultSet rsRole = psRole.executeQuery();
            if (rsRole.next()) {
                roleId = rsRole.getInt("idroles");
            }

            pstmtUserRole = connection.prepareStatement(insertUserRoleSQL);
            pstmtUserRole.setInt(1, userId);
            pstmtUserRole.setInt(2, roleId);
            pstmtUserRole.executeUpdate();

            connection.commit();

        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.closeConnection(generatedKeys, preparedStatement, connection);
        }
    }

    private final String checkUserForUserMail = "SELECT COUNT(*) FROM users WHERE mail = ?";

    @Override
    public boolean checkUserMail(RegInfo regInfo) throws DaoException {
        Connection connection = null;
        PreparedStatement checkUserStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            checkUserStatement = connection.prepareStatement(checkUserForUserMail);
            String mail = regInfo.getEmail();
            checkUserStatement.setString(1, mail);
            resultSet = checkUserStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }

        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.closeConnection(resultSet, checkUserStatement, connection);
        }
        return false;
    }
}