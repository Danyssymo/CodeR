package uno.dos.tres.bean;

import java.io.Serializable;
import java.util.Objects;

public class RegInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userName;
    private String password;
    private String email;
    private UserRoles role;

    public RegInfo() {}

    public RegInfo(String userName, String password, String email, UserRoles role) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegInfo regInfo = (RegInfo) o;
        return Objects.equals(userName, regInfo.userName) && Objects.equals(password, regInfo.password) && Objects.equals(email, regInfo.email) && role == regInfo.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, password, email, role);
    }
}
