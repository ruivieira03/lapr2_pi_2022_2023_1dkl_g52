package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.Message;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.Notification;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.VisitRequestReponse;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers.dto.MessageDTO;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.AgentDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.VisitRequest;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySaleDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySaleRequestDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.VisitRequestDTO;
import pt.ipp.isep.dei.esoft.project.repository.*;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

import java.time.LocalDateTime;
import java.util.List;

public class ReadAppoitementRequestController {
    private final Repositories repositories;
    private VisitRequestRepository visitRequestRepository;
    private AuthenticationRepository authenticationRepository;
    private VisitRequestReponse visitRequestReponse;
    private Notification notification;
    private Message message;
    public ReadAppoitementRequestController() {
        repositories = Repositories.getInstance();
    }

    public String getClientFromUserSession() {

        return visitRequestReponse.getAuthor().getEmail();
    }

    public VisitRequest getVisitRequest(){
        return visitRequestReponse.getVisitRequest();
    }

    public List<Message> getNotifications(){
        return notification.getMessages();
    }
    public OperationResult addDeclinedVisitRequest(VisitRequestDTO visitRequestDTO){

        return visitRequestRepository.addDeclinedVisitRequest(visitRequestDTO);
    }

}

