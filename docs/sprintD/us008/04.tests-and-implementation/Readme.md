# US 008 - See the list of property announcements to accept or decline them

# 4. Tests 



	@Test
    void convertRequestInPropertySale() {
        PropertySaleRepository rep = Repositories.getInstance().getPropertySaleRepository();

        OperationResult expected = new OperationResult();
        expected = OperationResult.successful;
        
        ClientDTO clientDTO1 = new ClientDTO("Anne", 123456789, "123-123-1234", "123-12-1234", "adress", "owner@ex.com");
        AgentDTO agentDTO = new Agent("Dave", 123456789, "123-12-1234", "123-123-1234", "street", "agent@ex.com");
        LandDTO landDTO = new Land(1200, 16, "Dirt Road plot 6", 43286, alabama, montgomery, montgomeryCity, "photos.png", clientDTO1)
        PropertySaleRequestDTO request = new PropertySaleRequestDTO();
        
        request.setProperty(landDTO)
        request.setRequestedPrice(2000)
        request.setTypeOfBusiness(PropertySaleRequest.TypeOfBusiness.RENT)
        request.setAgent(agentDTO)
        
        assertEquals(expected, rep.convertRequestInPropertySale(request, PropertySale.CommissionType.FIXED, 200))
    }




*It is also recommended to organize this content by subsections.* 

# 5. Construction (Implementation)


## Class ListPropertySaleController

```java
public class ListPropertySaleRequestsController {

    private final Repositories repositories;

    public ListPropertySaleRequestsController() {
        repositories = Repositories.getInstance();
    }

    public ErrorOptional<List<PropertySaleRequestDTO>> getPropertySaleRequestListFromAgent() {
        ErrorOptional<AgentDTO> agent = getAgentFromSession();

        if (agent.hasError()) return ErrorOptional.empty(agent.getErrorMessage());

        PropertySaleRequestRepository propertySaleRequestRepository = Repositories.getPropertySaleRequestRepository();
        return propertySaleRequestRepository.listPropertySaleRequests(agent.get());
    }
    
    public OperationResult removePropertySaleRequest(PropertySaleRequestDTO propertySaleRequestDTO) {
        PropertySaleRequestRepository propertySaleRequestRepository = Repositories.getPropertySaleRequestRepository();

        return propertySaleRequestRepository.removePropertySaleRequest(propertySaleRequestDTO);
    }

    public  OperationResult createPropertySale(PropertySaleRequestDTO propertySaleRequestDTO, PropertySale.CommissionType commissionType, double commission){
        PropertySaleRepository propertySaleRepository = Repositories.getPropertySaleRepository();

        OperationResult operationResult = propertySaleRepository.convertRequestInPropertySale(propertySaleRequestDTO,commissionType,commission);
        if (!operationResult.success())
            return OperationResult.failed(operationResult.getErrorMessage() + "\nError - Controller - Failed to create PropertySale!");

        return removePropertySaleRequest(propertySaleRequestDTO);
    }

    public OperationResult sendMessageToClient(ClientDTO clientDTO, MessageDTO messageDTO) {
        ClientRepository clientRepository = Repositories.getClientRepository();

        ErrorOptional<ClientDTO> client = getClientDTOFromEmail(clientDTO.email.toString());
        ErrorOptional<AgentDTO> agent = getAgentFromSession();

        if (client.hasError())
            return OperationResult.failed(client.getErrorMessage() + "\nError - Controller - Failed to get Client from Email!");
        if (agent.hasError())
            return OperationResult.failed(agent.getErrorMessage() + "\nError - Controller - Failed to get Agent from UserSession!");

        messageDTO.author =  agent.get();
        messageDTO.date = LocalDate.now();

        return clientRepository.notifyClient(client.get(), messageDTO);
    }
}

```

## Class AuthenticationRepository

```java
class AuthenticationRepositoryTest {

    public class AuthenticationRepository {
        private final AuthFacade authenticationFacade = new AuthFacade();

        public boolean doLogin(String email, String pwd) {
            return authenticationFacade.doLogin(email, pwd).isLoggedIn();
        }

        public void doLogout() {
            authenticationFacade.doLogout();
        }

        public UserSession getCurrentUserSession() {
            return authenticationFacade.getCurrentUserSession();
        }

        public boolean addUserRole(String id, String description) {
            return authenticationFacade.addUserRole(id, description);
        }

        public boolean addUserWithRole(String name, String email, String pwd, AuthenticationController.Roles roleId) {
            return authenticationFacade.addUserWithRole(name, email, pwd, roleId.name());
        }
    }

```

## Class ClientsRepository

```java
public class ClientRepository {

    List<Client> clients = new ArrayList<>();

    private final UserMapper userMapper = new UserMapper();

    private final NotificationMapper notificationMapper = new NotificationMapper();
    private final MessageMapper messageMapper = new MessageMapper();
    

    public ErrorOptional<NotificationDTO> getNotificationDTOFromClient(ClientDTO clientDTO) {
        ErrorOptional<Client> client = getClientFromEmail(clientDTO.email);

        if (client.hasError())
            return ErrorOptional.empty(client.getErrorMessage() + "\nError - Repository - Failed to get Client from ClientRepository!");

        Notification notification = client.get().getNotification();

        ErrorOptional<NotificationDTO> notificationDTO = notificationMapper.toDTO(notification);

        if (notificationDTO.hasError())
            return ErrorOptional.empty(notificationDTO.getErrorMessage() + "\nError - Repository - Failed to convert Notification into NotificatioDTO!");

        return ErrorOptional.of(notificationDTO.get());
    }

    public OperationResult notifyClient(ClientDTO clientDTO, MessageDTO messageDTO) {
        ErrorOptional<Client> client = getClientFromEmail(clientDTO.email);

        if (client.hasError())
            return OperationResult.failed(client.getErrorMessage() + "\nError - Repository - Failed to get Client from ClientRepository!");

        ErrorOptional<Message> message = messageMapper.toDomain(messageDTO);

        if (message.hasError())
            return OperationResult.failed(message.getErrorMessage() + "\nError - Repository - Failedd to convert MessageDTO into Message!");

        return client.get().addMessage(message.get());
    }
    
    private OperationResult isValid(Client client) {
        for (Client c : clients) {
            if (c.equals(client))
                return OperationResult.failed("Error - ClientRepository - Client already exists!");
        }

        return OperationResult.successfull();
    }

    public ErrorOptional<Client> getClientFromEmail(String email) {
        for (Client client : clients) {
            if (client.hasEmail(email))
                return ErrorOptional.of(client);
        }

        return ErrorOptional.empty("Error - Repository - ClientRepository does not contain Client[" + email + "]");
    }
    
    public ErrorOptional<Client> getClientFromEmail(Email email) {

        for (Client client : clients) {
            if (client.hasEmail(email))
                return ErrorOptional.of(client);
        }

        return ErrorOptional.empty("Error - Repository - ClientRepository does not contain Client[" + email.toString() + "]");
    }

    public ErrorOptional<ClientDTO> getClientDTOFromEmail(String email) {
        ErrorOptional<Client> client = getClientFromEmail(email);

        if (client.hasError()) return ErrorOptional.empty(client.getErrorMessage());


        ErrorOptional<UserDTO> dto = userMapper.toDTO(client.get());

        if (dto.hasError())
            return ErrorOptional.empty(dto.getErrorMessage() + "\nError - Repository - Failed to convert Client into ClientDTO!");

        return ErrorOptional.of((ClientDTO) dto.get());

    }

```
## Class PropertySaleRequestRepository

```java

public class PropertySaleRequestRepository {
    List<PropertySaleRequest> propertySaleRequests = new ArrayList<>();

    private final PropertySaleRequestMapper mapper = new PropertySaleRequestMapper();
    
    public OperationResult createPropertySaleRequest(PropertySaleRequestDTO dto) {
        try {
            ErrorOptional<PropertySaleRequest> propertySaleRequest = mapper.toDomain(dto);
            if (propertySaleRequest.hasError())
                return OperationResult.failed(propertySaleRequest.getErrorMessage() + "\nError - Repository - Failed to initiate PropertySaleRequest!");
            return addPropertySaleRequest(propertySaleRequest.get());
        } catch (IllegalArgumentException e) {
            return OperationResult.failed(e.getLocalizedMessage());
        }
    }
    
    private OperationResult addPropertySaleRequest(PropertySaleRequest propertySaleRequest) {
        if (!propertySaleRequest.isValid().success())
            return OperationResult.failed(propertySaleRequest.isValid().getErrorMessage() + "\nError - Repository - Failed local validation!");
        if (!isValid(propertySaleRequest))
            return OperationResult.failed(isValid(propertySaleRequest) + "\nError - Repository - Failed global validation!");

        propertySaleRequests.add(propertySaleRequest);
        return OperationResult.successfull();
    }

    private boolean isValid(PropertySaleRequest propertySaleRequest) {
        return !propertySaleRequests.contains(propertySaleRequest);
    }

    public ErrorOptional<List<PropertySaleRequestDTO>> listPropertySaleRequests(AgentDTO agentDTO) {
        List<PropertySaleRequestDTO> list = new ArrayList<>();

        StoreNetworkRepository storeNetworkRepository = Repositories.getStoreNetworkRepository();

        ErrorOptional<Agent> agent = storeNetworkRepository.getAgentFromEmail(agentDTO.email);
        if (agent.hasError()) return ErrorOptional.empty(agent.getErrorMessage());


        ErrorOptional<PropertySaleRequestDTO> propertySaleRequestDTO;
        for (PropertySaleRequest request : propertySaleRequests) {
            if (agent.get().equals(request.getAgent())) {
                propertySaleRequestDTO = mapper.toDTO(request);
                if (propertySaleRequestDTO.hasError())
                    return ErrorOptional.empty(propertySaleRequestDTO.getErrorMessage());
                list.add(propertySaleRequestDTO.get());
            }
        }

        return ErrorOptional.of(list);
    }

    public OperationResult removePropertySaleRequest(PropertySaleRequestDTO dto) {
        if (dto == null) {
            return OperationResult.failed("Error - Repository - PropertySaleRequestDTO cannot be null!");
        }

        for (PropertySaleRequest propertySaleRequest : propertySaleRequests) {
            if (propertySaleRequest.getID() == dto.id) {
                propertySaleRequests.remove(propertySaleRequest);
                return OperationResult.successfull();
            }
        }

        return OperationResult.failed("Error - Repository - PropertySaleRequestDTO doesn't exist in the repository!");
    }

```
## Class PropertySaleRepository

```java
public class PropertySaleRepository {
    
}
    List<PropertySale> propertySales = new ArrayList<>();

    private final PropertySaleMapper mapper = new PropertySaleMapper();

private OperationResult addPropertySale(PropertySale propertySale) {
        OperationResult localValidation = propertySale.isValid();
        if (!localValidation.success())
            return OperationResult.failed(localValidation.getErrorMessage() + "\nError - Repository - PropertySale failed local validation!");

        OperationResult globalValidation = isValid(propertySale);
        if (!globalValidation.success())
            return OperationResult.failed(globalValidation.getErrorMessage() + "\nError - Repository - PropertySale failed global validation!");

        propertySales.add(0, propertySale);
        return OperationResult.successfull();
    }
    
    private OperationResult isValid(PropertySale propertySale) {
        for (PropertySale p : propertySales) {
            if (p.equals(propertySale))
                return OperationResult.failed("Error - Repository - PropertySale already exists!");
        }

        return OperationResult.successfull();
    }

    public OperationResult convertRequestInPropertySale(PropertySaleRequestDTO request, PropertySale.CommissionType comissionType, double comission) {
        ErrorOptional<PropertySale> propertySale = mapper.toDomain(request, comissionType, comission);
        if (propertySale.hasError()) {
        return OperationResult.failed(propertySale.getErrorMessage() + "\nError - Repository - Failed to convert PropertySaleRquestDTO into PropertySale!");
        }

        return addPropertySale(propertySale.get());
        }
}
```

## Class StoreNetworkRepository

```java
public class StoreNetworkRepository {

    List<StoreNetwork> storeNetworks = new ArrayList<>();

    private final StoreNetworkMapper mapper = new StoreNetworkMapper();
    private final UserMapper userMapper = new UserMapper();


    public ErrorOptional<Agent> getAgentFromEmail(Email email) {
        ErrorOptional<Agent> agent = ErrorOptional.empty("");

        for (StoreNetwork storeNetwork : storeNetworks) {
            agent = storeNetwork.getAgentFromEmail(email);
            if (!agent.hasError())
                return agent;
        }

        return ErrorOptional.empty(agent.getErrorMessage() + "\nError - StoreNetworkRepository does not contain Agent[" + email + "]");
    }
}
    


```
# 6. Integration and Demo 




# 7. Observations






