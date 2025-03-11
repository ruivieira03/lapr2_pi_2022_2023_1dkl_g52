# US 016 - Review visit request

# 4. Tests

**Test 1:** Check that it is not possible to create an instance of the Task class with null values.

	@Test
    



*It is also recommended to organize this content by subsections.*

# 5. Construction (Implementation)


## Class ReviewVisitRequestController

```java
public class ReviewVisitRequestsController {

    private final Repositories repositories;

    public ReviewVisitRequestsController() {
        repositories = Repositories.getInstance();
    }

    public ErrorOptional<List<VisitRequestDTO>> getPropertySaleRequestListFromAgent() {
        ErrorOptional<AgentDTO> agent = getAgentFromSession();

        if (agent.hasError()) return ErrorOptional.empty(agent.getErrorMessage());

        VisitRequestRepository visitRequestRepository = Repositories.getVisitRequestRepository();
        return visitRequestRepository.getVisitRequestListFromAgent(agent.get());
    }

    private ErrorOptional<AgentDTO> getAgentFromSession() {
        AuthenticationRepository authenticationRepository = Repositories.getAuthenticationRepository();
        StoreRepository storeNetworkRepository = Repositories.getStoreRepository();

        UserSession userSession = authenticationRepository.getCurrentUserSession();

        String agenteEmail = userSession.getUserId().toString();

        return storeNetworkRepository.getAgentDTOFromEmail(agenteEmail);
    }

    public OperationResult addAcceptedRequests(VisitRequestDTO visitRequestDTO) {
        VisitRequestRepository visitRequestRepository = Repositories.getVisitRequestRepository();

        OperationResult operationResultAdd = visitRequestRepository.addAcceptedVisitRequest(visitRequestDTO);

        if (!operationResultAdd.success()) {
            return OperationResult.failed(operationResultAdd.getErrorMessage() + "\nError - Controller - Failed to add the visitRequest in the repository!");
        }

        return OperationResult.successfull();
    }

    public OperationResult sendEmailToClient(ClientDTO client, MessageDTO messageDTO, String subject, VisitRequestDTO visitRequestDTO) {
        EmailServices emailServices = new EmailServices();
        messageDTO.author = getAgentFromSession().get();
        messageDTO.date = LocalDateTime.now();
        if (emailServices.isEmailValid(messageDTO.author.email) && emailServices.isEmailValid(client.email)) {
            return emailServices.createFile(client.email, subject, messageDTO.message, visitRequestDTO);
        } else {
            return OperationResult.failed("Client and/or agent Emails are not valid");
        }
    }

    public OperationResult addDeclinedVisitRequest(VisitRequestDTO visitRequestDTO) {
        VisitRequestRepository visitRequestRepository = Repositories.getVisitRequestRepository();

        return visitRequestRepository.addDeclinedVisitRequest(visitRequestDTO);
    }

    public OperationResult sendResponseToClient(ClientDTO clientDTO, VisitRequestResponseDTO visitRequestResponseDTO) {
        ClientRepository clientRepository = Repositories.getClientRepository();

        return clientRepository.notifyClient(clientDTO, visitRequestResponseDTO);
    }
}
```



## Class VisitRequestRepository

```java
public class VisitRequestRepository implements Serializable {

List<VisitRequest> visitRequests = new ArrayList<>();
        List<VisitRequest> acceptedVisitRequests = new ArrayList<>();
        List<VisitRequest> declinedVisitRequests = new ArrayList<>();

private transient VisitRequestMapper mapper = new VisitRequestMapper();

public ErrorOptional<List<VisitRequestDTO>> getVisitRequestListFromAgent(AgentDTO agentDTO) {
        List<VisitRequest> list = new ArrayList<>();

        
        for (VisitRequest visitRequest : visitRequests) {
        if (visitRequest.getPropertySale().getAgent().hasEmail(agentDTO.email)) {
        list.add(visitRequest);
        }
        }

        
        List<VisitRequestDTO> listDTO = new ArrayList<>();

        for (VisitRequest visitRequest : list) {
        ErrorOptional<VisitRequestDTO> dto = mapper.toDTO(visitRequest);
        if (!dto.hasError())
        listDTO.add(dto.get());
        }

        return ErrorOptional.of(listDTO);
        }

private OperationResult isValid(VisitRequest visitRequest) {
        for (VisitRequest v : visitRequests) {
        if (v.equals(visitRequest))
        return OperationResult.failed("Error - Reporitory - VisitRequest already exists!");

        else if (v.getClient().equals(visitRequest.getClient())) {
        if (visitRequest.overlaps(v))
        return OperationResult.failed("Error - Reporitory - VisitRequest overlaps an existing request!");
        }
        }

        return OperationResult.successfull();
        }

public OperationResult addAcceptedVisitRequest(VisitRequestDTO visitRequestDTO) {
        ErrorOptional<VisitRequest> visitRequest = mapper.toDomain(visitRequestDTO);

        if(visitRequest.hasError()){
        return OperationResult.failed(visitRequest.getErrorMessage() + "\nErro - Repository - Failed to convert VisitRequestDTO into VisitRequest!");
        }

        acceptedVisitRequests.add(visitRequest.get());
        visitRequests.remove(visitRequestDTO.id-1);

        return OperationResult.successfull();
        }

public OperationResult addDeclinedVisitRequest(VisitRequestDTO visitRequestDTO) {
        ErrorOptional<VisitRequest> visitRequest = mapper.toDomain(visitRequestDTO);

        if(visitRequest.hasError()){
        return OperationResult.failed(visitRequest.getErrorMessage() + "\nErro - Repository - Failed to convert VisitRequestDTO into VisitRequest!");
        }

        declinedVisitRequests.add(visitRequest.get());
        visitRequests.remove(visitRequestDTO.id-1);

        return OperationResult.successfull();
        }
        }
```


## Class VisitRequest
````java
public class VisitRequest {
    private final int id;
    private Client client;
    private Message message;
    private PropertySale propertySale;

    private LocalDateTime visitStart;

    private LocalDateTime visitEnd;

    private static int CURRENT_ID = 1;

    public VisitRequest() {
        this.id = CURRENT_ID;
        CURRENT_ID++;
    }

    public int getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public PropertySale getPropertySale() {
        return propertySale;
    }

    public void setPropertySale(PropertySale propertySale) {
        this.propertySale = propertySale;
    }

    public LocalDateTime getVisitStart() {
        return visitStart;
    }

    public void setVisitStart(LocalDateTime visitStart) {
        this.visitStart = visitStart;
    }

    public LocalDateTime getVisitEnd() {
        return visitEnd;
    }

    public void setVisitEnd(LocalDateTime visitEnd) {
        this.visitEnd = visitEnd;
    }

    public boolean overlaps(VisitRequest other) {
        return (!this.visitStart.isAfter(other.visitStart) && !this.visitEnd.isBefore(other.visitStart)) ||
                (!this.visitStart.isAfter(other.visitEnd) && !this.visitEnd.isBefore(other.visitEnd));
    }

    public OperationResult isValid() {
        if (this.client == null)
            return OperationResult.failed("Error - VisitRequest - Client cannot be null!");

        if (this.propertySale == null)
            return OperationResult.failed("Error - VisitREquest - PropertySale cannot be null!");

        if (this.visitStart.isAfter(this.visitEnd))
            return OperationResult.failed(("Error - VisitRequest - VisitStart must be before visitEnd!"));

        return OperationResult.successfull();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof VisitRequest)) return false;

        VisitRequest v = (VisitRequest) obj;
        return v.client.equals(client) &&
                v.propertySale.equals(propertySale) &&
                v.visitEnd.equals(visitStart) &&
                v.visitEnd.equals(visitEnd);
    }
}


````

# 6. Integration and Demo
n/a

# 7. Observations
n/a

