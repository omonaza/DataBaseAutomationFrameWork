Feature: As a User I want to be able to register to the app, so I can use the food delivery service.

  Scenario: User should submit the following fields in order to successfully register to FoodDelivery app
    Given user registers to food delivery app with the following fields:
      | username | password | fullName | address     | city    | state | zip   | phone     |
      | John     | Test123  | John Doe | 123 main st | Chicago | IL    | 60625 | 112131321 |
    Then verify that status code is 200
    Then verify that response message is "User registration successful."
    Then verify that user information successfully saved in DB

  Scenario: User tries to register with an existing username.
    Given user registers to food delivery app with an existing username:
    Then verify that status code is 400
    Then verify that response message is "Username unavailable. Please choose another one."
    Then verify that user information successfully saved in DB


  Scenario:  user tries to register with empty username.
    Given user registers to food delivery app with empty username
    Then verify that status code is 400
    Then verify that response message is "Username cannot be null or empty."
    Then verify that user information is not saved in DB

  Scenario:  user tries to register with null username.
    Given user registers to food delivery app with null username
    Then verify that status code is 400
    Then verify that response message is "Username cannot be null or empty."
    Then verify that user information is not saved in DB

  Scenario: user tries to register with empty  fullname.
    Given user registers to food delivery app with empty fullname
    Then verify that status code is 400
    Then verify that response message is "Fullname cannot be null or empty."
    Then verify that user information is not saved in DB


  Scenario: user tries to register with null  fullname.
    Given user registers to food delivery app with null fullname
    Then verify that status code is 400
    Then verify that response message is "Fullname cannot be null or empty."
    Then verify that user information is not saved in DB

  Scenario: user tries to register without password.
    Given user registers to food delivery app without password
    Then verify that status code is 500
    Then verify that response message is "Password cannot be null or empty."
    Then verify that user information is not saved in DB

  @bug
  Scenario:user tries to register with empty  password.
    Given user registers to food delivery app empty password
    Then verify that status code is 500
    Then verify that response message is "Password cannot be null or empty."
    Then verify that user information is not saved in DB

  Scenario:user tries to register with null  password.
    Given user registers to food delivery app null password
    Then verify that status code is 500
    Then verify that response message is "Password cannot be null or empty."
    Then verify that user information is not saved in DB