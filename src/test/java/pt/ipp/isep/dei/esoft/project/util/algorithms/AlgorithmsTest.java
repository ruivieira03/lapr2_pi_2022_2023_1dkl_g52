package pt.ipp.isep.dei.esoft.project.util.algorithms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.VisitRequestDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AlgorithmsTest {

    private static List<VisitRequestDTO> visitRequestDTOList = new ArrayList<>();

    @BeforeEach
    static void setUp() {
        //PropertySaleDTO property = new PropertySaleDTO(new HouseDTO(), new AgentDTO(), 100000, 1000, LocalDate.of(2023, 1, 1), new ArrayList<>(), new ArrayList<>());
//
        //VisitRequestDTO visitRequestDTO1 = new VisitRequestDTO(new ClientDTO(), new PropertySaleDTO(), LocalDateTime.of(2023, 6, 1, 10, 0), LocalDateTime.of(2023, 6, 1, 11, 0));
        //VisitRequestDTO visitRequestDTO2 = new VisitRequestDTO(new ClientDTO(), new PropertySaleDTO(), LocalDateTime.of(2023, 5, 12, 10, 12), LocalDateTime.of(2023, 5, 12, 13, 0));
        //VisitRequestDTO visitRequestDTO3 = new VisitRequestDTO(new ClientDTO(), new PropertySaleDTO(), LocalDateTime.of(2023, 3, 23, 16, 40), LocalDateTime.of(2023, 3, 23, 18, 5));
        //VisitRequestDTO visitRequestDTO4 = new VisitRequestDTO(new ClientDTO(), new PropertySaleDTO(), LocalDateTime.of(2023, 8, 10, 20, 0), LocalDateTime.of(2023, 8, 10, 22, 0));
        //VisitRequestDTO visitRequestDTO5 = new VisitRequestDTO(new ClientDTO(), new PropertySaleDTO(), LocalDateTime.of(2023, 1, 1, 1, 0), LocalDateTime.of(2023, 1, 1, 11, 0));
//
        //visitRequestDTOList = List.of(visitRequestDTO1, visitRequestDTO2, visitRequestDTO3, visitRequestDTO4, visitRequestDTO5);

    }

    @Test
    void bubbleSort() {
        Algorithms.bubbleSort(visitRequestDTOList);

        assertEquals(visitRequestDTOList.get(0).visitStart, LocalDateTime.of(2023, 1, 1, 1, 0));
        assertEquals(visitRequestDTOList.get(1).visitStart, LocalDateTime.of(2023, 3, 23, 16, 40));
        assertEquals(visitRequestDTOList.get(2).visitStart, LocalDateTime.of(2023, 5, 12, 10, 12));
        assertEquals(visitRequestDTOList.get(3).visitStart, LocalDateTime.of(2023, 6, 1, 10, 0));
        assertEquals(visitRequestDTOList.get(4).visitStart, LocalDateTime.of(2023, 8, 10, 20, 0));

    }

    @Test
    void mergeSort() {
        Algorithms.mergeSort(visitRequestDTOList, 0, visitRequestDTOList.size() - 1);

        assertEquals(visitRequestDTOList.get(0).visitStart, LocalDateTime.of(2023, 1, 1, 1, 0));
        assertEquals(visitRequestDTOList.get(1).visitStart, LocalDateTime.of(2023, 3, 23, 16, 40));
        assertEquals(visitRequestDTOList.get(2).visitStart, LocalDateTime.of(2023, 5, 12, 10, 12));
        assertEquals(visitRequestDTOList.get(3).visitStart, LocalDateTime.of(2023, 6, 1, 10, 0));
        assertEquals(visitRequestDTOList.get(4).visitStart, LocalDateTime.of(2023, 8, 10, 20, 0));


    }
}