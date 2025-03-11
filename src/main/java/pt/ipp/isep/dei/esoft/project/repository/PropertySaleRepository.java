package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.property.*;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.PropertySale;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.PropertySaleRequest;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.PropertySaleMapper;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySaleDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySaleRequestDTO;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The type Property sale repository.
 */
public class PropertySaleRepository implements Serializable {
    /**
     * list of properties that are listed
     */
    List<PropertySale> propertySales = new ArrayList<>();

    private transient PropertySaleMapper mapper;

    /**
     * Instantiates a new Property sale repository.
     */
    public PropertySaleRepository() {
        mapper = new PropertySaleMapper();
    }

    /**
     * Method used to create a new property sale that is going to be listed in the application.
     *
     * @param propertySaleDTO the property sale dto
     * @return operation result
     */
    public OperationResult createPropertySale(PropertySaleDTO propertySaleDTO) {

        try {
            ErrorOptional<PropertySale> propertySale = mapper.toDomain(propertySaleDTO);
            if (propertySale.hasError()) {
                return OperationResult.failed(propertySale.getErrorMessage() + "\n Error - Repository - Failed to create PropertySale!");
            }

            return addPropertySale(propertySale.get());

        } catch (IllegalArgumentException e) {
            return OperationResult.failed(e.getLocalizedMessage());
        }
    }


    /**
     * List properties list.
     * Used to list properties.
     *
     * @param sortFilterOptions the sort filter options
     * @return the list
     */
    public List<PropertySaleDTO> listProperties(SortFilterOptions sortFilterOptions) {
        List<PropertySale> list = filterProperties(sortFilterOptions);

        sortProperties(list, sortFilterOptions);

        List<PropertySaleDTO> propertySaleDTOS = new ArrayList<>();
        for (PropertySale propertySale : propertySales) {
            propertySaleDTOS.add(mapper.toDTO(propertySale).get());
        }

        return propertySaleDTOS;
    }

    /**
     * Used to filter properties.
     *
     * @param sortFilterOptions
     * @return
     */

    private List<PropertySale> filterProperties(SortFilterOptions sortFilterOptions) {
        // check if there aren't any filter options

        if (sortFilterOptions.filterBusiness == null && sortFilterOptions.filterPropertyType == null && sortFilterOptions.filterNumberOfBedRooms == -1)
            return new ArrayList<>(propertySales);

        // if there are sort options
        List<PropertySale> list = new ArrayList<>();


        for (PropertySale propertySale : propertySales) {
            // type of business filter
            if (propertySale.getTypeOfBusiness().equals(sortFilterOptions.filterBusiness))
                list.add(propertySale);


                // type of property filter
            else if (sortFilterOptions.filterPropertyType != null) {
                if (sortFilterOptions.filterPropertyType.equals(Property.Type.LAND) && propertySale.getProperty() instanceof Land)
                    list.add(propertySale);

                else if (sortFilterOptions.filterPropertyType.equals(Property.Type.APARTMENT) && propertySale.getProperty() instanceof Apartment)
                    list.add(propertySale);

                else if (sortFilterOptions.filterPropertyType.equals(Property.Type.HOUSE) && propertySale.getProperty() instanceof House)
                    list.add(propertySale);
            }


            // number of bedrooms filter
            else if (sortFilterOptions.filterNumberOfBedRooms <= 0) {
                if (propertySale.getProperty() instanceof ResidentialProperty &&
                        ((ResidentialProperty) propertySale.getProperty()).getNumberOfBedrooms() == sortFilterOptions.filterNumberOfBedRooms)
                    list.add(propertySale);
            }
        }

        return list;
    }

    /**
     * Used to sort properties.
     *
     * @param list
     * @param sortFilterOptions
     */

    private void sortProperties(List<PropertySale> list, SortFilterOptions sortFilterOptions) {
        PropertySale.SortByCityComparator sortByCity = new PropertySale.SortByCityComparator();
        PropertySale.SortByPriceComparator sortByPrice = new PropertySale.SortByPriceComparator();

        // sort by Price
        if (sortFilterOptions.sortcriteria.equals(SortFilterOptions.SortType.SortByPrice))
            list.sort(sortByPrice);

        // sort by city
        if (sortFilterOptions.sortcriteria.equals(SortFilterOptions.SortType.SortByCity))
            list.sort(sortByCity);

        // reverse
        if (sortFilterOptions.reversed)
            Collections.reverse(list);
    }

    /**
     * Method used to get the property list that are listed in the application.
     *
     * @return property list
     */
    public List<PropertySaleDTO> getPropertySales() {
        List<PropertySaleDTO> list = new ArrayList<>();

        ErrorOptional<PropertySaleDTO> errorOptional;
        for (PropertySale propertySale : propertySales) {
            errorOptional = mapper.toDTO(propertySale);
            if (!errorOptional.hasError())
                list.add(errorOptional.get());
        }

        return list;
    }

    /**
     * Method used to add a property to the list of properties listed in the application.
     *
     * @param propertySale property
     * @return true if added or false if not added
     */
    private OperationResult addPropertySale(PropertySale propertySale) {
        OperationResult localValidation = propertySale.isValid();
        if (!localValidation.success())
            return OperationResult.failed(localValidation.getErrorMessage() + "\nError - Repository - PropertySale failed local validation!");

        OperationResult globalValidation = isValid(propertySale);
        if (!globalValidation.success())
            return OperationResult.failed(globalValidation.getErrorMessage() + "\nError - Repository - PropertySale failed global validation!");

        propertySales.add(0, propertySale);
        Repositories.getStoreRepository().addStorePropertyCount(propertySale.getAgent().getEmail());
        return OperationResult.successfull();
    }

    /**
     * Method used to check if a property is valid to be added in the list.
     *
     * @param propertySale
     * @return true if it is possible to add and false if not
     */
    private OperationResult isValid(PropertySale propertySale) {
        for (PropertySale p : propertySales) {
            if (p.equals(propertySale))
                return OperationResult.failed("Error - Repository - PropertySale already exists!");
        }

        return OperationResult.successfull();
    }

    /**
     * Remove property sale request operation result.
     *
     * @param dto the dto
     * @return the operation result
     */
    public OperationResult removePropertySaleRequest(PropertySaleDTO dto) {
        if (dto == null) {
            return OperationResult.failed("Error - Repository - PropertySaleRequestDTO cannot be null!");
        }

        for (PropertySale propertySale : propertySales) {
            if (propertySale.getID() == dto.id) {
                propertySales.remove(propertySale);
                return OperationResult.successfull();
            }
        }

        return OperationResult.failed("Error - Repository - PropertySaleRequestDTO doesn't exist in the repository!");
    }

    /**
     * Convert request in property sale operation result.
     *
     * @param request       the request
     * @param commissionType the commission type
     * @param commission     the commission
     * @return the operation result
     */
    public OperationResult convertRequestInPropertySale(PropertySaleRequestDTO request, PropertySale.CommissionType commissionType, double commission) {
        ErrorOptional<PropertySale> propertySale = mapper.toDomain(request, commissionType, commission);
        if (propertySale.hasError()) {
            return OperationResult.failed(propertySale.getErrorMessage() + "\nError - Repository - Failed to convert PropertySaleRquestDTO into PropertySale!");
        }

        return addPropertySale(propertySale.get());
    }

    /**
     * Gets property sale from id.
     *
     * @param id the id
     * @return the property sale from id
     */
    public ErrorOptional<PropertySale> getPropertySaleFromID(int id) {
        for (PropertySale propertySale : propertySales) {
            if (propertySale.getID() == id)
                return ErrorOptional.of(propertySale);
        }


        return ErrorOptional.empty("Error - Repository - PropertySaleRepository does not contain PropertySale[" + id + "]");
    }

    /**
     * Deserialize.
     */
    public void deserialize() {
        mapper = new PropertySaleMapper();
    }

    /**
     * The type Sort filter options.
     */
    public static class SortFilterOptions {
        /**
         * The enum Sort type.
         */
        public enum SortType {
            /**
             * Sort by price sort type.
             */
            SortByPrice,
            /**
             * Sort by city sort type.
             */
            SortByCity,
            /**
             * Sort by recently added sort type.
             */
            SortByRecentlyAdded
        }

        /**
         * The Sortcriteria.
         */
        public SortType sortcriteria = SortType.SortByRecentlyAdded;
        /**
         * The Filter business.
         */
        public PropertySaleRequest.TypesOfBusinesses filterBusiness = null;
        /**
         * The Filter number of bed rooms.
         */
        public int filterNumberOfBedRooms = -1;
        /**
         * The Filter property type.
         */
        public Property.Type filterPropertyType = null;
        /**
         * The Reversed.
         */
        public boolean reversed = false;

        /**
         * Reset.
         */
        public void reset() {
            sortcriteria = SortType.SortByRecentlyAdded;
            filterBusiness = null;
            filterNumberOfBedRooms = -1;
            filterPropertyType = null;
            reversed = false;
        }
    }
}
