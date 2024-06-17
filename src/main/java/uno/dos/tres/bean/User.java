package uno.dos.tres.bean;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String mail;
    private String username;
    private UserRoles role;

    public User() {}

    public User(String username, UserRoles role) {
        this.username = username;
        this.role = role;
    }

    public User(String mail, String username, UserRoles role) {
        this.mail = mail;
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(mail, user.mail) && Objects.equals(username, user.username) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mail, username, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "mail='" + mail + '\'' +
                ", username='" + username + '\'' +
                ", role=" + role +
                '}';
    }
}
