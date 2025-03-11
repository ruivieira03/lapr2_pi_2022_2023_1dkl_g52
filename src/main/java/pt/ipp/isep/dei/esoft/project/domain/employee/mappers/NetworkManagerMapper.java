package pt.ipp.isep.dei.esoft.project.domain.employee.mappers;

import pt.ipp.isep.dei.esoft.project.domain.employee.NetworkManager;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.NetworkManagerDTO;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

/**
 * The type Network manager mapper.
 */
public class NetworkManagerMapper {

    /**
     * Instantiates a new Network manager mapper.
     */
    public NetworkManagerMapper() {

    }

    /**
     * To dto error optional.
     * Used to map domain representation to dto representation.
     *
     * @param networkManager the network manager
     * @return the error optional
     */
    public ErrorOptional<NetworkManagerDTO> toDTO(NetworkManager networkManager) {
        if (networkManager == null) return ErrorOptional.empty("Error - Mapper - NetworkManager cannot be null!");

        NetworkManagerDTO dto = new NetworkManagerDTO();

        return ErrorOptional.of(dto);
    }

    /**
     * To domain error optional.
     * Used to map dto representation to domain representation.
     *
     * @param dto the dto
     * @return the error optional
     */
    public ErrorOptional<NetworkManager> toDomain(NetworkManagerDTO dto) {
        if (dto == null) return ErrorOptional.empty("Error - Mapper - NetworkManagerDTO cannot be null!");

        NetworkManager networkManager = new NetworkManager();

        return ErrorOptional.of(networkManager);
    }
}
