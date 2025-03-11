package pt.ipp.isep.dei.esoft.project.ui.console.menu;

import pt.ipp.isep.dei.esoft.project.application.controller.ListNotificationsController;
import pt.ipp.isep.dei.esoft.project.ui.console.CreatePropertySaleRequestUI;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ClientMenu implements Runnable {

    public ClientMenu() {

    }

    public void run() {
        List<MenuItem> options = new ArrayList<>();

        ListNotificationsController controller = new ListNotificationsController();

        options.add(new MenuItem("List Properties", new PropertySaleListMenu()));
        options.add(new MenuItem("Make a property sale request", new CreatePropertySaleRequestUI()));
        options.add(new MenuItem("Notifications(" + controller.getNumberOfNotifications() + ")", new NotificationsMenu()));


        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\nCLient Menu:");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
                updateNotifications(options);
            }
        } while (option != -1);

    }

    public void updateNotifications(List<MenuItem> options) {
        ListNotificationsController controller = new ListNotificationsController();
        options.remove(2);
        options.add(new MenuItem("Notifications(" + controller.getNumberOfNotifications() + ")", new NotificationsMenu()));
    }
}
