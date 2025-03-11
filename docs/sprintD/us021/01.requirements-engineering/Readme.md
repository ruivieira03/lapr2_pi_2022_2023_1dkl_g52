# US 021 -  Register visit and opinion

## 1. Requirements Engineering


### 1.1. User Story Description


As an agent, at the end of the visit, I want to register the visit and the opinion about the business.



### 1.2. Customer Specifications and Clarifications 


**From the specifications document:**

>	Each task is characterized by having a unique reference per organization, a designation, an informal and a technical description, an estimated duration and cost as well as its classifying task category. 


>	As long as it is not published, access to the task is exclusive to the employees of the respective organization. 



**From the client clarifications:**

> **Question:** 
>  
> **Answer:** 


### 1.3. Acceptance Criteria


* **AC1:** The opinion must be an alphanumeric type with 200 characters.
* **AC2:** A classification scale from 1 (most improbable) to 5 (most probable) stating the agent opinion whether the deal will go through.
* **AC3:** No duplicate entries for the same visit should be allowed.


### 1.4. Found out Dependencies


* There is a dependency to "US009 - To schedule a visit" since a visit needs to exist to be able to complete it.


### 1.5 Input and Output Data


**Input Data:**

* Typed data:
	* an opinion
	
* Selected data:
	* a classification (1-5) 


**Output Data:**

* (In)Success of the operation

### 1.6. System Sequence Diagram (SSD)

**Other alternatives might exist.**

#### Alternative One

![System Sequence Diagram - Alternative One](svg/us021-system-sequence-diagram.svg)


### 1.7 Other Relevant Remarks

* 