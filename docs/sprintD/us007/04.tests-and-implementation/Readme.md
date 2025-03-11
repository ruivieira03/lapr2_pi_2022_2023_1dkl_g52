# US 007 - Registration

# 4. Tests 



	@Test
    void addClient() {
        Client invalidGlobalClient = clientRepository.clients.get(0);
        assert (!clientRepository.addClient(invalidGlobalClient));


        Client validClient = new Client("name", 123123, 123123, 123123, "adress", "valid@email.com");
        assert (clientRepository.addClient(validClient));
    }

	@Test
    void addUserRole() {
        authenticationRepository.addUserRole(AuthenticationController.Roles.AGENT.name(), AuthenticationController.Roles.ADMINISTRATOR.name());
        assert (authenticationRepository.addUserWithRole("Clive", "123@ex.com", "1234", AuthenticationController.Roles.AGENT));
    }



*It is also recommended to organize this content by subsections.* 

# 5. Construction (Implementation)


## Class RegistrationController

```java
public class RegistrationController {

    private Repositories repositories;

    public RegistrationController() {
        repositories = Repositories.getInstance();
    }

    public boolean createClient(String name, int passportNumber, int taxNumber, String address, String emailAddress, int telephoneNumber, String password){
        AuthenticationRepository authenticationRepository = repositories.getAuthenticationRepository();
        authenticationRepository.addUserWithRole(name,emailAddress,password, AuthenticationController.Roles.CLIENT);

        ClientRepository clientRepository = repositories.getClientRepository();
        return(clientRepository.createClient(name, passportNumber,telephoneNumber,taxNumber,address, emailAddress));
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
class ClientRepositoryTest {

    public class ClientRepository {

        List<Client> clients = new ArrayList<>();
        
        public boolean addClient(Client client) {
            if (!client.isValid()) return false;
            if (!isValid(client)) return false;

            clients.add(client);
            return true;
        }

       
        private boolean isValid(Client client) {
            return !clients.contains(client);
        }

        public Optional<Client> getClientFromEmail(String email) {
            Optional<Client> clientReturn = Optional.empty();

            for (Client client: clients) {
                if (client.hasEmail(email)) clientReturn = Optional.of(client);
            }

            return clientReturn;
        }

        public Optional<Client> getClientFromEmail(Email email) {
            Optional<Client> clientReturn = Optional.empty();

            for (Client client: clients) {
                if (client.hasEmail(email)) clientReturn = Optional.of(client);
            }

            return clientReturn;
        }


        public boolean createClient(String name, int passportNumber, int phoneNumber, int taxNumber, String address, String email) {
            try {
                Client client = new Client(name, passportNumber, phoneNumber, taxNumber, address, email);
                return addClient(client);

            } catch (IllegalArgumentException e) {
                return false;
            }
        }
    }

```

## Class Client

```java
public class Client {
   
    private String name;
   
    private int passportNumber;
    
    private int phoneNumber;
    
    private int taxNumber;
    
    private String address;
    
    private Email email;

    
    public Client(String name, int passportNumber, int phoneNumber, int taxNumber, String address, String email) {
        this.name=name;
        this.passportNumber=passportNumber;
        this.phoneNumber=phoneNumber;
        this.taxNumber=taxNumber;
        this.address=address;
        this.email = new Email(email);
    }

   
    public String getName() {
        return name;
    }

   
    public Email getEmail() {
        return email;
    }

    
    public int getPassportNumber() {
        return passportNumber;
    }

   
    public int getPhoneNumber() {
        return phoneNumber;
    }

    
    public int getTaxNumber() {
        return taxNumber;
    }

   
    public String getAddress() {
        return address;
    }

    
    public boolean isValid(Client client){
        // FIXME: not implemented yet (placeholder)
        return true;
    }

   
    public boolean equals(Object obj){
        if (obj == null)  return false;
        if (obj.getClass() != getClass()) return false;
        if (obj == this) return true;

        Client other = (Client) obj;
        return ( getName().equals(other.getName()) && getAddress().equals(other.getAddress()) && getEmail().equals(other.getEmail()) && getPassportNumber()==other.getPassportNumber() && getPhoneNumber()==other.getPhoneNumber() && getTaxNumber()==other.getTaxNumber());
    }

    
    public boolean hasEmail(String email) {
        return email.toString().equals(email);
    }

    
    public boolean hasEmail(Email email) {
        return this.email.equals(email);
    }

   
    public boolean isValid() {
        return true;
    }

   
    public void setAddress(String address) {
        this.address = address;
    }

   
    public void setEmail(Email email) {
        this.email = email;
    }

    
    public void setName(String name) {
        this.name = name;
    }

    
    public void setPassportNumber(int passportNumber) {
        this.passportNumber = passportNumber;
    }

    
    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

   
    public void setTaxNumber(int taxNumber) {
        this.taxNumber = taxNumber;
    }
}

```





# 6. Integration and Demo 




# 7. Observations






