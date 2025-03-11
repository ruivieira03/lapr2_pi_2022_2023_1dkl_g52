package pt.ipp.isep.dei.esoft.project.domain.store.mappers.dto;

import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.AgentDTO;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.StoreManagerDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.CityDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.DistrictDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.StateDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Store dto.
 */
public class StoreDTO {
    /**
     * The Agents.
     */
    public List<AgentDTO> agents;
    /**
     * The Id.
     */
    public int id;
    /**
     * The Store manager.
     */
    public StoreManagerDTO storeManager;
    /**
     * The Name.
     */
    public String name;
    /**
     * The Email.
     */
    public String email;
    /**
     * The Telephone number.
     */
    public String telephoneNumber;
    /**
     * The Adress.
     */
    public String adress;
    /**
     * The Zip code.
     */
    public int zipCode;
    /**
     * The State.
     */
    public StateDTO state;
    /**
     * The District.
     */
    public DistrictDTO district;
    /**
     * The City.
     */
    public CityDTO city;
    /**
     * The Property count.
     */
    public int propertyCount;

    /**
     * Constructs a StoreDTO object with the specified store details.
     *
     * @param name            the name of the store
     * @param email           the email address of the store
     * @param telephoneNumber the telephone number of the store
     * @param adress          the address of the store
     * @param zipCode         the zip code of the store
     */
    public StoreDTO(String name, String email, String telephoneNumber, String adress, int zipCode) {
        this.name = name;
        this.email = email;
        this.telephoneNumber = telephoneNumber;
        this.adress = adress;
        this.zipCode = zipCode;
        this.agents = new ArrayList<>();
    }


    /**
     * Instantiates a new Store dto.
     *
     * @param name            the name
     * @param email           the email
     * @param telephoneNumber the telephone number
     * @param adress          the adress
     * @param zipCode         the zip code
     * @param state           the state
     * @param district        the district
     * @param city            the city
     */

    /**
     *
     * @param name the name of the store
     * @param email the email address of the store
     * @param telephoneNumber the telephone number of the store
     * @param adress the address of the store
     * @param zipCode the zip code of the store
     * @param state the state of the store
     * @param district the district of the store
     * @param city the cityof the store
     */
    public StoreDTO(String name, String email, String telephoneNumber, String adress, int zipCode, StateDTO state, DistrictDTO district, CityDTO city) {
        this.name = name;
        this.email = email;
        this.telephoneNumber = telephoneNumber;
        this.adress = adress;
        this.zipCode = zipCode;
        this.state = state;
        this.district = district;
        this.city = city;
        this.agents = new ArrayList<>();
    }

    /**
     * Instantiates a new Store dto.
     */

    public StoreDTO() {
        agents = new ArrayList<>();
    }

    /**
     * Returns a string representation of the StoreDTO object.
     *
     * @return a string representation of the StoreDTO object
     */
    public String toString(){
        return  String.format("|%38d|%27s|%41d|", id, name, propertyCount);
    }
}


