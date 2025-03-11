# US 013 - List all employees 

## 3. Design - User Story Realization 

### 3.1. Rationale



| Interaction ID | Question: Which class is responsible for...                    | Answer                       | Justification (with patterns)                                                                                     |
|----------------|:---------------------------------------------------------------|:-----------------------------|:------------------------------------------------------------------------------------------------------------------|
| Step 1         | ... interacting with the user?                                 | NetworkStoresSplitUI         | **Pure Fabrication:** there is no reason to assign this responsibility to any existing class in the Domain Model. |
|                | ... coordinating the US?                                       | NetworkStoresSplitController | **Controller**                                                                                                    |
|                | ... knowing the current stores?                                | StoreRepository              | **Information Expert:** contains stores.                                                                          |
|                |                                                                | Store                        | **Information Expert:** knows its own data.                                                                       |
|                |                                                                | StoreDTO                     | **Information Expert:** knows its own data.                                                                       |
|                |                                                                | SplitStoreListAlgorithms     | **Pure Fabrication:** this class takes the responsibility to calculate and find the optimal list.                 |
| Step 2         |                                                                |                              |                                                                                                                   |
| Step 3         | ... getting store lists?                                       | StoreRepository              | **Information Expert:** knows all stores.                                                                         |
|                | ... temporarily saving store lists?                            | StoreDTO                     | **Information Expert:** knows its own data.                                                                       |
|                | ... searching for the optimal division for the list of stores? | SplitStoreListAlgorithms     | **Pure Fabrication:** this class takes the responsibility to calculate and find the optimal list.                 |
| Step 4         | ... validating data (global validation)?                       | StoreRepository              | **Information Expert:** knows all stores.                                                                         | 
| Step 5         | ... validating data (local validation)?                        | Store                        | **Information Expert:** knows it's own data.                                                                      |                                                                                                                   ||
| Step 6         | ... showing the obtained sublists                              | NetworkStoresSplitUI         | **Pure Fabrication:** there is no reason to assign this responsibility to any existing class in the Domain Model. |




### Systematization ##

According to the taken rationale, the conceptual classes promoted to software classes are: 

* Store

Other software classes (i.e. Pure Fabrication) identified: 

 * NetworkStoresSplitUI 
 * StoreRepository
 * NetworkStoresSplitController
 * StoreDTO
 * 


## 3.2. Sequence Diagram (SD)

![us019-ssd.svg](svg%2Fus019-ssd.svg)

## 3.3. Class Diagram (CD)

![us019-class-diagram.svg](svg%2Fus019-class-diagram.svg)