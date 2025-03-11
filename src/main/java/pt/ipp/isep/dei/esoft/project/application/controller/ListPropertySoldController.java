package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySoldDTO;
import pt.ipp.isep.dei.esoft.project.repository.PropertySoldRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

import java.util.List;

/**
 * The type List property sold controller.
 */
public class ListPropertySoldController {

    private static final PropertySoldRepository.AlgorithmOptions algorithmOptions = new PropertySoldRepository.AlgorithmOptions();

    /**
     * Instantiates a new List property sold controller.
     */
    public ListPropertySoldController() {

    }

    /**
     * Method used to list all properties sold.
     *
     * @param algorithmOptions the algorithm options
     * @return the list
     */
    public List<PropertySoldDTO> listPropertySold (PropertySoldRepository.AlgorithmOptions algorithmOptions) {
        PropertySoldRepository propertySoldRepository = Repositories.getPropertySoldRepository();

        return propertySoldRepository.listPropertySold(algorithmOptions);
    }


    /**
     * Get algorithm options property sold repository . algorithm options.
     *
     * @return the property sold repository . algorithm options
     */
    public PropertySoldRepository.AlgorithmOptions getAlgorithmOptions(){
        return algorithmOptions;
    }
}
