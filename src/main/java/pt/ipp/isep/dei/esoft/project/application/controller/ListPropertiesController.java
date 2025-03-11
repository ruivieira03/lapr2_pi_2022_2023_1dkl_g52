package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySaleDTO;
import pt.ipp.isep.dei.esoft.project.repository.AuthenticationRepository;
import pt.ipp.isep.dei.esoft.project.repository.PropertySaleRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

import java.util.List;

/**
 * The type List properties controller.
 */
public class ListPropertiesController {

    private static PropertySaleDTO selectedProperty;

    private static final PropertySaleRepository.SortFilterOptions sortFilterOptions = new PropertySaleRepository.SortFilterOptions();


    /**
     * Instantiates a new List properties controller.
     */
    public ListPropertiesController() {
    }

    /**
     * Method used to display the list properties with sort criteria
     *
     * @param sortFilterOptions the sort filter options
     * @return list of properties sorted
     */
    public List<PropertySaleDTO> listProperties(PropertySaleRepository.SortFilterOptions sortFilterOptions) {

        PropertySaleRepository propertySaleRepository = Repositories.getPropertySaleRepository();

        return propertySaleRepository.listProperties(sortFilterOptions);
    }

    /**
     * Is logged in boolean.
     *
     * @return the boolean
     */
    public boolean isLoggedIn() {
        AuthenticationRepository authenticationRepository = Repositories.getAuthenticationRepository();

        return authenticationRepository.getCurrentUserSession().isLoggedIn();
    }


    /**
     * Sets selected property.
     *
     * @param selectedPropertyDTO the selected property dto
     */
    public void setSelectedProperty(PropertySaleDTO selectedPropertyDTO) {
        selectedProperty = selectedPropertyDTO;
    }

    /**
     * Gets selected property.
     *
     * @return the selected property
     */
    public ErrorOptional<PropertySaleDTO> getSelectedProperty() {
        if (selectedProperty == null)
            return ErrorOptional.empty("Error - Controller - PropertySale not selected!");

        return ErrorOptional.of(selectedProperty);
    }

    /**
     * Gets sort filter options.
     *
     * @return the sort filter options
     */
    public PropertySaleRepository.SortFilterOptions getSortFilterOptions() {
        return sortFilterOptions;
    }

}
