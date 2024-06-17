package uno.dos.tres.bean;

import java.io.Serializable;
import java.util.Objects;

public class News implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String title;
    private String content;
    private String imgPath;
    private String authorUsername;
    private String authorMail;

    public News() {}

    public News(String title, String content, String imgPath) {
        this.title = title;
        this.content = content;
        this.imgPath = imgPath;
    }

    public News(int id, String title, String content, String imgPath, String authorUsername, String authorMail) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imgPath = imgPath;
        this.authorUsername = authorUsername;
        this.authorMail = authorMail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public String getAuthorMail() {
        return authorMail;
    }

    public void setAuthorMail(String authorMail) {
        this.authorMail = authorMail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return id == news.id && Objects.equals(title, news.title) && Objects.equals(content, news.content) && Objects.equals(imgPath, news.imgPath) && Objects.equals(authorUsername, news.authorUsername) && Objects.equals(authorMail, news.authorMail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, imgPath, authorUsername, authorMail);
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", imgPath='" + imgPath + '\'' +
                ", authorUsername='" + authorUsername + '\'' +
                ", authorMail='" + authorMail + '\'' +
                '}';
    }
}
