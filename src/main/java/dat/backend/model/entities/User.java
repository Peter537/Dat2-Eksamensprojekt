package dat.backend.model.entities;

import java.util.Objects;

public class User {

    private String username;
    private String password;
    private String role;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return this.getUsername().equals(user.getUsername()) &&
                this.getPassword().equals(user.getPassword()) &&
                this.getRole().equals(user.getRole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getUsername(), this.getPassword(), this.getRole());
    }

    @Override
    public String toString() {
        return "User{" +
                "brugerNavn='" + this.username + '\'' +
                ", kodeord='" + this.password + '\'' +
                ", rolle='" + this.role + '\'' +
                '}';
    }
}