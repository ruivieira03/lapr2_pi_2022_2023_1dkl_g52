package pt.ipp.isep.dei.esoft.project.ui.console.menu;

import pt.ipp.isep.dei.esoft.project.application.controller.ListNotificationsController;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers.dto.MessageDTO;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers.dto.NotificationDTO;
import pt.ipp.isep.dei.esoft.project.ui.console.ShowMessageUI;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

import java.time.format.DateTimeFormatter;

import static pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils.selectsIndex;

public class NotificationsMenu implements Runnable {

    @Override
    public void run() {
        ListNotificationsController controller = new ListNotificationsController();

        ErrorOptional<NotificationDTO> notifications = controller.getNotifications();
        if (notifications.hasError()) {
            System.out.println(notifications.getErrorMessage() + "\nOperation failed!");
            return;
        }

        int input = 0;

        do {
            printNotifications(notifications.get());
            input = selectsIndex(notifications.get().messages);

            if (input >= 0 && input < notifications.get().messages.size()) {
                controller.setSelectedMessage(notifications.get().messages.get(input));
                new ShowMessageUI().run();
            }


        } while (input > 0);

        controller.setSelectedMessage(null);
    }

    public void printNotifications(NotificationDTO notifications) {
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        System.out.println("Notifications - " + notifications.messages.size());

        int index = 1;
        for (MessageDTO message : notifications.messages) {
            System.out.printf("%d | %s | From: %s%n", index, formatterDate.format(message.date), message.author.name);
            index++;
        }

        System.out.println("\n0 - Exit");

    }
}
