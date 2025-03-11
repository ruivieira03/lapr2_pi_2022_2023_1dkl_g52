package pt.ipp.isep.dei.esoft.project.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.isep.lei.esoft.auth.UserSession;

class AuthenticationRepositoryTest {

    private AuthenticationRepository authenticationRepository;


    @BeforeEach
    public void beforeEach() {
        authenticationRepository = new AuthenticationRepository();
        authenticationRepository.addUserRole(AuthenticationController.Roles.ADMINISTRATOR.name(), AuthenticationController.Roles.ADMINISTRATOR.name());
        authenticationRepository.addUserWithRole("Dave", "admin@ex.com", "1234", AuthenticationController.Roles.ADMINISTRATOR);
    }

    @Test
    void doLogin() {
        authenticationRepository.doLogin("admin@ex.com", "1234");
        UserSession userSession = authenticationRepository.getCurrentUserSession();

        assert(userSession.getUserId().toString().equals("admin@ex.com"));
    }

    @Test
    void doLogout() {
        authenticationRepository.doLogin("admin@ex.com", "1234");
        authenticationRepository.doLogout();
        UserSession userSession = authenticationRepository.getCurrentUserSession();

        assert(!userSession.isLoggedIn());
    }

    @Test
    void getCurrentUserSession() {
        UserSession userSession = authenticationRepository.getCurrentUserSession();
        assert (userSession != null);

    }

    @Test
    void addUserRole() {
        authenticationRepository.addUserRole(AuthenticationController.Roles.AGENT.name(), AuthenticationController.Roles.ADMINISTRATOR.name());
        assert (authenticationRepository.addUserWithRole("Clive", "123@ex.com", "1234", AuthenticationController.Roles.AGENT));
    }

    @Test
    void addUserWithRole() {
        assert (authenticationRepository.addUserWithRole("Clive", "123@ex.com", "1234", AuthenticationController.Roles.ADMINISTRATOR));
    }
}