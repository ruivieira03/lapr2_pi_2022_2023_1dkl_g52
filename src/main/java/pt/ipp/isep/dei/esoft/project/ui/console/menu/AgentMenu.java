package pt.ipp.isep.dei.esoft.project.ui.console.menu;

import pt.ipp.isep.dei.esoft.project.ui.console.*;
import pt.ipp.isep.dei.esoft.project.ui.console.ListPropertySaleRequestsUI;
import pt.ipp.isep.dei.esoft.project.ui.console.ListPurchaseRequestUI;
import pt.ipp.isep.dei.esoft.project.ui.console.RegisterPropertySaleUI;
/*import pt.ipp.isep.dei.esoft.project.ui.console.ReviewVisitRequestsUI;*/
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class AgentMenu implements Runnable {
    public AgentMenu() {
    }

    public void run() {
        List<MenuItem> options = new ArrayList<>();

        options.add(new MenuItem("List Properties", new PropertySaleListMenu()));
        options.add(new MenuItem("Register Property Sale", new RegisterPropertySaleUI()));
        options.add(new MenuItem("Manage Property Sale Requests", new ListPropertySaleRequestsUI()));
        options.add(new MenuItem("Manage Purchase Requests", new ListPurchaseRequestUI()));
        options.add(new MenuItem("List Visit Requests", new ListVisitRequestsUI()));
        options.add(new MenuItem("Manage Visit Requests", new ReviewVisitRequestsUI()));

        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\nAgent Menu:");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}
