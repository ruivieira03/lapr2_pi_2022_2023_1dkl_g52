# US 020 - Read the response for the appointment request

## 1. Requirements Engineering

### 1.1. User Story Description


As a client, I want to read the response for the appointment request, to
accept or reject it.


### 1.2. Customer Specifications and Clarifications 


**From the specifications document:**

> The application to be developed in this project will replace an application that was already in operation in some agencies and will be replaced in July 2023.



**From the client clarifications:**



### 1.3. Acceptance Criteria


- **AC1:** The agent must be notified when the message is displayed to the client.
- **AC2:** The appointment request must provide information about the property and date of the appointment.
- **AC3:** When the appointment is rejected, the client must specify the reason.
- **AC4:** The appointment request must provide the agent name and phone number.


### 1.4. Found out Dependencies

- **Depends on US016** : As an agent, when viewing a booking request, I want to respond to the user that scheduled the visit.
 

### 1.5 Input and Output Data
**Input Data**

* Select Notification
* Accept or Decline Appointement
* reason of declining

**Output Data:**

* Client Notifications
* Appointement Info

### 1.6. System Sequence Diagram (SSD) 

![us020-system-sequence-diagram-System_Sequence_Diagram___US020.svg](svg%2Fus020-system-sequence-diagram-System_Sequence_Diagram___US020.svg)

### 1.7 Other Relevant Remarks

