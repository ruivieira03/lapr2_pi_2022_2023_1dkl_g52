# US002 - Publishing of any sale announcement

## 1. Requirements Engineering

### 1.1 User Storie Description

As a client, I want to leave a message to the agent to schedule a visit to a property of my interest.

### 1.2 Customer Specifications and Clarafications

**From the specifications document:**

> After consulting a list of properties, the client can request to schedule a visit to the real estate agent
> for a specific property to verify its conditions. The agent receives the request, checks the
> availability and sends the response. If the customer accepts the order, it is automatically scheduled
> in the system.


**From client clarifications:**

> **Question:** Is all the required data for the message typed, or is any of it selected?
>
> **Answer:** For now the information should be typed.

 


### 1.3 Accepetance Criteria

* **AC1** A list of available properties must be shown, sorted from the most recent entries to the oldest.

* **AC2** The message must also include the client's name, phone number, preferred date and time slot (from x hour to y hour) for the property visit.

* **AC3** A client may post multiple visit requests, but only if those do not overlap each other.

* **AC4** The client must receive a success message when the request is valid and registered in the system.

### 1.4 Found out Dependencies

* PropertySales must be registered in the system
* User must be registered in the system

### 1.5 Input and Output Data

**Input Data**

* Typed data:
  * Year,
  * Month,
  * Day,
  * Hour,
  * minute.


* Selected Data:
  * PropertySale.


**Output Data:**

* (In)Sucess of the Operation

### 1.6 System SequenceDiagram (SDD)

**Other Alternatives might exist**

![System Sequence Diagram (SSD) - Alternative One](svg/us009%20-%20Sequence%20Diagram%20-%20Alternative%20One-System_Sequence_Diagram__SSD_.svg)

### 1.7 Other relevant remarks

n/a