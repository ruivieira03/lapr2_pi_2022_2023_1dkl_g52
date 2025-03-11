package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.ScheduleVisitRequestController;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySaleDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.VisitRequestDTO;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The type Schedule visit request ui.
 */
public class ScheduleVisitRequestUI implements Runnable {
    public void run() {
        ScheduleVisitRequestController controller = new ScheduleVisitRequestController();

        ErrorOptional<PropertySaleDTO> selectedPropertySale = controller.getSelectedPropertySaleDTO();
        if (selectedPropertySale.hasError()) {
            System.out.println(selectedPropertySale.getErrorMessage() + "\nOperation Failed!");
            return;
        }

        VisitRequestDTO dto = new VisitRequestDTO();

        dto.propertySale = selectedPropertySale.get();

        String prompt = "Type the visit date:";
        LocalDate visitDate = Utils.readLocalDate(prompt);

        prompt = "Type the start of the Visit:";
        LocalTime visitStartTime = Utils.readLocalTime(prompt);

        prompt = "Type the end of the Visit:";
        LocalTime visitEndTime = Utils.readLocalTime(prompt);

        dto.visitStart = visitDate.atTime(visitStartTime);
        dto.visitEnd = visitDate.atTime(visitEndTime);

        OperationResult operationResult = controller.registerVisitRequest(dto);
        if (operationResult.success())
            System.out.println("Operation Successfull!");

        else
            System.out.println(operationResult.getErrorMessage() + "\nOperation Failed!");

    }
}
