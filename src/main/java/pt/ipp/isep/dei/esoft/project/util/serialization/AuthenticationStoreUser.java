package pt.ipp.isep.dei.esoft.project.util.serialization;

import pt.ipp.isep.dei.esoft.project.domain.user.mappers.dto.UserDTO;

import java.io.Serializable;

public class AuthenticationStoreUser implements Serializable {

    private UserDTO user;
    private String password;

    public AuthenticationStoreUser(UserDTO user, String password) {
        this.user = user;
        this.password = password;
    }

    public AuthenticationStoreUser() {
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
