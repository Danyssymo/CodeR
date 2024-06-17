package uno.dos.tres.service.impl;

import uno.dos.tres.bean.News;
import uno.dos.tres.dao.DaoException;
import uno.dos.tres.dao.DaoProvider;
import uno.dos.tres.dao.NewsDao;
import uno.dos.tres.dao.impl.NewsDaoImpl;
import uno.dos.tres.service.NewsService;
import uno.dos.tres.service.ServiceException;

import java.util.ArrayList;
import java.util.List;

public class NewsServiceImpl implements NewsService {

    private final NewsDao newsDao = DaoProvider.getInstance().getNewsDao();

    @Override
    public List<News> getNews() throws ServiceException {
        try {
            return newsDao.getNews();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addNews(News news, String username) throws ServiceException {
        try {
            newsDao.addNews(news, username);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteNews(int id) throws ServiceException {
        try {
            newsDao.deleteNews(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
