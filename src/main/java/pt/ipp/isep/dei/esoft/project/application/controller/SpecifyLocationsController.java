package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.StateDTO;
import pt.ipp.isep.dei.esoft.project.repository.LocationsRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

/**
 * The type Specify locations controller.
 */
public class SpecifyLocationsController {

    private final Repositories repositories;

    /**
     * Instantiates a new Specify locations controller.
     */
    public SpecifyLocationsController() {
        this.repositories = Repositories.getInstance();
    }


    /**
     * Create state method.
     *
     * @param stateDTO the state dto
     * @return the operation result
     */
    public OperationResult createState(StateDTO stateDTO) {
        LocationsRepository locationsRepository = Repositories.getLocationsRepository();
        return locationsRepository.createState(stateDTO);
    }
}
