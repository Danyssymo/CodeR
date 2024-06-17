package uno.dos.tres.dao.impl;

import uno.dos.tres.bean.News;
import uno.dos.tres.dao.ConnectionPool.ConnectionPool;
import uno.dos.tres.dao.ConnectionPool.ConnectionPoolException;
import uno.dos.tres.dao.DaoException;
import uno.dos.tres.dao.NewsDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NewsDaoImpl implements NewsDao {

    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final String sqlForAddNews1 = "INSERT INTO news (title, content, img_path, users_idusers) VALUES (?, ?, ?, ?)";
    private final String sqlForAddNews2 = "SELECT idusers FROM users WHERE username = ?";

    @Override
    public void addNews(News news, String username) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatementUser = null;
        PreparedStatement preparedStatementNews = null;
        ResultSet rs = null;
        String title = news.getTitle();
        String content = news.getContent();
        String imgPath = news.getImgPath();
        int userId;
        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            preparedStatementUser = connection.prepareStatement(sqlForAddNews2);
            preparedStatementUser.setString(1, username);
            rs = preparedStatementUser.executeQuery();
            userId = -1;
            if (rs.next()) {
                userId = rs.getInt("idusers");
            }

            if (userId != -1) {
                preparedStatementNews = connection.prepareStatement(sqlForAddNews1);
                preparedStatementNews.setString(1, title);
                preparedStatementNews.setString(2, content);
                preparedStatementNews.setString(3, imgPath);
                preparedStatementNews.setInt(4, userId);
                preparedStatementNews.executeUpdate();
            } else {
                throw new DaoException("User Not Found");
            }
            connection.commit();
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.closeConnection(rs, preparedStatementUser, connection);
        }
    }

    private final String sqlForDeleteNews = "DELETE FROM news WHERE idnews = ?";

    @Override
    public void deleteNews(int id) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(sqlForDeleteNews);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.closeConnection(preparedStatement, connection);
        }
    }

    @Override
    public void updateNews(News news) throws DaoException {

    }

    private final String sqlForGetNews = "SELECT n.idnews, n.title, n.content, n.img_path, u.mail, u.username " +
            "FROM news n " +
            "JOIN users u ON n.users_idusers = u.idusers;";

    @Override
    public List<News> getNews() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<News> list = new ArrayList<>();
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(sqlForGetNews);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                News news = new News();
                news.setId(resultSet.getInt("idnews"));
                news.setTitle(resultSet.getString("title"));
                news.setContent(resultSet.getString("content"));
                news.setImgPath(resultSet.getString("img_path"));
                news.setAuthorUsername(resultSet.getString("username"));
                news.setAuthorMail(resultSet.getString("mail"));
                list.add(news);
            }
            //connection.commit();
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.closeConnection(resultSet, preparedStatement, connection);
        }
        return list;
    }
}
