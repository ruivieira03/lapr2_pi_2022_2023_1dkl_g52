package pt.ipp.isep.dei.esoft.project.util.dispatch;

import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySaleDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.VisitRequestDTO;

public class MessageBuilder {
    public static String propertySalePostedByAgent(PropertySaleDTO propertySaleDTO) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Your Property was posted for sale!\n");
        stringBuilder.append("Property: \n");
        stringBuilder.append("Street: " + propertySaleDTO.property.street + "\n");
        stringBuilder.append("ZipCode: " + propertySaleDTO.property.zipCode + "\n");
        stringBuilder.append("State: " + propertySaleDTO.property.state + "\n");
        stringBuilder.append("District: " + propertySaleDTO.property.district + "\n");
        stringBuilder.append("City: " + propertySaleDTO.property.city + "\n");
        stringBuilder.append("Requested price: " + propertySaleDTO.requestedPrice + "\n\n");
        stringBuilder.append("Contact info: "  + propertySaleDTO.agent.name + " - " + propertySaleDTO.agent.phoneNumber + "\n");

        return stringBuilder.toString();
    }
    public static String AcceptedVisitRequest(VisitRequestDTO visitRequestDTO, String message) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Your visit request has been accepted!\n");
        stringBuilder.append("Property: \n");
        stringBuilder.append("Street: " + visitRequestDTO.propertySale.property.street + "\n");
        stringBuilder.append("ZipCode: " + visitRequestDTO.propertySale.property.zipCode + "\n");
        stringBuilder.append("State: " + visitRequestDTO.propertySale.property.state + "\n");
        stringBuilder.append("District: " + visitRequestDTO.propertySale.property.district + "\n");
        stringBuilder.append("City: " + visitRequestDTO.propertySale.property.city + "\n");
        stringBuilder.append("Requested price: " + visitRequestDTO.propertySale.price + "\n\n");
        stringBuilder.append("Visit: \n");
        stringBuilder.append("Start date: " + visitRequestDTO.visitStart + "\n");
        stringBuilder.append("End date: " + visitRequestDTO.visitEnd + "\n");
        stringBuilder.append("Reason: " + message + "\n\n");
        stringBuilder.append("Contact info: "  + visitRequestDTO.propertySale.agent.name + " - " + visitRequestDTO.propertySale.agent.phoneNumber + "\n");

        return stringBuilder.toString();
    }

    public static String DeclinedVisitRequest(VisitRequestDTO visitRequestDTO, String message) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Your visit request has been declined!\n");
        stringBuilder.append("Property: \n");
        stringBuilder.append("Street: " + visitRequestDTO.propertySale.property.street + "\n");
        stringBuilder.append("ZipCode: " + visitRequestDTO.propertySale.property.zipCode + "\n");
        stringBuilder.append("State: " + visitRequestDTO.propertySale.property.state + "\n");
        stringBuilder.append("District: " + visitRequestDTO.propertySale.property.district + "\n");
        stringBuilder.append("City: " + visitRequestDTO.propertySale.property.city + "\n");
        stringBuilder.append("Requested price: " + visitRequestDTO.propertySale.price + "\n\n");
        stringBuilder.append("Visit: \n");
        stringBuilder.append("Start date: " + visitRequestDTO.visitStart + "\n");
        stringBuilder.append("End date: " + visitRequestDTO.visitEnd + "\n");
        stringBuilder.append("Reason: " + message + "\n\n");
        stringBuilder.append("Contact info: "  + visitRequestDTO.propertySale.agent.name + " - " + visitRequestDTO.propertySale.agent.phoneNumber + "\n");

        return stringBuilder.toString();
    }

}
