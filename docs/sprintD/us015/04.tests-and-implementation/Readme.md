# US 015 - List booking request

# 4. Tests

Due to the complexity of the creation of Visit Requests as it has to have Clients, Properties, Agents among many other things, it was not possible to completely test the functionality of the algorithms. However, the tests that were made were successful when running the program normally.

### Set up

```java
@BeforeEach
static void setUp() {
    PropertySaleDTO property = new PropertySaleDTO(new PropertyDTO(), new HouseDTO(), new AgentDTO(), 100000, 1000, LocalDate.of(2023, 1, 1), new ArrayList<>(), new ArrayList<>());

    VisitRequestDTO visitRequestDTO1 = new VisitRequestDTO(new ClientDTO(), new PropertySaleDTO(), LocalDateTime.of(2023, 6, 1, 10, 0), LocalDateTime.of(2023, 6, 1, 11, 0));
    VisitRequestDTO visitRequestDTO2 = new VisitRequestDTO(new ClientDTO(), new PropertySaleDTO(), LocalDateTime.of(2023, 5, 12, 10, 12), LocalDateTime.of(2023, 5, 12, 13, 0));
    VisitRequestDTO visitRequestDTO3 = new VisitRequestDTO(new ClientDTO(), new PropertySaleDTO(), LocalDateTime.of(2023, 3, 23, 16, 40), LocalDateTime.of(2023, 3, 23, 18, 5));
    VisitRequestDTO visitRequestDTO4 = new VisitRequestDTO(new ClientDTO(), new PropertySaleDTO(), LocalDateTime.of(2023, 8, 10, 20, 0), LocalDateTime.of(2023, 8, 10, 22, 0));
    VisitRequestDTO visitRequestDTO5 = new VisitRequestDTO(new ClientDTO(), new PropertySaleDTO(), LocalDateTime.of(2023, 1, 1, 1, 0), LocalDateTime.of(2023, 1, 1, 11, 0));
    
    visitRequestDTOList = List.of(visitRequestDTO1, visitRequestDTO2, visitRequestDTO3, visitRequestDTO4, visitRequestDTO5);

    }
```

### Bubble Sort

```java
@Test
void bubbleSort() {
    Algorithms.bubbleSort(visitRequestDTOList);

    assertEquals(visitRequestDTOList.get(0).visitStart, LocalDateTime.of(2023, 1, 1, 1, 0));
    assertEquals(visitRequestDTOList.get(1).visitStart, LocalDateTime.of(2023, 3, 23, 16, 40));
    assertEquals(visitRequestDTOList.get(2).visitStart, LocalDateTime.of(2023, 5, 12, 10, 12));
    assertEquals(visitRequestDTOList.get(3).visitStart, LocalDateTime.of(2023, 6, 1, 10, 0));
    assertEquals(visitRequestDTOList.get(4).visitStart, LocalDateTime.of(2023, 8, 10, 20, 0));

    }
```

### Merge Sort

```java
 @Test
void mergeSort() {
    Algorithms.mergeSort(visitRequestDTOList, 0, visitRequestDTOList.size() - 1);

    assertEquals(visitRequestDTOList.get(0).visitStart, LocalDateTime.of(2023, 1, 1, 1, 0));
    assertEquals(visitRequestDTOList.get(1).visitStart, LocalDateTime.of(2023, 3, 23, 16, 40));
    assertEquals(visitRequestDTOList.get(2).visitStart, LocalDateTime.of(2023, 5, 12, 10, 12));
    assertEquals(visitRequestDTOList.get(3).visitStart, LocalDateTime.of(2023, 6, 1, 10, 0));
    assertEquals(visitRequestDTOList.get(4).visitStart, LocalDateTime.of(2023, 8, 10, 20, 0));
    }
```
    




# 5. Construction (Implementation)


## Class ReviewVisitRequestController

```java
public class ReviewVisitRequestsController {
    private final Repositories repositories;
    private long startTime;
    private long endTime;

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

    public String[] getVisitRequestByAgentAsString(){
        VisitRequestRepository visitRequestRepository = Repositories.getVisitRequestRepository();
        AgentDTO agentDTO = getAgentFromSession().get();
        return visitRequestRepository.getVisitRequestByAgentAsString(agentDTO);
    }

    public List<VisitRequestDTO> getVisitRequests(LocalDate beginDate, LocalDate endDate) {
        ErrorOptional<List<VisitRequestDTO>> visitRequestDTOList = getPropertySaleRequestListFromAgent();
        VisitRequestRepository repo = Repositories.getVisitRequestRepository();
        List<VisitRequestDTO> filteredVisitRequests = repo.filterByDate(visitRequestDTOList, beginDate, endDate);
        if (filteredVisitRequests != null && !filteredVisitRequests.isEmpty()) {
            sortList(filteredVisitRequests);
        }
        return filteredVisitRequests;
    }

    private void sortList(List<VisitRequestDTO> visitRequestDTOList) {
        String sortAlgorithm = Utils.ReadProperties("Sort.Algorithm");
        switch (sortAlgorithm) {
            case "Bubble":
                startTime= System.nanoTime();
                Algorithms.bubbleSort(visitRequestDTOList);
                endTime= System.nanoTime();
                float timeBubble = (float) (endTime - startTime) / 1000000;
                System.out.printf("\nBubble Sort: %.5f milliseconds\n", timeBubble);
                break;

            case "Merge":
                startTime= System.nanoTime();
                endTime= System.nanoTime();
                Algorithms.mergeSort(visitRequestDTOList, 0, visitRequestDTOList.size() - 1);
                endTime= System.nanoTime();
                float timeMerge = (float) (endTime - startTime) / 1000000;
                System.out.printf("\nMerge Sort: %.5f milliseconds\n", timeMerge);
                break;

            default:
                throw new IllegalArgumentException("No sort option");
        }
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

    public List<VisitRequestDTO> filterByDate(ErrorOptional<List<VisitRequestDTO>> visitRequestDTOList, LocalDate beginDate, LocalDate endDate) {
        List<VisitRequestDTO> filteredList = new ArrayList<>();

        for (VisitRequestDTO visitRequestDTO : visitRequestDTOList.get()) {
            if (visitRequestDTO.visitStart.isAfter(beginDate.atStartOfDay()) && visitRequestDTO.visitEnd.isBefore(endDate.atStartOfDay())) {
                filteredList.add(visitRequestDTO);
            }
        }

        return filteredList;
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

## Algorithms
````java
public class Algorithms {
    public static void bubbleSort(List<VisitRequestDTO> list) {
        boolean flag;

        for (int i = 0; i < list.size() - 1; i++) {
            flag = false;

            for (int j = 0; j < list.size() - i - 1; j++) {
                if (list.get(j).visitStart.isAfter(list.get(j + 1).visitStart)) {
                    Collections.swap(list, j, j + 1);
                    flag = true;
                }
            }
            if (!flag)
                break;

        }
    }

    public static void mergeSort(List<VisitRequestDTO> visitRequestDTOList, int left, int right)
    {
        if (left < right) {
            // Find the middle point
            int middle = left + (right - left) / 2;

            // Sort first and second halves
            mergeSort(visitRequestDTOList, left, middle);
            mergeSort(visitRequestDTOList, middle + 1, right);

            sort(visitRequestDTOList, left, middle, right);
        }
    }

    private static void sort(List <VisitRequestDTO> visitRequestDTOList, int left, int middle, int right)
    {
        // Find sizes of two sublists to be merged
        int n1 = middle - left + 1;
        int n2 = right - middle;

        /* Create temp lists */
        List <VisitRequestDTO> Left=new ArrayList<>();
        List <VisitRequestDTO> Right=new ArrayList<>();

        /*Copy data to temp lists*/
        for (int i = 0; i < n1; ++i)
            Left.add(visitRequestDTOList.get(left+i));

        for (int j = 0; j < n2; ++j)
            Right.add(visitRequestDTOList.get(middle+1+j));

        /* Merge the temp lists */

        // Initial indexes of first and second sublists
        int i = 0, j = 0;

        // Initial index of merged sublists list
        int k = left;
        while (i < n1 && j < n2) {
            if ((Left.get(i).visitStart.isBefore(Right.get(j).visitStart)) || (Left.get(i).visitStart.isEqual(Right.get(j).visitStart))){
                visitRequestDTOList.set(k,Left.get(i));
                i++;
            }
            else {
                visitRequestDTOList.set(k,Right.get(j));
                j++;
            }
            k++;
        }

        /* Copy remaining elements of Left if any */
        while (i < n1) {
            visitRequestDTOList.set(k,Left.get(i));
            i++;
            k++;
        }

        /* Copy remaining elements of Right if any */
        while (j < n2) {
            visitRequestDTOList.set(k,Right.get(j));
            j++;
            k++;
        }
    }

}
````

## ListVisitRequestUI
````java
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
````

# 6. Integration and Demo
## Menu
```
Agent Menu:
1. List Properties
2. Register Property Sale
3. Manage Property Sale Requests
4. Manage Purchase Requests
5. List Visit Requests
6. Manage Visit Requests

0 - Cancel

Type your option: 
5
```

## List Visit Requests with Merge Sort
```
Please insert the begin date (dd/mm/yyyy):
01/03/2023

Please insert the end date (dd/mm/yyyy):
01/08/2023

Begin date: 01/03/2023
End date: 01/08/2023


Are these dates correct? (Y/N)

y

Merge Sort: 1,57320 milliseconds
Visit Requests:
|___|    Client Name     |  Type   | Business |Requested price |       State        |      District      |        City        |                 Street                 | Zip Code |   Area   |Distance from center| Bedrooms | Bathrooms | Parking |      Available Equipment     | Basement | Inhabitable Loft | Sun Exposure | Visit Date | Start Hour |  End Hour  |
|---|:-------------------|:--------|:---------|:---------------|:-------------------|:-------------------|:-------------------|:---------------------------------------|:---------|:---------|:-------------------|:---------|:----------|:--------|:-----------------------------|:---------|:-----------------|:-------------|------------|------------|------------|
| 1.|                Anne|    House|      SELL|          200000|               Texas|             Brazora|            PearLand|                  Independace Street n.4|     12345|       300|                4,00|         1|          1|        2|                          None|       Yes|               Yes|         SOUTH|  2023-06-12|       09:00|       09:30|
| 2.|                Anne|Apartment|      RENT|          100000|             Florida|          Miami Dade|               Miami|                         5th Avenue n.19|     46265|      1000|               13,00|         1|          1|       10|                           123|          |                  |              |  2023-06-12|       10:00|       12:00|
| 3.|                Anne|     Land|      SELL|           50000|             Alabama|          Montgomery|          Montgomery|                        Dirt Road plot 6|     43286|      1200|               16,00|          |           |         |                              |          |                  |              |  2023-06-12|       13:00|       14:00|
| 4.|                Anne|     Land|      RENT|           70000|          California|       San Francisco|       San Francisco|                   High Montains plot 17|     28930|      2000|               21,00|          |           |         |                              |          |                  |              |  2023-06-12|       17:30|       18:00|
````

## List Visit Requests with Bubble Sort
```
Please insert the begin date (dd/mm/yyyy):
01/03/2023

Please insert the end date (dd/mm/yyyy):
01/08/2023

Begin date: 01/03/2023
End date: 01/08/2023


Are these dates correct? (Y/N)

y

Bubble Sort: 1,36940 milliseconds
Visit Requests:
|___|    Client Name     |  Type   | Business |Requested price |       State        |      District      |        City        |                 Street                 | Zip Code |   Area   |Distance from center| Bedrooms | Bathrooms | Parking |      Available Equipment     | Basement | Inhabitable Loft | Sun Exposure | Visit Date | Start Hour |  End Hour  |
|---|:-------------------|:--------|:---------|:---------------|:-------------------|:-------------------|:-------------------|:---------------------------------------|:---------|:---------|:-------------------|:---------|:----------|:--------|:-----------------------------|:---------|:-----------------|:-------------|------------|------------|------------|
| 1.|                Anne|    House|      SELL|          200000|               Texas|             Brazora|            PearLand|                  Independace Street n.4|     12345|       300|                4,00|         1|          1|        2|                          None|       Yes|               Yes|         SOUTH|  2023-06-12|       09:00|       09:30|
| 2.|                Anne|Apartment|      RENT|          100000|             Florida|          Miami Dade|               Miami|                         5th Avenue n.19|     46265|      1000|               13,00|         1|          1|       10|                           123|          |                  |              |  2023-06-12|       10:00|       12:00|
| 3.|                Anne|     Land|      SELL|           50000|             Alabama|          Montgomery|          Montgomery|                        Dirt Road plot 6|     43286|      1200|               16,00|          |           |         |                              |          |                  |              |  2023-06-12|       13:00|       14:00|
| 4.|                Anne|     Land|      RENT|           70000|          California|       San Francisco|       San Francisco|                   High Montains plot 17|     28930|      2000|               21,00|          |           |         |                              |          |                  |              |  2023-06-12|       17:30|       18:00|
````

## List Visit Requests with no data
```
Please insert the begin date (dd/mm/yyyy):
01/11/2023

Please insert the end date (dd/mm/yyyy):
01/12/2023

Begin date: 01/11/2023
End date: 01/12/2023


Are these dates correct? (Y/N)

y

No visit requests found for the given dates.
````

