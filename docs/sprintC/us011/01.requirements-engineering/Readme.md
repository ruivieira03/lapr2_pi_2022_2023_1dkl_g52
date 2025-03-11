# US 011 - Listing Requests 

## 1. Requirements Engineering


### 1.1. User Story Description


As an agent, I want to list real estate purchase orders to accept or decline a purchase order for a property. After accepting or declining, an email notification should be sent to the customer.


### 1.2. Customer Specifications and Clarifications 


**From the specifications document:**

>	Each task is characterized by having a unique reference per organization, a designation, an informal and a technical description, an estimated duration and cost as well as the its classifying task category.
>   As long as it is not published, access to the task is exclusive to the employees of the respective organization. 



**From the client clarifications:**


> **Question:** Does grouping purchase orders by property mean that only properties are displayed, and only after a property is selected are its requests shown? Or are all the requests shown but requests of the same property are shown together?
>  
> **Answer:** The system should show (to the agent) a list of properties that have prurchase orders. For each property the system should show a list.

> **Question:** When properties are sorted form oldest to most recent, does this relate to when the property was added to the system or the property's date of construction?
>
> **Answer:** This sorting should be made using the date when the property announcement was published by the agent.

> **Question:** Regarding US011 should we provide all the property characteristics to assist agents in choosing the appropriate purchase order?
> 
>  **Answer:** Yes. Yes.

> **Question:** When the agent declines an order, she has to be removed from the list and system?
> 
> **Answer:** The order should be removed from the list but not from the system.

### 1.3. Acceptance Criteria


* **AC1:** The list of purchase orders should be grouped by property. The properties should be sorted from the oldest to the most recent one. For each property, the list of purchase orders should be sorted by the amount offered, the highest offer must appear first.
* **AC2:** For each offer, the agent must be able to accept or decline it. The action of accepting or declining an offer should trigger an email notification to the client.
* **AC3:** When a purchase order is accepted, all the other orders should be declined, and a message sent to the client.
* **AC4:** If a property does not contain any offers, the system should show an empty list of offers.


### 1.4. Found out Dependencies


* There is a dependency to "US010 Place order to purchase the property" since orders need to exist to the agent be able to accept or decline them.


### 1.5 Input and Output Data


**Input Data:**

* Typed data:
	* if the order is accepted/declined
	
* Selected data:
	* purchase orders


**Output Data:**

* E-mail sent to the customer with the decision

### 1.6. System Sequence Diagram (SSD)

**Other alternatives might exist.**

#### Alternative One

![System Sequence Diagram - Alternative One](svg/us011-system-sequence.svg)

### 1.7 Other Relevant Remarks
