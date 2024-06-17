package uno.dos.tres.service;

import uno.dos.tres.bean.News;

import java.util.List;

public interface NewsService {

    List<News> getNews() throws ServiceException;

    void addNews(News news, String username) throws ServiceException;

    void deleteNews(int id) throws ServiceException;

}
