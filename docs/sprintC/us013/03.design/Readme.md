# US 013 - List all employees 

## 3. Design - User Story Realization 

### 3.1. Rationale



| Interaction ID | Question: Which class is responsible for... | Answer                 | Justification (with patterns)                                                                                     |
|----------------|:--------------------------------------------|:-----------------------|:------------------------------------------------------------------------------------------------------------------|
| Step 1         | ... interacting with the user?              | ListEmployeeUI         | **Pure Fabrication:** there is no reason to assign this responsibility to any existing class in the Domain Model. |
|                | ... coordinating the US?                    | ListEmployeeController | **Controller**                                                                                                    |
|                | ... knowing each store?                     | StoreRepository        | **Pure Fabrication**                                                                                              |
|                | ... knowing each annoucement?               | AnnoucementRepository  | **Pure Fabrication**                                                                                              |
| Step 2         | ... showing the employee list?              | ListEmployeeUI         | **Pure Fabrication:** there is no reason to assign this responsibility to any existing class in the Domain Model. |
|                | ... storing the employee list?              |                        |                                                                                                                   |
|                | ... retrieving the employee list?           | ListEmployeeController | **Controller**                                                                                                    |


### Systematization ##

According to the taken rationale, the conceptual classes promoted to software classes are: 

* Domain Classes (e.g. Employee, NetworkManager, StoreManager, Agent, SystemAdministrator)
* Repositories (e.g. EmployeeRepository, SystemAdministratorRepository)

Other software classes (i.e. Pure Fabrication) identified: 

 * ListEmployeeUI
 * ListEmployeeController
 * StoreRepository
 * PropertySaleRepository
 * StoreDTO
 * StoreMapper


## 3.2. Sequence Diagram (SD)

![us013-sd.svg](svg%2Fus013-sd.svg)

## 3.3. Class Diagram (CD)

![us013-class-diagram.svg](svg%2Fus013-class-diagram.svg)