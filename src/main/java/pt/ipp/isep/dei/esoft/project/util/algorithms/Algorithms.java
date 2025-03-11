package pt.ipp.isep.dei.esoft.project.util.algorithms;

import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.VisitRequestDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
