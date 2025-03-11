# US 001 - To display listed properties 

## 1. Requirements Engineering


### 1.1. User Story Description


As an unregistered user, I want to display listed properties.



### 1.2. Customer Specifications and Clarifications 


**From the specifications document:**

>	Each property is characterized by having the type of property (apartment, house or land), the area in m2, the location, the distance from the city centre, the requested price and one or more photographs.

>	If the property is an apartment or a house, it also must be provided the number of bedrooms, the number of bathrooms, the number of parking spaces aand the available equipment.

>	In case the property is a house, the existance of basement, an inhabitable loft and the sun exposure must be registered as well.

**From the client clarifications:**

> **Question:** In the project description it is stated that "the client is, then, responsible for being able to consult the properties by type, number of rooms, and sort by criteria such as price or the parish where the property is located.". Is the client able to sort properties by only these 4 criteria or is he able to sort properties by any of the properties' characteristics?
>  
> **Answer:** The client should be able to select the type of business (renting or buying), the type of property and the number of rooms. Then, the client should be able to sort properties by price or by parish where the property is located.
If the client does not select the type of business, the type of property and the number of rooms, the application should allow the client to sort all properties that are on sale or on renting.


> **Question:** In the project's documentation it's mentioned that "All those who wish to use the application must be authenticated", but in the US1 it's said that an unregistered user can see a list of properties. Can users who aren't authenticated do this?
>
> **Answer:** Non-authenticated users can only list properties.

> **Question:** When an unregistered user wants to list properties, the list given by the program is sorted by default with which criteria? For example the list is shown with the properties sorted by most recently added?
>
> **Answer:** Thank you for your suggestion. By default, the list should be shown with the properties sorted by most recently added.

### 1.3. Acceptance Criteria

**AC1:** The client should choose only one value for each feature of the property.

**AC1:** When the client selects to display the listed properties, if there are no listed properties, it should show an empty list. 
### 1.4. Found out Dependencies


n/a


### 1.5 Input and Output Data


**Input Data:**

* Typed data:
	
	
* Selected data:


* to list the properties 
* a type of business
* a type of property
* a number of rooms
* Sort by price, by city or by state

      
      

**Output Data:**

* listed properties

### 1.6. System Sequence Diagram (SSD)

#### Other alternatives might exist

![System Sequence Diagram](svg/us001-system-sequence-diagram-System_Sequence_Diagram__SSD_.svg)

### 1.7 Other Relevant Remarks

* n/a