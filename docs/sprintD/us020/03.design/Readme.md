# US 013 - List all employees 

## 3. Design - User Story Realization 

### 3.1. Rationale


| Interaction ID | Question: Which class is responsible for... | Answer                            | Justification (with patterns)      |
|:---------------|:--------------------------------------------|:----------------------------------|:-----------------------------------|
| Step 1         | ...interacting with the actor?              | ReadAppointementRequestUI         | Pure Fabrication                   |
|                | ...coordinating the US?                     | ReadAppointementRequestController | Controller                         |
| Step 2         | ...knowing the requests to display?         | VisitRequestRepository            | IE: Has all visit requests         |
|                | ...knowing the agent in the system?         | UserSession                       |                                    |
|                | ...knowing the visit details to display?    | VisitRequestResponse              | IE: Knows its own attributes       |
| Step 3         | ...knowing the notifications                | Notification                      | Pure Fabrication                   | 
| Step 4         |                                             |                                   |                                    | 
| Step 5         | ...saving the input data?                   | ReadAppointementRequestUI         | Pure Fabrication                   | 
| Step 6         |                                             |                                   |                                    | 
| Step 7         | ...accept or decline request                | ReadAppointementRequestUI         | Pure Fabrication                   | 
| Step 8         | ...saving the input data?                   | ReadAppointementRequestUI         | Pure Fabrication                   |
| Step 9         | ...sending the notification to the agent?   | Notification                      | IE: Method used to send the emails |
|                | ...inform (in)Success of operation          | ReadAppointementRequestUI         | Pure Fabrication                   | 


### Systematization ##

According to the taken rationale, the conceptual classes promoted to software classes are: 

* Domain Classes 
  - VisitRequest
  - Client
  - Notification
  - Message
  - VisitRequestResponse
  - PropertySale
* Repositories (e.g. EmployeeRepository, SystemAdministratorRepository)
  - VisitRequestRepository
  - ClientRepository

Other software classes (i.e. Pure Fabrication) identified: 

 * ReadAppointementRequestUI
 * ReadAppointementRequestController


## 3.2. Sequence Diagram (SD)

![us020_sequence_diagram.svg](svg%2Fus020_sequence_diagram.svg)

## 3.3. Class Diagram (CD)

![us020_class_diagram.svg](svg%2Fus020_class_diagram.svg)