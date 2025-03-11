# US 019 - Divide the seat of stores into two subsets

## 1. Requirements Engineering


### 1.1. User Story Description


As a network manager, I want to divide the set of all stores into two subsets, so that the total number of properties of the stores between the two subsets is the closest possible.


### 1.2. Customer Specifications and Clarifications 


**From the specifications document:**

> The manager of the network intends to analyse the performance of each of the branches and the
global behaviour of the network on a daily basis.



**From the client clarifications:**



### 1.3. Acceptance Criteria


- **AC1:** The application should implement a brute-force algorithm, an algorithm
  which consists of calculating all the partitions of cardinal 2 and finding out which
  one is the minimum for the difference of the sum of the elements of each sublist.
- **AC2:** The algorithm should return the sublists of the partition (each sublist should
  contain a set of tuples with two elements each, the storeID and the number of
  properties manage by the store) that satisfies the stated condition and also the
  difference between the sums of the elements of the sublists. In the user manual,
  this algorithm must be written in pseudocode.
- **AC3:** Runtime tests for variable length n input lists, with com n = 3, 6, 9, …, 30
  (asymptotic behavior) should be performed. In each run the number of stores
  should be chosen sequentially. In the user manual, the results of measuring the
  time of each algorithm for one/several inputs of the same size, as also the graphic
  of the asymptotic behavior of the execution time should be presented (use a unit
  of time that allows you to distinguish the running times of all tested examples).
  Also, it should be presented the input list, the two sublists that make up the
  partition and difference of sums, for at least one example of each input size.
- **AC4** Worst-case time complexity of the algorithm should be documented in the
  application user manual (in the annexes, where algorithms should be written in
  pseudocode) that must be delivered with the application.


### 1.4. Found out Dependencies

- **Depends on US003** : to register a new employee, the network manager must be registered in the system.


### 1.5 Input and Output Data

**Output Data:**

* Insucces/Success of the operation
* 2 Lists of Stores

### 1.6. System Sequence Diagram (SSD)

![us019-ssd](svg%2Fus019-ssd.svg)

### 1.7 Other Relevant Remarks

* Can be only one network manager
* There's only one network manager
* Network manager should be logged in the application

