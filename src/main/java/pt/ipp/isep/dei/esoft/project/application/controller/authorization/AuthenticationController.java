package pt.ipp.isep.dei.esoft.project.application.controller.authorization;

import pt.ipp.isep.dei.esoft.project.repository.AuthenticationRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.isep.lei.esoft.auth.UserSession;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.util.List;

/**
 * The type Authentication controller.
 *
 * @author Paulo Maio pam@isep.ipp.pt
 */
public class AuthenticationController {

    /**
     * The enum Roles.
     */
    public enum Roles {
        /**
         * Administrator role.
         */
        ADMINISTRATOR,
        /**
         * Agent role.
         */
        AGENT,
        /**
         * Client role.
         */
        CLIENT,
        /**
         * Network manager role.
         */
        NETWORK_MANAGER,
        /**
         * Store manager role.
         */
        STORE_MANAGER
    }


    //private final ApplicationSession applicationSession;
    private final AuthenticationRepository authenticationRepository;

    /**
     * Instantiates a new Authentication controller.
     */
    public AuthenticationController() {
        this.authenticationRepository = Repositories.getAuthenticationRepository();
    }

    /**
     * Do login boolean.
     *
     * @param email the email
     * @param pwd   the pwd
     * @return the boolean
     */
    public boolean doLogin(String email, String pwd) {
        try {
            return authenticationRepository.doLogin(email, pwd);
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    /**
     * Gets user roles.
     *
     * @return the user roles
     */
    public List<UserRoleDTO> getUserRoles() {
        if (authenticationRepository.getCurrentUserSession().isLoggedIn()) {
            return authenticationRepository.getCurrentUserSession().getUserRoles();
        }
        return null;
    }

    /**
     * Do logout.
     */
    public void doLogout() {
        authenticationRepository.doLogout();
    }

    /**
     * Get current user session user session.
     *
     * @return the user session
     */
    public UserSession getCurrentUserSession(){
        return authenticationRepository.getCurrentUserSession();
    }
}
