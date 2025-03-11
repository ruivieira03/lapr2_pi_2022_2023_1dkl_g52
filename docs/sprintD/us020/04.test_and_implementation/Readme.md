# US 020 - Read response from appointement

# 4. Tests

**Test 1:** Check that it is not possible to create an instance of the Task class with null values.

	@Test
    



*It is also recommended to organize this content by subsections.*

# 5. Construction (Implementation)


## Class ReadAppointementRequestController

```java
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

        // get
        for (VisitRequest visitRequest : visitRequests) {
            if (visitRequest.getPropertySale().getAgent().hasEmail(agentDTO.email)) {
                list.add(visitRequest);
            }
        }

        // toDTO
        List<VisitRequestDTO> listDTO = new ArrayList<>();

        for (VisitRequest visitRequest : list) {
            ErrorOptional<VisitRequestDTO> dto = mapper.toDTO(visitRequest);
            if (!dto.hasError())
                listDTO.add(dto.get());
        }

        return ErrorOptional.of(listDTO);
    }

    public OperationResult removeVisitRequestFromPropertySale(PropertySaleDTO propertySaleDTO) {
        boolean removedAny = false;

        for (VisitRequest visitRequest : visitRequests) {
            if (visitRequest.getPropertySale().getID() == propertySaleDTO.id) {
                visitRequests.remove(visitRequest);
                removedAny = true;
            }
        }

        for (VisitRequest visitRequest : acceptedVisitRequests) {
            if (visitRequest.getPropertySale().getID() == propertySaleDTO.id) {
                acceptedVisitRequests.remove(visitRequest);
                removedAny = true;
            }
        }

        if (removedAny)
            return OperationResult.successfull();

        return OperationResult.failed("Error - Repository - Cannot remove VisitRequest because none have refer to the PropertySale[" + propertySaleDTO.id + "]");
    }

    public OperationResult removeVisitRequest(VisitRequestDTO dto) {
        for (VisitRequest visitRequest : visitRequests) {
            if (visitRequest.getId() == dto.id) {
                visitRequests.remove(visitRequest);
                return OperationResult.successfull();
            }
        }

        return OperationResult.failed("Error - Repository - Cannot remove VisitRequest[" + dto.id + "] because it doesn't exists!");
    }

    public OperationResult registerVisitRequest(VisitRequestDTO visitRequestDTO) {
        ErrorOptional<VisitRequest> visitRequest = mapper.toDomain(visitRequestDTO);

        if (visitRequest.hasError())
            return OperationResult.failed(visitRequest.getErrorMessage() + "\nErro - Repository - Failed to convert VisitRequestDTO into VisitRequest!");

        return addVisitRequest(visitRequest.get());
    }

    public OperationResult addVisitRequest(VisitRequest visitRequest) {
        OperationResult localValidation = visitRequest.isValid();
        if (!localValidation.success())
            return OperationResult.failed(localValidation.getErrorMessage() + "\nError - Repository - Failed local validation!");

        OperationResult globalValidation = isValid(visitRequest);
        if (!globalValidation.success())
            return OperationResult.failed(globalValidation.getErrorMessage() + "\nError - Repository - Failed global validation!");

        visitRequests.add(visitRequest);
        return OperationResult.successfull();

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

    public String[] getVisitRequestByAgentAsString(AgentDTO agentDTO) {
        List<VisitRequestDTO> list = getVisitRequestListFromAgent(agentDTO).get();
        String[] visitRequests = new String[list.size()];
        int index = 0;

        for ( VisitRequestDTO visitRequestDTO : list) {
            visitRequests[index] = visitRequestDTO.toString();
            index++;
        }


        return visitRequests;
    }

    public void deserialize() {
        mapper = new VisitRequestMapper();
    }
}


```


## Class VisitRequest
````java
public class VisitRequest implements Serializable {

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
## Class VisitRequestResponse
````java
public class VisitRequestReponse extends Message{
    VisitRequest visitRequest;
    public VisitRequestReponse() {
    }
    public VisitRequest getVisitRequest() {
        return visitRequest;
    }
    public void setVisitRequest(VisitRequest visitRequest) {
        this.visitRequest = visitRequest;
    }
    @Override
    public String toString() {
        return super.toString() + visitRequest.toString();
    }

    public OperationResult isValid() {
        if(!visitRequest.isValid().success()){
            return OperationResult.failed(visitRequest.isValid().getErrorMessage() + "Error - VisitRequestResponse  - Visit request must be valid!");
        }
        return OperationResult.successfull();
    }
}
````

# 6. Integration and Demo


# 7. Observations


