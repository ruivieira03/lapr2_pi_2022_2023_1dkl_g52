package pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StateDTOTest {

    private StateDTO stateDTO;

    @BeforeEach
    void beforeEach() {
        stateDTO = new StateDTO();
        stateDTO = new StateDTO("State");
        stateDTO = new StateDTO("State", new String[]{"District"}, new String[][]{{"City"}});
    }

    @Test
    void test() {
        assertEquals(stateDTO.name, stateDTO.toString());
    }

}