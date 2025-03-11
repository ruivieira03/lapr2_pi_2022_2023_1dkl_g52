# US 011 - Listing Requests 

## 3. Design - User Story Realization 

### 3.1. Rationale

| Interaction ID | Question: Which class is responsible for...   | Answer                       | Justification (with patterns)                                                                                     |
|:---------------|:----------------------------------------------|:-----------------------------|:------------------------------------------------------------------------------------------------------------------|
| Step 1  		     | 	... interacting with the actor?              | ListPropertyOrdersUI         | **Pure Fabrication:** there is no reason to assign this responsibility to any existing class in the Domain Model. |
| 			  		        | 	... coordinating the US?                     | ListPropertyOrdersController | **Controller**                                                                                                    |
| 			  		        | 	... instantiating a new List of properties?  | PropertySale                 | **Creator (Rule 1):** in the DM PropertyOrders has the orders for the properties.                                 |
| 			  		        | 							                                       | Store                        | **Information Expert:** knows/has its own Employees                                                               |
| 			  		        | 							                                       | Employee                     | **Information Expert:** knows its own data (e.g. email)                                                           |
|                |                                               | PropertySale                 | **Information Expert:** knows the properties orders                                                               |
|                |                                               | OrderDecision                | **Information Expert:** knows the decisions (e.g. accepted or declined)                                           |
|                |                                               | Message                      | **Information Expert:** knows the message to send based on the decision                                           |
|                |                                               | PropertySaleRequest          | **Information Expert:** knows which property is being selected                                                    |                                                   |
| Step 2  		     | 							                                       |                              |                                                                                                                   |
| Step 3  		     | 	...saving the inputted data?                 | PropertySale                 | **Information Expert:** object created in step 1 has its own data.                                                |
| Step 4         | ...saving the inputted decision               | OrderDecision                | **Information Expert:** object created in step 1 has its own data.                                                |
| Step 4  		     | 	...listing and sorting all properties?       | PropertySale                 | **Information Expert:** knows all properties to be listed.                                                        |
| Step 5         | ...saving which Property was selected?        | PropertySaleRequest          | **Information Expert:** knows its data                                                                            | 
| Step 7  		     | 	... validating all data (local validation)?  | PropertySaleRequest          | **Information Expert:** owns its data.                                                                            | 
| 			  		        | 	... validating all data (global validation)? | PropertySale                 | **Information Expert:** knows all its tasks.                                                                      | 
| 			  		        | 	... saving the Agent's decision?             | OrderDecision                | **Information Expert:** owns the decision                                                                         | 
| Step 8         | ...sending email to client with decision?     | Message                      | **Information Expert:** owns the message                                                                          |
| Step 9  		     | 	... informing operation success?             | ListPropertyOrdersUI         | **Information Expert:** is responsible for user interactions.                                                     | 

### Systematization ##

According to the taken rationale, the conceptual classes promoted to software classes are: 

 * Store
 * PropertySale
 * Message
 * PropertySaleRequest
 * OrderDecision
 * Employee

Other software classes (i.e. Pure Fabrication) identified: 

 * ListPropertyOrdersUI  
 * ListPropertyOrdersController


## 3.2. Sequence Diagram (SD)

This diagram shows the full sequence of interactions between the classes involved in the realization of this user story.

![Sequence Diagram - Full](svg/us011-sequence-diagram-full.svg)


## 3.3. Class Diagram (CD)

![Class Diagram](svg/us011-class-diagram.svg)