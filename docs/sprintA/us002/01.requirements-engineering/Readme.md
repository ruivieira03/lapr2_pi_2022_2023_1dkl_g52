# US002 - Publishing of any sale announcement

## 1. Requirements Engineering


### 1.1 User Storie Description


As an agent, I can publish any sale announcement on the system, for example received through a phone call.



### 1.2 Customer Specifications and Clarafications

**From the specifications document:**

> The property owner provides property characteristics and requested price and sends the request to an agent, through a phone call or meeting in the agency.

> The property owner must provide information on: the type of property, the area, the location, the distance from city center, the requested price and one or more photographs.

> If the property is an apartment or a house, the owner also provides: the number of beedrooms, the number of bathroomsm the number of parking spaces and the available equipment, such as central heating and/or air conditioning.
 
> Incase the property is a house, the existence of a basement, an inhabitabke loft, and sun exposure must be registered as well.

> Upon reciving the order, the agent sets the commission and publishes the offer in the system.



**From client clarifications:**


> **Question:** What values should be considered when talking about sun exposure?
> 
> **Answer:** Sun exposure will take the following values: North, South, East, or West.

> **Question:** When the agent receives the phone call (as mentioned in US002) is it the agent who registers the order in the system or has the order already been entered into the system by the owner? Or is the phone call just for the owner to tell the agent that he registered a request in the system?
> 
> **Answer:** The agent registers the order in the system.

> **Question:** Also, since the only way that an agent can receive sale announcement request is through a phone call, wouldn't that contradict what was stated in the project description: "Owners go to one of the company's branches and meet with a real estate agent to sell or rent one or more properties, or they can use the company's application for the same purposes."?
> 
> **Answer:** US2: As an agent, I can publish any sale announcement on the system, for example received through a phone call. For example...

> **Question:** In one of the previous questions you have stated that for now the only way that a agent can receive the request to publish an announcement is through a phone call, however in the primary data necessary to create an announcement it's said that it's necessary to upload at least one photo of the property. Taking that into consideration, the announcement can't be fully made by only communicating with the owner through a phone call, how is it possible for an agent to publish an announcement without all the necessary data?
> 
> **Answer:** The owner can send the photograph by e-mail or any other means. The owner can even deliver the photograph to the store.

> **Question:** Another doubt that has emmerged is related to the identification of the owner of the property, isn't it required that the owner identifies themselves in the announcement ?
> 
> **Answer:** Info about the owner is not published.

> **Question:** Is it mandatory for the agent to input the commission value before publishing an announcement?
> 
> **Answer:** Yes.

> **Question:** What would be the attributes of the Owner and Agent?
>
> **Answer:** The Owner attributes are: the name, the citizen's card number, the tax number, the address, the email address and the contact
telephone number. The Agent is an employee of the company.




### 1.3 Accepetance Criteria

* **AC1:** All required fields must be filled in.



### 1.4 Found out Dependencies

**US 006** - Location will be defined by states, districts and cities that were previously defined by the system administrator 


### 1.5 Input and Output Data

**Input Data**

* Typed data:
  * area, 
  * location,
  * distance from city center,
  * requested price,
  * agent commission,
  * number of bedrooms (houses/apartments only),
  * number of bathrooms (houses/apartments only),
  * numer of parking spaces (houses/apartments only),
  * available equipment (houses/apartments only),
  * owner's name,
  * owner's citizen card number, 
  * owner's tax number,
  * owner's address,
  * owner's email adress,
  * owner's telephone number.


* Selected Data:
  * property type,
  * type of business (buying or renting),
  * type of commission,
  * existance of a basement (houses only),
  * existance of inhabitable loft (houses only),
  * sun exposure (houses only).


* Imported Data:
  * one or more photograps.


**Output Data:**

* (In)Sucess of the Operation

### 1.6 System SequenceDiagram (SDD)

**Other Alternatives might exist**


![System Sequence Diagram - Alternative one](svg/us002%20-%20Sequence%20Diagram%20-%20Alternative%20One-System_Sequence_Diagram__SSD_.svg)


### 1.7 Other relevant remarks

n/a