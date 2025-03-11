package pt.ipp.isep.dei.esoft.project.util.serialization;

import pt.ipp.isep.dei.esoft.project.domain.user.mappers.dto.UserDTO;
import pt.ipp.isep.dei.esoft.project.repository.AuthenticationRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AuthenticationStore implements Serializable {

    List<AuthenticationStoreUser> list = new ArrayList<>();

    public List<AuthenticationStoreUser> getList() {
        return list;
    }

    public void setList(List<AuthenticationStoreUser> list) {
        this.list = list;
    }

    public void addUser(UserDTO userDTO, String password) {
        list.add(new AuthenticationStoreUser(userDTO, password));
    }

    public void offLoadUsersToRepository() {
        AuthenticationRepository authenticationRepository = Repositories.getAuthenticationRepository();
        for (AuthenticationStoreUser user : list) {
            authenticationRepository.addUserWithRoleNoStore(user.getUser(), user.getPassword());
        }
    }
}
