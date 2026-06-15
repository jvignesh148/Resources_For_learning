Cucumber
----------

TDD - Test Driven Development

Ex: JUnit/TestNG (for Java), NUnit (for .NET), PyTest (for Python), Jasmine (for JavaScript)


BDD - Behavioral Driven Development

Ex:	Cucumber (supports multiple languages like Java, Ruby, JavaScript, etc.)
	SpecFlow (for .NET)
	Behave (for Python)
	JBehave (for Java)

team
----
stake holders/customer (non-tech)
product manager (non-tech)
project manager - (non-tech)
tester - tech
developer - tech
scrum master (non-tech)

BDD, or Behavior-Driven Development, emphasizes collaboration between technical and non-technical stakeholders 
through the use of a common language to describe the behavior of a system. 
It focuses on writing tests in a human-readable format, typically using a "given-when-then" structure, which helps clarify requirements and expectations.


Cucumber
-----------
1) Feature file
2) step definitions file
3) test runner file

Feature file contains scenarios & Steps.

Gherkin - language contains keywords..

Scenario
Given
When
Then
And


Pre-condition  - Given
Actions  - When
Validations  - Then


Login.feature
------------
Feature : User login
	Scenario: Succesful login
		Given the user opens application
		And  the user navigate to login page
		When the user entered valid user name and valid password
		And the user clicked on submit button.
		Then the user should see My account page.
		And the user name should be displayed on my account page


Step definition file contains methods which are represent to steps in feature file

testRunner class  ---> feature file, steps, report etc...



Cucumber project setup
--------------
1) Install cucumber eclipse plugin ( From eclipse market place)
2) Create Maven project
3) Update pom.xml with required dependencies.












