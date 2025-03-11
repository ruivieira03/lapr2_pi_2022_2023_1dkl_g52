package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.ListPropertySoldController;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.PropertySold;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySoldDTO;
import pt.ipp.isep.dei.esoft.project.repository.PropertySoldRepository;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

import java.util.List;

import static pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils.showAndSelectIndex;
import static pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils.showPropertySoldList;

/**
 * The type List property sold ui.
 */
public class ListPropertySoldUI implements Runnable {

    public void run() {
        ListPropertySoldController controller = new ListPropertySoldController();

        String prompt = " ========= List of properties sold sorted by area =========\n";

        PropertySoldRepository.AlgorithmOptions algorithmOptions = controller.getAlgorithmOptions();

        getSortAlgorithm(algorithmOptions);
        getSortingOption(algorithmOptions);

        List<PropertySoldDTO> propertySoldList = controller.listPropertySold(algorithmOptions);

        showPropertySoldList(propertySoldList, prompt);
    }


    /**
     * Gets sort algorithm.
     *
     * @param algorithmOptions the algorithm options
     */
    public void getSortAlgorithm(PropertySoldRepository.AlgorithmOptions algorithmOptions) {
        String prompt = "Choose the algorithm that will se used for sorting";
        List<Object> options = List.of(PropertySoldRepository.AlgorithmOptions.SortAlgorithm.values());


        int input;

        input = showAndSelectIndex(options, prompt);

        if (input == 0)
            algorithmOptions.sortAlgorithm = PropertySoldRepository.AlgorithmOptions.SortAlgorithm.BubbleSort;

        if (input == 1)
            algorithmOptions.sortAlgorithm = PropertySoldRepository.AlgorithmOptions.SortAlgorithm.InsertionSort;
    }

    /**
     * Gets sorting option.
     *
     * @param algorithmOptions the algorithm options
     */
    public void getSortingOption(PropertySoldRepository.AlgorithmOptions algorithmOptions) {
        String prompt = "Select the sorting option";
        List<Object> options = List.of(PropertySoldRepository.AlgorithmOptions.SortType.values());

        int input;

        input = showAndSelectIndex(options, prompt);

        if (input == 0)
            algorithmOptions.sortCriteria = PropertySoldRepository.AlgorithmOptions.SortType.SortByAreaAscending;
        if (input == 1)
            algorithmOptions.sortCriteria = PropertySoldRepository.AlgorithmOptions.SortType.SortByAreaDescending;
    }
}