# US 006 - To create a Task 

## 3. Design - User Story Realization 

### 3.1. Rationale

**SSD - Alternative 1 is adopted.**

| Interaction ID                                                   | Question: Which class is responsible for... | Answer                   | Justification (with patterns)                                                                                 |
|:-----------------------------------------------------------------|:--------------------------------------------|:-------------------------|:--------------------------------------------------------------------------------------------------------------|
| Step 1  		                                                       | 	... interacting with the actor?            | ListPropertiesUI         | Pure Fabrication: there is no reason to assign this responsibility to any existing class in the Domain Model. |
| 			  		                                                          | 	... coordinating the US?                   | ListPropertiesController | Controller                                                                                                    |
| Step 2  		                                                       | 	...knowing the attributes to show?						   | Property                 | IE: Knows its own attributes.                                                                                 |
| Step 3  		                                                       | 	...saving the selected data?               | PropertyList             | IE: know all properties .                                                                                     |
|                                                                  | ... validating all data?                    | PropertyList             | IE: know its own data.                                                                                        |
| Step 4  		                                                       | 	...knowing the sort options to show?       | PropertyList             | IE: selected data sort PropertyList.                                                                          |
| Step 5  		                                                       | 	... saving the selected sort option?       | PropertyList             | IE: selected data sort PropertyList.                                                                          |
|                                                                  | ... validating all data?                    | PropertyList             | IE: knows its own data.                                                                                       |
| Step 6  		                                                       | 	... showing the list of properties?						  | ListPropertiesUI         | IE: is responsible for user interactions.                                                                     |              

### Systematization ##

According to the taken rationale, the conceptual classes promoted to software classes are: 

 * Property
 * PropertyList

Other software classes (i.e. Pure Fabrication) identified: 

 * ListPropertiesUI  
 * ListPropertiesController


## 3.2. Sequence Diagram (SD)

### Full Diagram

This diagram shows the full sequence of interactions between the classes involved in the realization of this user story.

![Sequence Diagram - Full](svg/us006-sequence-diagram-full.svg)


## 3.3. Class Diagram (CD)

![Class Diagram](svg/us006-class-diagram.svg)