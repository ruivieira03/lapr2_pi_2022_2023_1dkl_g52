package pt.ipp.isep.dei.esoft.project.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationsRepositoryTest {

    private LocationsRepository locationsRepository;

    @BeforeEach
    void beforeEach() {
        locationsRepository = new LocationsRepository();
        locationsRepository.createState("state", new String[]{"district"}, new String[][] {{"City"}});
    }

    @Test
    void createState() {
        // illegal arguments
        assert (!locationsRepository.createState(null, null, null));

        // fail global validation
        assert (!locationsRepository.createState("state", new String[]{"district1"}, new String[][] {{"City1"}}));

        locationsRepository.createState("Florida", new String[]{"Miami Dade"}, new String[][]{{"Miami"}});
        assert (locationsRepository.getStatesList().size() == 2);
    }

    @Test
    void getStatesList() {
        assert (locationsRepository.getStatesList().size() == 1);
    }
}