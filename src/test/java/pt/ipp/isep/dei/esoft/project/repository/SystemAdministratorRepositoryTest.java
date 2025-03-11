package pt.ipp.isep.dei.esoft.project.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.employee.SystemAdministrator;

class SystemAdministratorRepositoryTest {

    private SystemAdministratorRepository systemAdministratorRepository;

    @BeforeEach
    void beforeEach() {
        systemAdministratorRepository = new SystemAdministratorRepository();
        systemAdministratorRepository.createSystemAdministrator("Dave", 123, 123, "123", "admin@ex.com", 123123123);
    }

    @Test
    void testContructor() {
        SystemAdministrator admin = new SystemAdministrator("Tyler", 123, 123, "123", "admin@ex.com", 123123123);
        systemAdministratorRepository = new SystemAdministratorRepository(admin);

        assert (systemAdministratorRepository.getSystemAdministrator().getName().equals("Tyler"));
    }

    @Test
    void getSystemAdministrator() {
        assert (systemAdministratorRepository.getSystemAdministrator() instanceof SystemAdministrator);
    }

    @Test
    void setSystemAdministrator() {
        SystemAdministrator systemAdministrator = new SystemAdministrator("name", 123, 123, "123", "email@ex.com", 123123123);
        systemAdministratorRepository.setSystemAdministrator(systemAdministrator);
        assert (systemAdministratorRepository.getSystemAdministrator().getName().equals("name"));
    }

    @Test
    void createSystemAdministrator() {
        // fails local validation
        assertFalse (systemAdministratorRepository.createSystemAdministrator("name", 123, 123, "123", "invalid||EMAIL||", 123123123));

        // fails global validation
        assertFalse (systemAdministratorRepository.createSystemAdministrator("Dave", 123, 123, "123", "admin@ex.com", 123123123));

        // working
        assert (systemAdministratorRepository.createSystemAdministrator("name", 123, 123, "adress", "email@email.com", 123123123));
    }
}