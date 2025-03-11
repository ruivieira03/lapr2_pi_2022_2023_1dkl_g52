package pt.ipp.isep.dei.esoft.project.ui.console.menu;


import pt.ipp.isep.dei.esoft.project.ui.console.ImportLegacyDataUI;
import pt.ipp.isep.dei.esoft.project.ui.console.RegisterEmployeeUI;
import pt.ipp.isep.dei.esoft.project.ui.console.SpecifyLocationsUI;

import java.util.ArrayList;
import java.util.List;

import static pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils.showAndSelectIndex;

public class AdminMenu implements Runnable {
    public AdminMenu() {
    }

    public void run() {
        List<MenuItem> options = new ArrayList<>();

        options.add(new MenuItem("List Properties", new PropertySaleListMenu()));
        options.add(new MenuItem("Register Employee", new RegisterEmployeeUI()));
        options.add(new MenuItem("Add location", new SpecifyLocationsUI()));
        options.add(new MenuItem("Import Legacy Data", new ImportLegacyDataUI()));
        int option = 0;
        do {
            option = showAndSelectIndex(options, "\n\nAdmin Menu:");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}
