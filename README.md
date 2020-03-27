# testCalc

**Instructions:**  
The attached application can be deployed on any java web server. We recommend Apache Tomcat.   
Description of the application and its interfaces is available on the app home page.  
This app was developed with basic scenarios for testing (web services, UI).  
Before implementation of new features and larger refactoring the developers asked for test automation.  
Propose the technology you would use and implement a subset of the proposed tests. 
If you find any errors within an app, report them.  

**What do we expect:**  
Short summary - how you approached the solution and what did you consider:  
Test scripts - as attachment.  
Example of bug reports for selected issues.  
  
  
## Overview
├── test-automation             # Automated tests for WebUI and Rest API  
    ├── src/main                  
        ├── java/com/calc       # Base classes for tests  
        ├── resources           # Selenium webdriver executables  
    ├── src/test                     
        ├── java/com/calc       # Tests  
        └── resources           # Test data Excel spreadsheet with test combinations    
├── test-jmeter                 # JMeter performance tests  
├── test-plan                   # Test plan, bugs (pdf), models for pairwise testing tool (PICT)  
  
## Project setup  
See */test-plan/Test Plan.pdf* for details  

