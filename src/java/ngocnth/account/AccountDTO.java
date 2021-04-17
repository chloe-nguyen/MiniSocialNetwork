
package ngocnth.account;

import java.io.Serializable;

public class AccountDTO implements Serializable {
    private String email;
    private String password;
    private String name;
    private String role;
    private int statusId;

    public AccountDTO() {
    }

    public AccountDTO(String email, String password, String name, String role, int statusId) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.statusId = statusId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    
}
