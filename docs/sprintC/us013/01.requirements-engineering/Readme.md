# US 014 - List all employees working in every store of the network

## 1. Requirements Engineering


### 1.1. User Story Description


As a network manager, I want to list all employees working in every store
of the network.


### 1.2. Customer Specifications and Clarifications 


**From the specifications document:**

> The application to be developed in this project will replace an application that was already in operation in some agencies and will be replaced in July 2023.



**From the client clarifications:**



### 1.3. Acceptance Criteria


- **AC1:** The list of employees should be alphabetically sorted and grouped by store.
- **AC2:** Stores should be sorted according to their property listings, from the one with more listings to the one with less listings.
- **AC3:** Each store should state how many property listings it has.


### 1.4. Found out Dependencies

- **Depends on US003** : to register a new employee, the network manager must be registered in the system.
- **Depends on US005** : to register a store, the network manager must be assigned to a store.
- **Depends on US008** : See the list of the properties annoucements requests, to group employees by stores sorted by properties annoucements number.


### 1.5 Input and Output Data

**Output Data:**

* Insucces/Success of the operation
* List of all employees

### 1.6. System Sequence Diagram (SSD)

![us013-ssd-US013___List_all_employees.svg](svg%2Fus013-ssd-US013___List_all_employees.svg)

### 1.7 Other Relevant Remarks

* Can be only one Network Manager
* Are only one Network of Stores
* Network manager should be logged in the aplication

