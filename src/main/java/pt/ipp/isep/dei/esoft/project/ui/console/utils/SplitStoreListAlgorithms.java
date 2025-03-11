package pt.ipp.isep.dei.esoft.project.ui.console.utils;


import pt.ipp.isep.dei.esoft.project.domain.store.mappers.dto.StoreDTO;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SplitStoreListAlgorithms {

    public static int minDiffFinal = Integer.MAX_VALUE;
    public static float timeSecondsFinal;

    public List<List<StoreDTO>> splitStoreListInTwo(List<StoreDTO> storesList , int numberOfStores) {
        long timeS = System.nanoTime();

        List<StoreDTO> storeListToUse = new ArrayList<>();

            for (int i = 0; i < numberOfStores; i++)
                storeListToUse.add(storesList.get(i));


        List<int[]> numberList = loadList(storeListToUse);

        List<int[]> listA = new ArrayList<>();
        List<int[]> listB = new ArrayList<>();

        findClosestPartition(numberList, listA, listB);

        long timeE = System.nanoTime();
        timeSecondsFinal = (float) (timeE - timeS) /1_000_000_000;


        return orderStoreLists(List.of(listA, listB), storesList);
    }


    public void findClosestPartition(List<int[]> nums, List<int[]> list1, List<int[]> list2) {

        int n = nums.size();
        int minDiff = Integer.MAX_VALUE;
        int signature = 0;

        long n2 = (long) Math.pow(2, n);


        for (int i = 0; i < n2; i++) {

            int sum1 = 0;
            int sum2 = 0;

            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) > 0) {
                    sum1 += nums.get(j)[1];
                } else {
                    sum2 += nums.get(j)[1];
                }
            }

            int diff = Math.abs(sum1 - sum2);
            if (diff < minDiff) {
                minDiff = diff;
                setMinDiff(minDiff);
                signature = i;
            }



        }

        for (int j = 0; j < n; j++) {
            if (((1 << j) & signature) > 0) {
                list1.add(nums.get(j));
            } else {
                list2.add(nums.get(j));
            }
        }
    }



    private List<int[]> loadList(List<StoreDTO> storesList) {
        List<int[]> list = new ArrayList<>();

        for (int i = 0; i < storesList.size(); i++) {
            list.add(new int[]{i, storesList.get(i).propertyCount});
        }

        return list;
    }


    public List<List<StoreDTO>> orderStoreLists(List<List<int[]>> lists, List<StoreDTO> storesList) {
        List<StoreDTO> listA = new ArrayList<>();
        for (int[] i : lists.get(0)) {
            listA.add(storesList.get(i[0]));
        }

        List<StoreDTO> listB = new ArrayList<>();
        for (int[] i : lists.get(1)) {
            listB.add(storesList.get(i[0]));
        }

        return List.of(listA, listB);
    }

    public int getMinDiffFinal() {
        return minDiffFinal;
    }
    public float getTimeSecondsFinal(){return timeSecondsFinal;}

    public void setMinDiff(int mindif) {
        minDiffFinal = mindif;
    }
}