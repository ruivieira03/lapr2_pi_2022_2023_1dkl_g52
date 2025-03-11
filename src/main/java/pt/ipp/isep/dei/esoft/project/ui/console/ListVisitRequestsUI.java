package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.ReviewVisitRequestsController;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.VisitRequestDTO;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ListVisitRequestsUI implements Runnable {
    private LocalDate beginDate;
    private LocalDate endDate;

    @Override
    public void run() {
        while (true) {
            requestDates();

            if (dataIsConfirmed()) {
                ReviewVisitRequestsController ctrl = new ReviewVisitRequestsController();
                List<VisitRequestDTO> visitRequests = ctrl.getVisitRequests(beginDate, endDate);

                if (visitRequests.isEmpty()) {
                    System.out.println("\nNo visit requests found for the given dates.");
                } else {
                    Utils.showVisitRequestsList(visitRequests, "Visit Requests:");
                }
                break;
            }
        }

    }

    private void requestDates() {
        beginDate = Utils.readDateFromConsole("Please insert the begin date (dd/mm/yyyy):");
        endDate = Utils.readDateFromConsole("Please insert the end date (dd/mm/yyyy):");
    }

    private boolean dataIsConfirmed() {
        System.out.println("\nBegin date: " + beginDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        System.out.println("End date: " + endDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        return Utils.confirm("Are these dates correct? (Y/N)");
    }
}
