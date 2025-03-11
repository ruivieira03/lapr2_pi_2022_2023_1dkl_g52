package pt.ipp.isep.dei.esoft.project.domain.store;

import pt.ipp.isep.dei.esoft.project.domain.employee.Agent;
import pt.ipp.isep.dei.esoft.project.domain.employee.StoreManager;
import pt.ipp.isep.dei.esoft.project.domain.locations.City;
import pt.ipp.isep.dei.esoft.project.domain.locations.District;
import pt.ipp.isep.dei.esoft.project.domain.locations.State;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The type Store.
 */
public class Store implements Serializable {
    private List<Agent> agents;
    private StoreManager storeManager;

    private int id;
    private String name;
    private String email;
    private String telephoneNumber;
    private String address;
    private int zipCode;
    private State state;
    private District district;
    private City city;
    private int propertyCount;


    /**
     * method to initiate a new store
     */
    public Store() {
        this.agents = new ArrayList<>();
        this.storeManager = null;
        this.propertyCount = 0;
    }


    /**
     * method to get an agent of the store by his email
     *
     * @param email email
     * @return agent agent from email
     */
    public ErrorOptional<Agent> getAgentFromEmail(String email) {
        Optional<Agent> agentReturn = Optional.empty();

        for (Agent agent : agents) {
            if (agent.hasEmail(email)) {
                return ErrorOptional.of(agent);
            }
        }

        return ErrorOptional.empty("Error - Store - Store[" + this.id + "] does not contain Agent[" + email + "]");

    }


    /**
     * method to get the agents from a store
     *
     * @return agents agents
     */
    public ErrorOptional<List<Agent>> getAgents() {

        return ErrorOptional.of(agents);
    }

    /**
     * method to change the agents of a store
     *
     * @param agents agents
     */
    public void setAgents(List<Agent> agents) {
        this.agents = agents;
    }

    /**
     * method to get the manager of a store
     *
     * @return agents store manager
     */
    public StoreManager getStoreManager() {
        return storeManager;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets state.
     *
     * @return the state
     */
    public State getState() {
        return state;
    }

    /**
     * Gets district.
     *
     * @return the district
     */
    public District getDistrict() {
        return district;
    }

    /**
     * Gets city.
     *
     * @return the city
     */
    public City getCity() {
        return city;
    }

    /**
     * Gets property count.
     *
     * @return the property count
     */
    public int getPropertyCount() {
        return propertyCount;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets telephone number.
     *
     * @param telephoneNumber the telephone number
     */
    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Sets zip code.
     *
     * @param zipCode the zip code
     */
    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * Sets state.
     *
     * @param state the state
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * Sets district.
     *
     * @param district the district
     */
    public void setDistrict(District district) {
        this.district = district;
    }

    /**
     * Sets city.
     *
     * @param city the city
     */
    public void setCity(City city) {
        this.city = city;
    }

    /**
     * Sets property count.
     *
     * @param propertyCount the property count
     */
    public void setPropertyCount(int propertyCount) {
        this.propertyCount = propertyCount;
    }

    /**
     * method to change the manager of a store
     *
     * @param storeManager storeManager
     */
    public void setStoreManager(StoreManager storeManager) {
        this.storeManager = storeManager;
    }

    /**
     * Add property count.
     */
    public void addPropertyCount() {
        this.propertyCount++;
    }


    /**
     * method to add an agent
     *
     * @param agent
     * @return
     */
    private OperationResult addAgent(Agent agent) {
        if (!agent.isValid().success()) return agent.isValid();
        if (!isAgentValid(agent).success()) return isAgentValid(agent);

        agents.add(agent);
        return OperationResult.successfull();
    }

    /**
     * method to verify if agent doesn't already exist
     *
     * @param agent
     * @return
     */

    private OperationResult isAgentValid(Agent agent) {
        for (Agent a : agents) {
            if (a.equals(agent))
                return OperationResult.failed("Error - Store - Agent already exists!");
        }

        return OperationResult.successfull();
    }


    /**
     * method to add the recently created Store Manager
     *
     * @param storeManager
     * @return
     */

    private OperationResult addStoreManager(StoreManager storeManager) {
        if (!storeManager.isValid().success()) return storeManager.isValid();
        if (!isStoreManagerValid(storeManager).success()) return isStoreManagerValid(storeManager);

        this.storeManager = storeManager;
        return OperationResult.successfull();
    }

    /**
     * method to verify if recently added store manager doesn't already exist
     *
     * @param storeManager
     * @return
     */

    private OperationResult isStoreManagerValid(StoreManager storeManager) {
        if (!this.storeManager.equals(storeManager))
            return OperationResult.successfull();

        return OperationResult.failed("Error - Store - StoreMAnager already registered!");
    }

    /**
     * Register agent operation result.
     *
     * @param agent the agent
     * @return the operation result
     */
    public OperationResult registerAgent(Agent agent) {
        if (!agent.isValid().success()) return OperationResult.failed("Error - Store - Failed local validation!");
        if (!isAgentValid(agent).success()) return OperationResult.failed("Error - Store - Failed global validation!");

        agents.add(agent);
        return OperationResult.successfull();
    }

    /**
     * Register store manager operation result.
     *
     * @param storeManager the store manager
     * @return the operation result
     */
    public OperationResult registerStoreManager(StoreManager storeManager) {
        if (!storeManager.isValid().success())
            return OperationResult.failed("Error - Store - Failed local validation!");

        this.storeManager = storeManager;
        return OperationResult.successfull();
    }

    /**
     * Is valid operation result.
     *
     * @return the operation result
     */
    public OperationResult isValid() {
        // TODO: implement method
        return OperationResult.successfull();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (!(obj.getClass() == this.getClass())) return false;

        Store other = (Store) obj;

        return other.email.equals(this.email);
    }
}
