package pt.ipp.isep.dei.esoft.project.ui.console.menu;

import pt.ipp.isep.dei.esoft.project.ui.console.ListPropertySoldUI;
import pt.ipp.isep.dei.esoft.project.ui.console.ListEmployeeUI;
import pt.ipp.isep.dei.esoft.project.ui.console.NetworkStoresSplitUI;
import pt.ipp.isep.dei.esoft.project.ui.console.RegressionUI;

import java.util.ArrayList;
import java.util.List;

import static pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils.showAndSelectIndex;

public class NetworkManagerMenu implements Runnable{
    @Override
    public void run() {

        List<MenuItem> options = new ArrayList<>();

        options.add(new MenuItem("List Properties", new PropertySaleListMenu()));
        options.add(new MenuItem("List all employees", new ListEmployeeUI()));
        options.add(new MenuItem("List Sold Properties", new ListPropertySoldUI()));
        options.add(new MenuItem("Split Stores List",new NetworkStoresSplitUI()));
        options.add(new MenuItem("Regression study", new RegressionUI()));

        int option = 0;
        do {
            option = showAndSelectIndex(options, "\n\nNetwork Manager Menu:");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);

    }

    public NetworkManagerMenu(){
    }


}

