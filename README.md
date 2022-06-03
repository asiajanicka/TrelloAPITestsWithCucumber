The project contains API tests in Java for web application Trello.

###Test are written using:
- BDD approach using Cucumber framework
- RestAssured - for API requests
- AssertJ - for tests assertions
- Allure - for reporting
- Lombok
- JUnit5

###Features covered by tests are:  
- Create board with following params:
    - board name 
      - **Defect found**: can't create board with name of max length specified in Trello spec
    - description 
      - **Defect found**: can't create board with description of max length specified in Trello spec
    - defaultLabels
    - defaultLists
    - workspace id
    - background color 
      - **Defect found**: when trying to create board with background color param set to blank or invalid, the response is with 500 status code, instead of 400
- Create board with given permission(access) level - e2e tests  
*(private / workspace visible / public)*

- Create board with given voting preferences - e2e tests  
*(Who can vote on the board)*
  - **Defect found**:  
    - Preconditions:  
      1) public or workspace visible board
      2) voting prefs set to only board members  
    - Steps:
        1) workspace member who isn't a board members tries to vote on the board  
    - Expected result:
      1) workspace member can not add vote  
    - Actual result:
      1) workspace member adds vote

- Create board with given comments preferences - e2e tests  
*(Who can comment on cards on the board)*
  - **Defect found**:  
    - Preconditions:  
      1) public or workspace visible board  
      2) comments prefs set to only board members  
    - Steps:
      1) workspace member who isn't a board members tries to add comment to card displayed on the board  
    - Expected result  
      1) workspace member can not add comment 
    - Actual result  
      1) workspace member adds comment

- Create board with given invitations preferences - e2e tests  
*(Determines what types of members can invite users to join the board)* 
  - **Defect found**:
    - Preconditions:
      1) boards with all permission levels
      2) invitations prefs set to any of only board members or admins
    - Steps:
      1) the account owner (Kate) adds user (Tom) to board as admin/board member
      2) Tom tries to invite other user to board
    - Expected result
      1) New user is added to board
      2) Status code 200
      3) Id of new board member is in board members list returned in response 
    - Actual result
      1) New user is added to board  
      2) Status code 200  
      3) Id of new board member is not added to board members list returned in response
  - **Defect found**:
    - Preconditions:
      1) public or workspace visible board
      2) invitations prefs set to only board members
    - Steps:
      1) workspace member who isn't a board members tries to invite other user to the board
    - Expected result
      1) workspace member can not invite other user
    - Actual result
      2) workspace member invites other user to board

- Create max number of boards limited for workspace tests


Links to Trello API endpoints covered by tests:
https://developer.atlassian.com/cloud/trello/rest/api-group-boards/#api-boards-post

###To serve test report with Allure on your computer:
1) run tests using: **mvn clean test**
2) generate a report with: **allure serve target/allure-results**  

Please remember that Allure must be installed according to the spec: https://docs.qameta.io/allure/contactUsForm