# US 018 - Analyse deals

## 1. Requirements Engineering

### 1.1. User Story Description

As a store manager, I intend to analyse the deals (only sale of houses and apartments) made. In particular, I intend to
compare property sale values with the forecast values. The prediction of the value of the business/property must be
estimated based on the characteristics of the property and using linear and multilinear regression algorithms.

### 1.2. Customer Specifications and Clarifications

**From the specifications document:**

> The manager of the network intends to analyse the performance of each of the branches and the
> global behaviour of the network on a daily basis.



**From the client clarifications:**

> **Question:** When the store manager analyses the property values, can he analyse one property, all the properties, or
> a selection of properties of their choosing?
>
> **Answer:** The system should present the price estimates (using the selected regression model) for all house and
> apartment deals made.


> **Question:** When it comes to analysing the properties can the store manager see all the properties in the system?
>
> **Answer:** The goal of this US is to compare property (only house and apartment properties) sale values with the
> forecast values. Therefore, the system should only show all house and apartments deals made, including deals that were
> imported from the legacy system.


> **Question:** When the store manager selects simple-linear regression to analyse the deals, does he also select one of
> the specified relevant parameters to serve as the [in]dependent variable in the regression model? ("property area in
> square feet, the distance from the center, the number of bedrooms, the number of bathrooms and the number of parking
> spaces").
>
> **Answer:** The store manager should choose the independent variable and the regression model to be used to fit the
> data.
> Important: The property area is always in square feet. There is no need to convert the values loaded from the file.
>
> **Question:** A report containing regression methods, confidence intervals, and other US-related aspects of MATCP is
> mentioned in the Sprint D requirements document for US018. The team is wondering if this "report" refers to information
> that should be displayed in the application itself, the user manual, or both. And if the "report" isn't going to be
> displayed in the app, are we only going to show the user the forecasted price in relation to the chosen property and the
> regression model with all the necessary statistics?
>
> **Answer:** In US18 the application should show, to the store manager, the forecasts, the regression model,
> statistics, etc.. The teams should use this output of the application to write the report (annexes of the user manual).

### 1.3. Acceptance Criteria

* **AC1:** The goal of this US is to forecast property sale prices (only for houses and apartments) using information
  from the property area (in square feet), the distance from the center (in miles), the number of bedrooms, the number
  of bathrooms and the number of parking spaces features of each property. In this study, simple linear and a
  multi-linear regression models should be developed to find the linear relationship between: 1- each independent
  variable and the dependent variable; 2- all independent variables and the dependent variable. As the application is
  under development it is not possible to generate all data required for the analysis. Therefore, and to simulate a
  production system, we made available in moodle a CSV file (generated by the legacy system) containing information
  required for this study. The regression analysis should be documented in the application user manual (in the annexes)
  that must be delivered with the application. The report should include property values/prices (sale prices and
  forecast prices), the regression model used to estimate each value, R(SLR), R2 and R2 adjusted for SLR and MLR,
  confidence intervals and hypothesis tests for regression coefficients and significance model with Anova.
* **AC2:** All houses and apartments business registered in the system should be used to fit the regression models.
* **AC3:** The system should enable the actor to select each regression model and should present the price estimates
  using the selected model. Moreover, the system should show the selected regression model and all required statistics.
* **AC4:** To implement this US the org.apache.commons.math4.stat
  package (https://commons.apache.org/proper/commons-math/userguide/stat.html) should be used.

### 1.4. Found out Dependencies

n/a

### 1.5 Input and Output Data

**Input Data:**
* Selected data:
    * Type of regression
    * (optional) Independant variable

**Output Data:**

* Result data

### 1.6. System Sequence Diagram (SSD)

**Other alternatives might exist.**

#### Alternative One

![System Sequence Diagram - Alternative One](svg/us018-system-sequence-diagram-alternative-one.svg)


### 1.7 Other Relevant Remarks

n/a