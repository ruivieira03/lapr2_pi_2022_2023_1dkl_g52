# Supplementary Specification (FURPS+)

## Functionality

### Common across several US

- The application must allow viewing of all registered properties and give the option of filtering and sorting them according to user preferences.
- Cities, states and districts must be specified in the application so that customers can select this data whenever they need it.

### Audit

- ###### Whenever a property is registered in the application, its registration date must be recorded.

### Reporting

- After a visit to a house, the agent responsible for the same records the visit and an indication of whether he thinks the deal will go through or not.

### Security

- All registered information, except the agency commission, can be accessed by the client who intends to
  buy or rent the property.
- All those who wish to use the application must be authenticated with a password of seven alphanumeric characters.

### System Management 

- Every store has a store manager, responsible for  monitoring and streamlining the storeNetwork with the
  aim of getting to know better the business carried out and to analyse and evaluate the performance
  of employees.
- The manager of the network intends to analyse the performance of each of the branches and the
  global behaviour of the network on a daily basis.
- The system administrator will be responsible for preparing the system in order to facilitate the insertion of advertisements and facilitate the use of the application.


### Workflow

- The client must be able to see the status of his visit, sale, lease or purchase requests in the application.


## Usability

###### _Evaluates the user interface. It has several subcategories, among them: error prevention; interface aesthetics and design; help and documentation; consistency and standards._

### Error prevention

- All data must always be validated.

### Adequacy of the interface for different types of users

- Unregistered users can only use the application to display listed properties and view the properties details.
- Registered users can use all functionalities of the application.

## Reliability

###### _Refers to the integrity, compliance and interoperability of the software. The requirements to be considered are: frequency and severity of failure, possibility of recovery, possibility of prediction, accuracy, average time between failures._

There are no such requirements.

## Performance

###### _Evaluates the performance requirements of the software, namely: response time, start-up time, recovery time, memory consumption, CPU usage, load capacity and application availability._

There are no such requirements.

## Supportability

###### _The supportability requirements gathers several characteristics, such as: testability, adaptability, maintainability, compatibility, configurability, installability, scalability and more._

* The development team must implement unit tests for all methods, except for methods that
implement Input/Output operations. 
* The unit tests should be implemented using the JUnit 5 framework. 
* The JaCoCo plugin should be used to generate the coverage report.
## +

### Design Constraints

###### _Specifies or constraints the system design process. Examples may include: programming languages, software process, mandatory standards/patterns, use of development tools, class library, etc._

* The application must be developed in Java language; 
* The aplication must be developed using the IntelliJ IDE or NetBeans; 
* The application graphical interface is to be developed in JavaFX 11;
* Adopt the best practices for identifying requirements, and for OO software analisys and design;
* All the images/figures produced during the software development process should be recorded in
SVG format.
* The team must use Javadoc to generate useful documentation for Java code.

### Implementation Constraints

###### _Specifies or constraints the code or construction of a system such as: mandatory standards/patterns,implementation languages, database integrity, resource limits, operating system._

* The application must be developed in Java language;
* The team must adopt recognized coding conventions and standards (e.g., CamelCase);
* The application should use object serialization to ensure data persistence between two runs of the
  application.

### Interface Constraints

###### _Specifies or constraints the features inherent to the interaction of the system being developed with other external systems._

* All those who wish to use the application must be authenticated with a password of seven alphanumeric characters,
including three capital letters and two digits(exception for unregistred users).
* The application must support the English language.

### Physical Constraints

###### _Specifies a limitation or physical requirement regarding the hardware used to house the system, as for example: material, shape, size or weight._

There are no such requirements.