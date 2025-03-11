# US 012 - Import Legacy Data

## 1. Requirements Engineering


### 1.1. User Story Description


As a system administrator, I want to import information from a legacy system that has been in use in several agencies.


### 1.2. Customer Specifications and Clarifications 


**From the specifications document:**

> The application to be developed in this project will replace an application that was already in operation in some agencies and will be replaced in July 2023.

**From the client clarifications:**

>**Q:** From the provided CSV file, our team infers that this feature is meant to import data related to announcements only and not other data like, for example, employee information. Is this correct?
> 
>**A:** The new system should allow the System Admininstrator to import CSV files like the one provided in moodle. The legacy system is not able to export any other fields/attributes or formats.


>**Q:** Also, in relation to AC2, can we assume that a CSV file is any file whose filename ends with ".csv"?
> 
>**A:** The System Administrator should be able to load any file with the extension csv. The file content must be validated, showing a message to the system administrator if the file is empty or its content is not in the requested format.


>**Q**: In the legacy File provided, on the Location, I assume that the structure of the cell is: Address, City, State, and postal code. Assuming that this is the right structure, shouldn't the districts of the cities be there as well?
> 
>**A**: Please check carefully the legacy file. For instance, in line 5 we get "71 ST. NICHOLAS DRIVE, NORTH POLE, FAIRBANKS NORTH STAR,  AK, 99705". When loading the data, you should consider location addresses with and without district info.


>**Q:** It has been clearly previously established that there are two types of commissions (fixed and percentage). However, the CSV containing the data from the legacy system only has one column that references any type of commission: column U "commission(%)". Does that mean that there is only one type of commission, or was the fixed type accidentally left out or did I fail to notice the fixed commission type?
> 
>**A:** In the past our company only had the type of commissions that you see in the CSV file. Our legacy system has many limitations and this is why we are asking you to develop a new system.


>**Q:** In the CSV containing the data from the legacy system, properties classified as Apartment have themselves a value for Sun Exposure. However, the specification document describes this attribute as exclusive to the property type House: "In case the property is a house, the existence of a basement, an inhabitable loft, and sun exposure must be registered as well.". Do we add Sun Exposure to the apartment's attributes or does it stay exclusive to house?
> 
>**A:** When loading an apartment from the leagacy system (from the CSV file) you should ignore the attribute Sun Exposure. The CSV file contains data exported from a legacy system. The legacy system does not registers the same information that the system that you are developing now registers. For instance, the legacy system does not associates an agent to a property, therefore, when importing data from a legacy system you should create a agent/employee having:name=Legacy Agent; passport card number=000000000; tax number=000000000, email address=legacy@realstateUSA.com; contact telephone number = 0000000000; and associate this "legacy agent" with each property (make it the property responsable agent). Moreover, you should prepare your system to accept two formats for the passport number, one with 9 numbers and the other starts with the letter C followed by eight numbers.


>**Q:** Can the System Administrator, when wanting to import information from a legacy system, send more than one file at once?
> 
>**A:** Only one file at a time.


### 1.3. Acceptance Criteria


- **AC1:** The system administrator must be able to choose a file to import.
- **AC2:** The system should only accept CSV files.
- **AC3:** The file content must be validated, showing a message to the system administrator if the file is empty or its content is not in the requested format.
- **AC4:** The import operation, when successful, should trigger a success message to the system administrator.

### 1.4. Found out Dependencies

There are no known dependencies.

### 1.5 Input and Output Data

**Input Data:**

- Typed data
  - File to import

**Output Data:**
* (In)Success of the operation

### 1.6. System Sequence Diagram (SSD)

![us012-ssd.svg](svg%2Fus012-ssd.svg)

### 1.7 Other Relevant Remarks

