package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.ListPropertiesController;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySaleDTO;
import pt.ipp.isep.dei.esoft.project.repository.PropertySaleRepository;
import pt.ipp.isep.dei.esoft.project.ui.console.menu.SelectSaleMenu;

import java.util.List;
import java.util.Scanner;

import static pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils.selectsObject;

/**
 * The type Select sale ui.
 */
public class SelectSaleUI implements Runnable {
    /**
     * The Controller.
     */
    ListPropertiesController controller = new ListPropertiesController();

    /**
     * Instantiates a new Select sale ui.
     */
    public SelectSaleUI() {
    }

    public void run() {
        // check if usr is logged in
        if (!controller.isLoggedIn()) {
            System.out.println("Log in or register in the aplication to use this feature!");
            System.out.println("Press enter to continue");
            new Scanner(System.in).nextLine();
            System.out.println("\n\n");
            return;
        }

        PropertySaleRepository.SortFilterOptions sortFilterOptions = controller.getSortFilterOptions();
        List<PropertySaleDTO> propertySaleDTOList = controller.listProperties(sortFilterOptions);

        PropertySaleDTO selectedProperty;
        do {
            System.out.println("Select one of the previously showed properties:");
            selectedProperty = (PropertySaleDTO) selectsObject(propertySaleDTOList);
        } while (selectedProperty == null);

        controller.setSelectedProperty(selectedProperty);

        new SelectSaleMenu().run();

        controller.setSelectedProperty(null);
    }

    /**
     * Select property sale.
     */
    public void selectPropertySale() {


    }
}
