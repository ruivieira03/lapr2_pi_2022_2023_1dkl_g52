package pt.ipp.isep.dei.esoft.project.ui.console.menu;

import pt.ipp.isep.dei.esoft.project.application.controller.ListPropertiesController;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySaleDTO;
import pt.ipp.isep.dei.esoft.project.repository.PropertySaleRepository;

import pt.ipp.isep.dei.esoft.project.ui.console.PropertySaleFilterAndSortUI;
import pt.ipp.isep.dei.esoft.project.ui.console.SelectSaleUI;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils.showPropertySaleList;

public class PropertySaleListMenu implements Runnable {
    public PropertySaleListMenu() {
    }

    public void run() {
        ListPropertiesController controller = new ListPropertiesController();

        // filter and sort Criteria
        PropertySaleRepository.SortFilterOptions sortFilterOptions = controller.getSortFilterOptions();

        List<MenuItem> options = new ArrayList<>();
        options.add(new MenuItem("Filter/Sort", new PropertySaleFilterAndSortUI()));
        options.add(new MenuItem("Select", new SelectSaleUI()));


        int option;
        do {
            System.out.println("=======AVAILABLE SALES=======");
            List<PropertySaleDTO> properties = controller.listProperties(sortFilterOptions);
            showPropertySaleList(properties,getPrompt(sortFilterOptions));

            option = Utils.showAndSelectIndex(options, "\nOptions");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);

        sortFilterOptions.reset();
    }

    private static String getPrompt(PropertySaleRepository.SortFilterOptions sortFilterOptions) {
        String prompt = "List of properties:";

        if (sortFilterOptions.sortcriteria == PropertySaleRepository.SortFilterOptions.SortType.SortByPrice) {
            prompt += " | Sorted by price |";
        }

        if (sortFilterOptions.sortcriteria == PropertySaleRepository.SortFilterOptions.SortType.SortByCity) {
            prompt += " | Sorted by city |";
        }
        if (sortFilterOptions.sortcriteria == PropertySaleRepository.SortFilterOptions.SortType.SortByRecentlyAdded) {
            prompt += " | Sorted by recently added |";
        }


        if (sortFilterOptions.filterBusiness != null) {
            prompt += " | Filtered by " + sortFilterOptions.filterBusiness.name().toLowerCase() + " |";
        }
        if (sortFilterOptions.filterPropertyType != null) {
            prompt += " | Filetered by " + sortFilterOptions.filterPropertyType.name().toLowerCase() +  " | ";
        }

        if (sortFilterOptions.filterNumberOfBedRooms > 0) {
            prompt += " | Filtered by " + sortFilterOptions.filterNumberOfBedRooms + " beedrooms |";
        }


        return prompt;
    }
}
