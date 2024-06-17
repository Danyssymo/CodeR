package uno.dos.tres.bean;

import java.io.Serializable;
import java.util.Objects;

public class AuthInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String login;
    private String password;

    public AuthInfo() {}

    public AuthInfo(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthInfo authInfo = (AuthInfo) o;
        return Objects.equals(login, authInfo.login) && Objects.equals(password, authInfo.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }
}
