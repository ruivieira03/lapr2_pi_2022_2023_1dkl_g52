package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.ListNotificationsController;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers.dto.MessageDTO;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

import java.time.format.DateTimeFormatter;

import static pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils.readLineFromConsole;

/**
 * The type Show message ui.
 */
public class ShowMessageUI implements Runnable{


    @Override
    public void run() {
        ListNotificationsController controller = new ListNotificationsController();

        MessageDTO message = controller.getSelectedMessage();
        if (message == null) {
            System.out.println("There's no Selected message! \nOperation Failed");
            return;
        }
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.out.printf("Date : %s%nAuthor: %s%n----------------------------------------------------------------------%n%s", formatterDate.format(message.date), message.author.name, message.message);

        String prompt = "\n\nPress enter to exit";
        readLineFromConsole(prompt);

        OperationResult operationResult = controller.removeMessage(message);
        if (operationResult.success())
            System.out.println("Operation Succesfull!");
        else
            System.out.println(operationResult.getErrorMessage() + "\nOperation Failed!");
    }
}
