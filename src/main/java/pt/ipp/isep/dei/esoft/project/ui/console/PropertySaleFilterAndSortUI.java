package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.ListPropertiesController;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.PropertySaleRequest;
import pt.ipp.isep.dei.esoft.project.repository.PropertySaleRepository;

import java.util.List;

import static pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils.*;

/**
 * Class used to interact with the user
 */
public class PropertySaleFilterAndSortUI implements Runnable {
    /**
     * Method used to run the class ListPropertiesUI
     */
    public void run() {
        ListPropertiesController controller = new ListPropertiesController();

        PropertySaleRepository.SortFilterOptions sortFilterOptions = controller.getSortFilterOptions();

        String prompt = "Select option:";
        List<String> filterOrSortList = List.of(new String[]{"Sort", "Filter"});

        int input;
        do {
            input = showAndSelectIndex(filterOrSortList, prompt);

            if (input == 0)
                getSortCriteria(sortFilterOptions);
            if (input == 1)
                getFilterCriteria(sortFilterOptions);


        } while (input != -1);
    }

    /**
     * Method used to get sort criteria.
     *
     * @param sortFilterOptions
     */

    private void getSortCriteria(PropertySaleRepository.SortFilterOptions sortFilterOptions) {
        String prompt = "Choose a Sort Criteria:";
        List<Object> options = List.of(PropertySaleRepository.SortFilterOptions.SortType.values());

        PropertySaleRepository.SortFilterOptions.SortType sortType = (PropertySaleRepository.SortFilterOptions.SortType) showAndSelectOne(options, prompt);

        if (sortType != null)
            sortFilterOptions.sortcriteria = sortType;

        prompt = "Sort in reversed order? (Y/N)";

        sortFilterOptions.reversed = confirm(prompt);
    }

    private void getFilterCriteria(PropertySaleRepository.SortFilterOptions sortFilterOptions) {
        String prompt = "Select filter criteria:";
        List<String> options = List.of(new String[]{"Filter by business type", "Filter by number of bedrooms", "Filter by Recently added"});

        int input = showAndSelectIndex(options, prompt);

        if (input == 0)
            getBusinessType(sortFilterOptions);
        if (input == 1)
            getNumberOfBedrooms(sortFilterOptions);
        if (input == 2)
            sortFilterOptions.sortcriteria = PropertySaleRepository.SortFilterOptions.SortType.SortByRecentlyAdded;
    }

    /**
     * Method used to get business type.
     *
     * @param sortFilterOptions
     */
    private void getBusinessType(PropertySaleRepository.SortFilterOptions sortFilterOptions) {
        String prompt = "Select property Business:";
        List<Object> options = List.of(PropertySaleRequest.TypesOfBusinesses.values());

        sortFilterOptions.filterBusiness = (PropertySaleRequest.TypesOfBusinesses) showAndSelectOne(options, prompt);
    }


    /**
     * Method used to get number of bedrooms.
     *
     * @param sortFilterOptions
     */

    private void getNumberOfBedrooms(PropertySaleRepository.SortFilterOptions sortFilterOptions) {
        String prompt = "Type number of beedrooms (-1 to cancel):";

        sortFilterOptions.filterNumberOfBedRooms = readIntegerFromConsole(prompt);
    }
}
