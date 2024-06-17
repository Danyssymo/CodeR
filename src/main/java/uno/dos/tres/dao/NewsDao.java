package uno.dos.tres.dao;

import uno.dos.tres.bean.News;

import java.util.List;

public interface NewsDao {

    void addNews(News news, String username) throws DaoException;
    void deleteNews(int id) throws DaoException;
    void updateNews(News news) throws DaoException;
    List<News> getNews() throws DaoException;


}
