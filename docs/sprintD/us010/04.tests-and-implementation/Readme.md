# US 004 - To Submit a request to list a property

# 4. Tests

**Test 1:** Check that it is not possible to create an instance of the Task class with null values.

	@Test
    



*It is also recommended to organize this content by subsections.*

# 5. Construction (Implementation)


## Class PurchaseRequestController

```java
public class PurchaseRequestController {
    private Repositories repositories;

    public PurchaseRequestController() {
        this.repositories = Repositories.getInstance();
    }

    public OperationResult createPurchaseRequest(PurchaseRequestDTO purchaseRequestDTO) {
        PurchaseRequestRepository purchaseRequestRepository = repositories.getPurchaseRequestRepository();

        ErrorOptional<ClientDTO> client = getClientFromUserSession();
        if (client.hasError())
            return OperationResult.failed(client.getErrorMessage() + "\nError - Controller - Failed to get ClientDTO from user session");
        purchaseRequestDTO.client = client.get();

        return purchaseRequestRepository.createPurchaseRequest(purchaseRequestDTO);

    }

    public ErrorOptional<ClientDTO> getClientFromUserSession() {
        AuthenticationRepository authenticationRepository = new AuthenticationRepository();

        UserSession user = authenticationRepository.getCurrentUserSession();
        Email email = user.getUserId();

        ClientRepository clientRepository = new ClientRepository();
        return clientRepository.getClientDTOFromEmail(email.toString());
    }

    public List<PropertySaleDTO> getPropertySaleList(short sortCriteria) {
        PropertySaleRepository propertySaleRepository = repositories.getPropertySaleRepository();
        return propertySaleRepository.listProperties(sortCriteria);
    }
}

```



## Class PurchaseRequestRepository

```java
public class PurchaseRequestRepository {
    ArrayList<PurchaseRequest> purchaseRequestList = new ArrayList<>();

    private final PurchaseRequestMapper mapper = new PurchaseRequestMapper();

    public OperationResult createPurchaseRequest(PurchaseRequestDTO purchaseRequestDTO) {
        ErrorOptional<PurchaseRequest> purchaseRequest = mapper.toDomain(purchaseRequestDTO);
        if (purchaseRequest.hasError())
            return OperationResult.failed(purchaseRequest.getErrorMessage() + "\nError - Repository - Failed to convert PurchaseRequestDTO into PurchaseRequest!");

        return addPurchaseRequest(purchaseRequest.get());
    }

    private OperationResult addPurchaseRequest(PurchaseRequest purchaseRequest) {
        if (!purchaseRequest.isValid()) return OperationResult.failed("Error - Repository - Failed local validation!");
        if (!isValid(purchaseRequest)) return OperationResult.failed("Error - Repository - Failed global validation!");

        purchaseRequestList.add(purchaseRequest);
        return OperationResult.successfull();
    }

    private boolean isValid(PurchaseRequest purchaseRequest) {
        for (PurchaseRequest p : purchaseRequestList) {
            if (p.equals(purchaseRequest))
                return false;
        }

        return true;
    }
}
```


## Class PurchaseRequest
````java
public class PurchaseRequest {
    private PropertySale propertySale;
    private Client client;
    private float orderAmount;
    public PurchaseRequest(PropertySale propertySale, Client client, float orderAmount){
        if ( client == null ) throw new IllegalArgumentException("Client not found");
        if (propertySale == null) throw  new IllegalArgumentException("Property not found");
        this.orderAmount=orderAmount;
        this.client=client;
        this.propertySale=propertySale;
    }

    public PurchaseRequest() {

    }

    public boolean isValid(){
        if (orderAmount < propertySale.getRequestedPrice()){
            System.out.printf("Order amount can't be lower than the price requested");
            return false;
        }else return true;
    }
    public Client getClient(){return client;}
    public float getOrderAmount(){return orderAmount;}
    public PropertySale getPropertySale(){return propertySale;}

    public void setClient(Client client) {
        this.client = client;
    }

    public void setOrderAmount(float orderAmount) {
        this.orderAmount = orderAmount;
    }

    public void setPropertySale(PropertySale propertySale) {
        this.propertySale = propertySale;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this)  return true;
        if (!(obj instanceof PurchaseRequest)) return false;

        PurchaseRequest request = (PurchaseRequest) obj;

        return propertySale.equals(request.propertySale) &&
                client.equals(request.client) &&
                orderAmount == request.orderAmount;
    }
}


````

# 6. Integration and Demo


# 7. Observations


