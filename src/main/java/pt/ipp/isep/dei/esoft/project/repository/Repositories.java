package pt.ipp.isep.dei.esoft.project.repository;

import java.io.Serializable;

/**
 * This class contains all repositories
 */
public class Repositories implements Serializable {

    /**
     * Repositories
     */
    private static Repositories instance = new Repositories();
    /**
     * propertySaleRepository
     */
    PropertySaleRepository propertySaleRepository = new PropertySaleRepository();
    /**
     * purchaseRequestRepository
     */
    PurchaseRequestRepository purchaseRequestRepository = new PurchaseRequestRepository();

    /**
     * PropertySold repository
     */
    PropertySoldRepository propertySoldRepository = new PropertySoldRepository();

    /**
     * The Visit request repository.
     */
    VisitRequestRepository visitRequestRepository = new VisitRequestRepository();
    /**
     * propertySaleRequestRepository
     */
    PropertySaleRequestRepository propertySaleRequestRepository = new PropertySaleRequestRepository();
    /**
     * clientRepository
     */
    ClientRepository clientRepository = new ClientRepository();
    /**
     * branchRepository
     */
    StoreRepository storeRepository = new StoreRepository();
    /**
     * locationsRepository
     */
    LocationsRepository locationsRepository = new LocationsRepository();
    /**
     * systemAdministratorRepository
     */
    SystemAdministratorRepository systemAdministratorRepository = new SystemAdministratorRepository();
    /**
     * authenticationRepository
     */
    AuthenticationRepository authenticationRepository = new AuthenticationRepository();


    /**
     * Instantiates a new Repositories.
     */
    public Repositories() {
    }

    /**
     * Used to return repositories instance to get to other repositories
     *
     * @return Repositories instance
     */
    public static Repositories getInstance() {
        return instance;
    }


    /**
     * Used to get PropertySaleRepository instance
     *
     * @return PropertySaleRepository instance
     */
    public static PropertySaleRepository getPropertySaleRepository() {
        return instance.propertySaleRepository;
    }

    /**
     * Used to get PropertySaleRequestRepository instance
     *
     * @return PropertySaleRequestRepository instance
     */
    public static PropertySaleRequestRepository getPropertySaleRequestRepository() {
        return instance.propertySaleRequestRepository;
    }

    /**
     * Used to get ClientRepository instance
     *
     * @return Clientnstance client repository
     */
    public static ClientRepository getClientRepository() {
        return instance.clientRepository;
    }

    /**
     * Used to get BranchRepository instance
     *
     * @return BranchRepository instance
     */
    public static StoreRepository getStoreRepository() {
        return instance.storeRepository;
    }

    /**
     * Used to get LocationsRepository instance
     *
     * @return LocationsRepository instance
     */
    public static LocationsRepository getLocationsRepository() {
        return instance.locationsRepository;
    }

    /**
     * Gets system administrator repository.
     *
     * @return the system administrator repository
     */
    public static SystemAdministratorRepository getSystemAdministratorRepository() {
        return instance.systemAdministratorRepository;
    }

    /**
     * Used to get AuthenticationRepository instance
     *
     * @return AuthenticationRepository instance
     */
    public static AuthenticationRepository getAuthenticationRepository() {
        return instance.authenticationRepository;
    }

    /**
     * Used to get PurchaseRequestRepository instance
     *
     * @return PurchaseRequestRepository purchase request repository
     */
    public static PurchaseRequestRepository getPurchaseRequestRepository() {
        return instance.purchaseRequestRepository;
    }

    /**
     * Gets visit request repository.
     *
     * @return the visit request repository
     */
    public static VisitRequestRepository getVisitRequestRepository() {
        return instance.visitRequestRepository;
    }

    /**
     * Sets instance.
     *
     * @param repositories the repositories
     */
    public static void setInstance(Repositories repositories) {
        instance = repositories;
    }

    /**
     * Deserialize.
     */
    public static void deserialize() {
        instance.propertySaleRepository.deserialize();
        instance.purchaseRequestRepository.deserialize();
        instance.visitRequestRepository.deserialize();
        instance.propertySaleRequestRepository.deserialize();
        instance.clientRepository.deserialize();
        instance.storeRepository.deserialize();
        instance.locationsRepository.deserialize();
        instance.systemAdministratorRepository.deserialize();
        instance.authenticationRepository.deserialize();
        instance.propertySoldRepository.deserialize();
    }

    /**
     * Gets property sold repository.
     *
     * @return the property sold repository
     */
    public static PropertySoldRepository getPropertySoldRepository() {
        return instance.propertySoldRepository;
    }
}

