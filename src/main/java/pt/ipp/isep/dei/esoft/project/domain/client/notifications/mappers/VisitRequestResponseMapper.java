package pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers;

import pt.ipp.isep.dei.esoft.project.domain.client.notifications.VisitRequestReponse;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers.dto.MessageDTO;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers.dto.VisitRequestResponseDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.VisitRequest;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.VisitRequestMapper;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.VisitRequestDTO;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

/**
 * The type Visit request response mapper.
 */
public class VisitRequestResponseMapper {

    private final transient VisitRequestMapper visitRequestMapper = new VisitRequestMapper();

    /**
     * To dto error optional.
     * Used to map domain representation to dto representation.
     *
     * @param visitRequestReponse the visit request reponse
     * @return the error optional
     */
    public ErrorOptional<MessageDTO> toDTO(VisitRequestReponse visitRequestReponse) {
        if (visitRequestReponse == null)
            return ErrorOptional.empty("Error - Mapper - VisitRequestResponse cannot be null!");

        VisitRequestMapper visitRequestMapper = new VisitRequestMapper();

        VisitRequestResponseDTO dto = new VisitRequestResponseDTO();

        ErrorOptional<VisitRequestDTO> errorOptional = visitRequestMapper.toDTO(visitRequestReponse.getVisitRequest());
        if (errorOptional.hasError()) return ErrorOptional.empty(errorOptional.getErrorMessage());
        dto.visitRequestDTO = errorOptional.get();

        return ErrorOptional.of(dto);
    }

    /**
     * To domain error optional.
     * Used to map dto representation to domain representation.
     *
     * @param dto the dto
     * @return the error optional
     */
    public ErrorOptional<VisitRequestReponse> toDomain(VisitRequestResponseDTO dto) {
        if (dto == null) return ErrorOptional.empty("Error - Mapper - VisitRequestResponseDTO cannot be null!");

        VisitRequestReponse visitRequestReponse = new VisitRequestReponse();

        ErrorOptional<VisitRequest> visitRequestErrorOptional = visitRequestMapper.toDomain(dto.visitRequestDTO);
        if(visitRequestErrorOptional.hasError()) return ErrorOptional.empty("Error - Mapper - Cannot converto visitRequestDTO into visitRequest!");

        visitRequestReponse.setVisitRequest(visitRequestErrorOptional.get());

        return ErrorOptional.of(visitRequestReponse);
    }
}
