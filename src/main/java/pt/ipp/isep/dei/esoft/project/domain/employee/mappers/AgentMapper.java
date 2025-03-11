package pt.ipp.isep.dei.esoft.project.domain.employee.mappers;

import pt.ipp.isep.dei.esoft.project.domain.employee.Agent;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.AgentDTO;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

/**
 * The type Agent mapper.
 */
public class AgentMapper {
    /**
     * Instantiates a new Agent mapper.
     */
    public AgentMapper() {

    }

    /**
     * To dto error optional.
     * Used to map domain representation to dto representation.
     *
     * @param agent the agent
     * @return the error optional
     */
    public ErrorOptional<AgentDTO> toDTO(Agent agent) {
        if (agent == null) return ErrorOptional.empty("Error - Mapper - Agent cannot be null");

        AgentDTO dto = new AgentDTO();

        return ErrorOptional.of(dto);
    }

    /**
     * To domain error optional.
     * Used to map dto representation to domain representation.
     *
     * @param dto the dto
     * @return the error optional
     */
    public ErrorOptional<Agent> toDomain(AgentDTO dto) {
        if (dto == null) return ErrorOptional.empty("Error - Mapper - AgentDTO cannot be null!");

        Agent agent = new Agent();

        return ErrorOptional.of(agent);
    }
}
