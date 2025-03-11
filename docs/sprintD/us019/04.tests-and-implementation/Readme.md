# US 001 - Display listed properties 

# 4. Tests 

**Test 1:** Check that the sorting method is sorting correctly. 

	@Test
      
    void splitStoreListInTwo() {
        SplitStoreListAlgorithms algorithm = new SplitStoreListAlgorithms();

        List<List<StoreDTO>> lists = algorithm.splitStoreListInTwo(storeRepository.getSortedStoreList());

        storeDTO3.propertyCount = 2;
        storeDTO1.propertyCount = 1;
        storeDTO2.propertyCount = 1;

        List<StoreDTO> sub1 = new ArrayList<>();
        sub1.add(storeDTO3);
        List<StoreDTO> sub2 = new ArrayList<>();
        sub2.add(storeDTO2);
        sub2.add(storeDTO1);

        List<List<StoreDTO>> lists2 = new ArrayList<>();
        lists2.add(sub1);
        lists2.add(sub2);

        assertEquals(lists2.toString(),lists.toString());

    }

    


*It is also recommended to organize this content by subsections.* 

# 5. Construction (Implementation)


## Class SplitStoreAlgorithms

```java
public class SplitStoreListAlgorithms {

    private List<List<StoreDTO>> bestOption;

    public List<List<StoreDTO>> splitStoreListInTwo(List<StoreDTO> storesList) {


        List<List<StoreDTO>> finalList = splitList(storesList);

        permuteSubLists(finalList, storesList.size());

        return bestOption;

    }


    private List<List<StoreDTO>> splitList(List<StoreDTO> list) {
        int size = list.size();
        List<StoreDTO> subList1 = new ArrayList<>();
        List<StoreDTO> subList2 = new ArrayList<>();

        for (int i = 0; i < size / 2; i++)
            subList1.add(list.get(i));

        for (int i = size / 2; i < size; i++)
            subList2.add(list.get(i));


        return List.of(subList1, subList2);
    }

    /**
     * Finds and evaluates all the possible combinations of the array
     * This method implements the "Heap´s algorithm"
     * For more information about this algorithm: <a href="https://en.wikipedia.org/wiki/Heap%27s_algorithm">Heap Algorithm</a>
     * @param storeDTOs
     * @param k
     */
    private void permuteSubLists(List<List<StoreDTO>> storeDTOs, int k) {


        if (k == 1) {
            evaluateList(storeDTOs);

        } else {
            permuteSubLists(storeDTOs, k - 1);
            for (int i = 0; i < k - 1; i++) {

                if (k % 2 == 0) {
                    swap(storeDTOs, i, k - 1);
                } else {
                    swap(storeDTOs, 0, k - 1);
                }
                permuteSubLists(storeDTOs, k - 1);
            }

        }
    }

    private void swap(List<List<StoreDTO>> storeDTOS, int a, int b) {
        StoreDTO storeA;
        StoreDTO storeB;

        if (a < storeDTOS.get(0).size()) storeA = storeDTOS.get(0).get(a);
        else storeA = storeDTOS.get(1).get(a - storeDTOS.get(0).size());

        if (b < storeDTOS.get(0).size()) storeB = storeDTOS.get(0).get(b);
        else storeB = storeDTOS.get(1).get(b - storeDTOS.get(0).size());

        if (a < storeDTOS.get(0).size()) storeDTOS.get(0).set(a, storeB);

        else storeDTOS.get(1).set(a - storeDTOS.get(0).size(), storeB);

        if (b < storeDTOS.get(0).size()) storeDTOS.get(0).set(b, storeA);

        else storeDTOS.get(1).set(b - storeDTOS.get(0).size(), storeA);

    }


    private int getNumberOfElements(List<StoreDTO> list) {

        int nElements = 0;

        for (StoreDTO store : list) {
            nElements = nElements + store.propertyCount;
        }
        return nElements;
    }

    private int calculateStoreListsPropertiesDifference(List<List<StoreDTO>> subLists) {
        return Math.abs(getNumberOfElements(subLists.get(0)) - getNumberOfElements(subLists.get(1)));
    }

    private void evaluateList(List<List<StoreDTO>> subLists) {
        if (bestOption == null) {
            bestOption = new ArrayList<>();
            for (int i = 0; i < subLists.size(); i++) {
                bestOption.add(new ArrayList<>());
                for (int j = 0; j < subLists.get(i).size(); j++) {
                    bestOption.get(i).add(subLists.get(i).get(j));
                }
            }
        }
        if (calculateStoreListsPropertiesDifference(subLists) < calculateStoreListsPropertiesDifference(bestOption))
            bestOption = new ArrayList<>(subLists);

    }
}
```


## Class NetworkStoresSplitController

```java
public class NetworkStoresSplitController {


    public ErrorOptional<List<List<StoreDTO>>> NetworkStoresSplit() {

        Repositories repositories = Repositories.getInstance();

        StoreRepository storeRepository = Repositories.getStoreRepository();

        List<StoreDTO> storeList = storeRepository.getSortedStoreList();

        SplitStoreListAlgorithms algorithms = new SplitStoreListAlgorithms();

        List<List<StoreDTO>> splitedList = algorithms.splitStoreListInTwo(storeList);

        return ErrorOptional.of(splitedList);
    }
}

```

# 6. Integration and Demo 
n/a

# 7. Observations

n/a



