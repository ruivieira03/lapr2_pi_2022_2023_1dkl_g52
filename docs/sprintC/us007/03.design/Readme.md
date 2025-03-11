# US 007 - Registration 

## 3. Design - User Story Realization 

### 3.1. Rationale

**SSD - Alternative 1 is adopted.**

| Interaction ID | Question: Which class is responsible for...     | Answer                   | Justification (with patterns)          |
|:---------------|:------------------------------------------------|:-------------------------|:---------------------------------------|
| Step 1         | ...interacting with the user?                   | RegistrationUI           | Pure Fabrication                       |
|                | ...coordinating the US                          | RegistrationController   | Controller                             |
| Step 2         | ...knowing what attributes to ask for?          | RegistrationUI           | Pure Fabrication                       |
| Step 3         | ...instantiating new user?                      | AuthenticationRepository | Creator                                |
|                | ...validating user data(local validation)?      | AuthFacade               | IE : Created user knows its own data   |
|                | ...adding the user?                             | AuthenticationRepository | IE : Owns all the users                |
|                | ...saving the user?                             | AuthenticationRepository | IE : Owns all the users                |
|                | ...validating the user(global validation)?      | AuthenticationRepository | IE : Knows its users                   |
|                | ...validating input data(localValidation) ?     | Client                   | IE:  Created client knows its own data |
|                | ...saving the inputted data?                    | Client                   | IE:  Created client has its own data.  |
|                | ...instantiating a new client?                  | ClientsRepository        | Creator                                |
|                | ...validating created client(globalValidation)? | ClientsRepository        | IE: Knows all its clients              |
|                | ...saving the created client?                   | ClientsRepository        | IE: Owns all clients                   |
| Step 4         | informing success in the operation?             | RegistrationUI           | Pure Fabrication                       |


### Systematization ##

According to the taken rationale, the conceptual classes promoted to software classes are: 

 * Client


Other software classes (i.e. Pure Fabrication) identified: 

 * RegistrationUI  
 * ClientsRepository
 * AuthFacade
 * AuthenticationRepository


## 3.2. Sequence Diagram (SD)

###  Full Diagram

This diagram shows the full sequence of interactions between the classes involved in the realization of this user story.

![Sequence Diagram - Full](svg/us007-sequence-diagram-full.svg)

## 3.3 Class Diagram (CD)

![Class Diagram](svg/us007-class-diagram.svg)