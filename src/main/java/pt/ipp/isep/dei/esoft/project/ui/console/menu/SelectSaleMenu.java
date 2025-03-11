package pt.ipp.isep.dei.esoft.project.ui.console.menu;


import pt.ipp.isep.dei.esoft.project.application.controller.ListPropertiesController;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySaleDTO;
import pt.ipp.isep.dei.esoft.project.ui.console.PurchaseRequestUI;
import pt.ipp.isep.dei.esoft.project.ui.console.ScheduleVisitRequestUI;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

import java.util.ArrayList;
import java.util.List;

import static pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils.showPropertySaleList;

public class SelectSaleMenu implements Runnable {
    public SelectSaleMenu() {
    }

    AuthenticationController authenticationController = new AuthenticationController();
    ListPropertiesController listPropertiesController = new ListPropertiesController();

    public void run() {

        List<MenuItem> options = new ArrayList<>();
        options.add(new MenuItem("Make a Visit request", new ScheduleVisitRequestUI()));
        options.add(new MenuItem("Make a Purchase request", new PurchaseRequestUI()));

        ErrorOptional<PropertySaleDTO> selectedProperty = listPropertiesController.getSelectedProperty();
        if (selectedProperty.hasError()) {
            System.out.println(selectedProperty.getErrorMessage() + "\nOperation Failed!");
            return;
        }

        int option;

        do {
            List<PropertySaleDTO> list = new ArrayList<>();
            list.add(selectedProperty.get());

            String prompt = "==SELECTED SALE==================================================================================================================================================================================================================================================================================";
            showPropertySaleList(list, prompt);


            option = Utils.showAndSelectIndex(options, "\n Sale interactions");
            if ((option >= 0) && (option < options.size()))
                options.get(option).run();


            if (option == 3)
                option = -2;

        } while ((option != -1));
    }
}
