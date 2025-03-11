package pt.ipp.isep.dei.esoft.project.domain.locations.mappers;

import pt.ipp.isep.dei.esoft.project.domain.locations.District;
import pt.ipp.isep.dei.esoft.project.domain.locations.State;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.DistrictDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.StateDTO;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

import java.util.ArrayList;
import java.util.List;

/**
 * The type State mapper.
 */
public class StateMapper {

    private final transient DistrictMapper districtMapper = new DistrictMapper();

    /**
     * Instantiates a new State mapper.
     */
    public StateMapper() {
    }

    /**
     * To dto error optional.
     * Used to map domain representation to dto representation.
     *
     * @param state the state
     * @return the error optional
     */
    public ErrorOptional<StateDTO> toDTO(State state) {
        if (state == null) return ErrorOptional.empty("Error - Mapper - State cannot be null!");

        StateDTO dto = new StateDTO();

        dto.name = state.getName();

        dto.districtList = new ArrayList<>();
        for (District district : state.getDistricts()) {
            ErrorOptional<DistrictDTO> errorOptional;
            errorOptional = districtMapper.toDTO(district);
            if (errorOptional.hasError()) return ErrorOptional.empty(errorOptional.getErrorMessage());
            dto.districtList.add(errorOptional.get());
        }

        return ErrorOptional.of(dto);
    }

    /**
     * To domain error optional.
     * Used to map dto representation to domain representation.
     *
     * @param dto the dto
     * @return the error optional
     */
    public ErrorOptional<State> toDomain(StateDTO dto) {
        if (dto == null) return ErrorOptional.empty("Error - Mapper - StateDTO cannot be null!");

        State state = new State();

        state.setName(dto.name);

        List<District> districtList = new ArrayList<>();
        for (DistrictDTO districtDTO : dto.districtList) {
            ErrorOptional<District> errorOptional;
            errorOptional = districtMapper.toDomain(districtDTO);
            if (errorOptional.hasError()) return ErrorOptional.empty(errorOptional.getErrorMessage());
            districtList.add(errorOptional.get());
        }

        state.setDistricts(districtList);

        return ErrorOptional.of(state);
    }
}
