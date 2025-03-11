# US 012 - Import Legacy Data 

## 3. Design - User Story Realization 

### 3.1. Rationale



| Interaction ID | Question: Which class is responsible for...         | Answer                                                        | Justification (with patterns)                                                                                                           |
|:---------------|:----------------------------------------------------|:--------------------------------------------------------------|:----------------------------------------------------------------------------------------------------------------------------------------|
| Step 1         | ... interacting with the user?                      | ImportDataUI                                                  | **Pure Fabrication:** there is no reason to assign this responsibility to any existing class in the Domain Model.                       |
|                | ... coordinating the US?                            | ImportDataController                                          | **Controller**                                                                                                                          |
| Step 2         | ... showing the file list?                          | ImportDataUI                                                  | **Pure Fabrication:** there is no reason to assign this responsibility to any existing class in the Domain Model.                       |
|                | ... storing the file list?                          |                                                               |                                                                                                                                         |
|                | ... retrieving the file list?                       | ImportDataController                                          | **Controller**                                                                                                                          |
| Step 3         |                                                     |                                                               |                                                                                                                                         |
| Step 4         |                                                     |                                                               |                                                                                                                                         |
| Step 5         | ... validating the file type? (csv)                 | FileReader                                                    | **Pure Fabrication** and **Information Expert:** knows the allowed file types for importing data.                                       |                                                                                                              
|                | ... verifying the file isn't empty?                 | FileReader                                                    | **Pure Fabrication:** there is no reason to assign this responsibility to any existing class in the Domain Model.                       |
| Step 6         |                                                     |                                                               |                                                                                                                                         |
| Step 7         | ... verifying the files content format.             | Domain Classes (e.g. Store, Employee)                         | **Information Expert:** knows its own data and its correct format.                                                                      |
|                | ... storing the information from the file?          | Repository Classes (e.g. StoreRepository, EmployeeRepository) | **Information Expert:** knows all the objects of its type.                                                                              |
|                | ... instantiating the new information?              | Domain Classes (e.g. Store, Employee)                         | **Creator**                                                                                                                             |
| Step 8         | ... notifying the user about the operation success? | ImportDataUI                                                  | **Pure Fabrication:** **Pure Fabrication:** there is no reason to assign this responsibility to any existing class in the Domain Model. |

### Systematization ##

According to the taken rationale, the conceptual classes promoted to software classes are: 

* Domain Classes (e.g. Store, Employee)

Other software classes (i.e. Pure Fabrication) identified: 

 * ImportDataUI
 * ImportDataController
 * FileReader


## 3.2. Sequence Diagram (SD)



## 3.3. Class Diagram (CD)

![Class Diagram](svg/us006-class-diagram.svg)