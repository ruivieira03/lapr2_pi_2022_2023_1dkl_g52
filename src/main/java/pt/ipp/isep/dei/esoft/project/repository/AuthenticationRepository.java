package pt.ipp.isep.dei.esoft.project.repository;


import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.AgentDTO;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.NetworkManagerDTO;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.StoreManagerDTO;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.SystemAdministratorDTO;
import pt.ipp.isep.dei.esoft.project.domain.user.mappers.dto.UserDTO;
import pt.ipp.isep.dei.esoft.project.util.serialization.AuthenticationStore;
import pt.isep.lei.esoft.auth.AuthFacade;
import pt.isep.lei.esoft.auth.UserSession;

import java.io.Serializable;

/**
 * The type Authentication repository.
 */
public class AuthenticationRepository implements Serializable {
    /**
     * Used to login
     */
    private transient AuthFacade authenticationFacade = new AuthFacade();

    private final AuthenticationStore authenticationStore = new AuthenticationStore();

    /**
     * Method used to login in the application
     *
     * @param email user email
     * @param pwd   user password
     * @return true if logged in or false if not
     */
    public boolean doLogin(String email, String pwd) {
        return authenticationFacade.doLogin(email, pwd).isLoggedIn();
    }

    /**
     * Method used to logout of the application
     */
    public void doLogout() {
        authenticationFacade.doLogout();
    }

    /**
     * Method used to get the current user using the application
     *
     * @return current user session
     */
    public UserSession getCurrentUserSession() {
        return authenticationFacade.getCurrentUserSession();
    }

    /**
     * Method used to add a new user role to the system
     *
     * @param id          role id
     * @param description role description
     * @return true if added or false if not
     */
    public boolean addUserRole(String id, String description) {
        return authenticationFacade.addUserRole(id, description);
    }

    /**
     * Adds a new user to the repository
     *
     * @param userDTO  User to be registered
     * @param password Password
     * @return boolean Success of operation
     */
    public boolean addUserWithRole(UserDTO userDTO, String password) {

        AuthenticationController.Roles role;

        if (userDTO instanceof ClientDTO) role = AuthenticationController.Roles.CLIENT;
        else if (userDTO instanceof AgentDTO) role = AuthenticationController.Roles.AGENT;
        else if (userDTO instanceof StoreManagerDTO) role = AuthenticationController.Roles.STORE_MANAGER;
        else if (userDTO instanceof NetworkManagerDTO) role = AuthenticationController.Roles.NETWORK_MANAGER;
        else if (userDTO instanceof SystemAdministratorDTO) role = AuthenticationController.Roles.ADMINISTRATOR;
        else return false;

        if (authenticationFacade.addUserWithRole(userDTO.name, userDTO.email, password, role.toString())) {
            authenticationStore.addUser(userDTO, password);
            return true;
        }

        return false;
    }

    /**
     * Add user with role no store.
     *
     * @param userDTO  the user dto
     * @param password the password
     */
    public void addUserWithRoleNoStore(UserDTO userDTO, String password) {
        AuthenticationController.Roles role;

        if (userDTO instanceof ClientDTO) role = AuthenticationController.Roles.CLIENT;
        else if (userDTO instanceof AgentDTO) role = AuthenticationController.Roles.AGENT;
        else if (userDTO instanceof StoreManagerDTO) role = AuthenticationController.Roles.STORE_MANAGER;
        else if (userDTO instanceof NetworkManagerDTO) role = AuthenticationController.Roles.NETWORK_MANAGER;
        else if (userDTO instanceof SystemAdministratorDTO) role = AuthenticationController.Roles.ADMINISTRATOR;
        else return;

        authenticationFacade.addUserWithRole(userDTO.name, userDTO.email, password, role.toString());
    }

    /**
     * Deserialize.
     */
    public void deserialize() {
        authenticationFacade = new AuthFacade();
        addUserRoles();
        authenticationStore.offLoadUsersToRepository();
    }

    private void addUserRoles() {
        for (AuthenticationController.Roles role : AuthenticationController.Roles.values()) {
            addUserRole(role.name(), role.name());
        }
    }
}
