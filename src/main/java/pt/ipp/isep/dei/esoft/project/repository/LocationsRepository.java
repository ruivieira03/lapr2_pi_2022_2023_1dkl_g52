package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.locations.City;
import pt.ipp.isep.dei.esoft.project.domain.locations.District;
import pt.ipp.isep.dei.esoft.project.domain.locations.State;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.StateMapper;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.StateDTO;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The type Locations repository.
 */
public class LocationsRepository implements Serializable {
    /**
     * list of states
     */
    List<State> states = new ArrayList<>();

    private transient StateMapper stateMapper = new StateMapper();


    /**
     * Method used to create a state.
     *
     * @param stateDTO StateDTO - state data transfer object
     * @return boolean - successes
     */
    public OperationResult createState(StateDTO stateDTO) {
        ErrorOptional<State> state = stateMapper.toDomain(stateDTO);
        if (state.hasError())
            return OperationResult.failed(state.getErrorMessage() + "\nError - Repository failed to convert StateDTO into State!");

        return addState(state.get());
    }

    /**
     * Method used to add a state to the list.
     *
     * @param state state
     * @return true if added or false if not
     */
    private OperationResult addState(State state) {
        if (!state.isValid().success())
            return OperationResult.failed(state.isValid().getErrorMessage() + "\nError - Repository - Failed local validation!");
        if (!isValid(state).success())
            return OperationResult.failed(isValid(state).getErrorMessage() + "\nError - Repository - Failed global validation!");

        states.add(state);
        return OperationResult.successfull();
    }

    /**
     * Method used to check if a state is valid.
     *
     * @param state state
     * @return true is it is valid or false if not
     */
    private OperationResult isValid(State state) {
        for (State s : states) {
            if (s.equals(state))
                return OperationResult.failed("Error - Repository - State already exists!");
        }

        return OperationResult.successfull();
    }


    /**
     * Method used to get the states list.
     *
     * @return states list
     */
    public List<StateDTO> getStatesList() {

        List<StateDTO> list = new ArrayList<>();

        ErrorOptional<StateDTO> errorOptional;

        for (State state : states) {
            errorOptional = stateMapper.toDTO(state);
            if (!errorOptional.hasError())
                list.add(errorOptional.get());
        }

        return list;
    }

    /**
     * Gets state by name.
     *
     * @param name the name
     * @return the state by name
     */
    public Optional<State> getStateByName(String name) {
        if (name == null) return Optional.empty();

        for (State state : states) {
            if (state.getName().equals(name))
                return Optional.of(state);
        }

        return Optional.empty();
    }

    /**
     * Gets district by name.
     *
     * @param state the state
     * @param name  the name
     * @return the district by name
     */
    public Optional<District> getDistrictByName(State state, String name) {
        if (name == null || state == null) return Optional.empty();

        for (District district : state.getDistricts()) {
            if (district.getName().equals(name))
                return Optional.of(district);
        }

        return Optional.empty();
    }

    /**
     * Gets city by name.
     *
     * @param district the district
     * @param name     the name
     * @return the city by name
     */
    public Optional<City> getCityByName(District district, String name) {
        if (name == null || district == null) return Optional.empty();

        for (City city : district.getCities()) {
            if (city.getName().equals(name))
                return Optional.of(city);
        }

        return Optional.empty();
    }

    /**
     * Deserialize.
     */
    public void deserialize() {
        stateMapper = new StateMapper();
    }
}
