Feature: Food functionality

  Scenario: User adds food / happy path
    Given add new food to FoodDelivery with the following fields
      | description | imageUrl        | price | name   | foodType  |
      | Wine        | https:foods.com | 20.00 | Merlot | Beverages |
    Then verify that status code is 200
    Then verify that food has been successfully added

  Scenario: user adds food without image url
    Given add new food to FoodDelivery without image url
      | description | price | name   | foodType  |
      | Wine        | 20.00 | Merlot | Beverages |
    Then verify that status code is 403
    Then verify response error message "Invalid request - Food image url cannot be null or empty."


  Scenario: user adds food without price
    Given add new food to FoodDelivery without price
      | description | imageUrl        | name   | foodType  |
      | Wine        | https:foods.com | Merlot | Beverages |
    Then verify that status code is 403
    Then verify response error message "Invalid request - Food price cannot be null or empty."

  Scenario: user adds food without a name
    Given add new food to FoodDelivery without name
      | description | imageUrl        | price | foodType  |
      | Wine        | https:foods.com | 20.00 | Beverages |
    Then verify that status code is 403
    Then verify response error message "Invalid request - Food name cannot be null or empty."

  Scenario: user adds food with an empty  name
    Given add new food to FoodDelivery with an empty name
      | description | imageUrl        | price | foodType  |
      | Wine        | https:foods.com | 20.00 | Beverages |
    Then verify that status code is 403
    Then verify response error message "Invalid request - Food name cannot be null or empty."

  Scenario: user adds food with a  null name
    Given add new food to FoodDelivery with null name
      | description | imageUrl        | price | foodType  |
      | Wine        | https:foods.com | 20.00 | Beverages |
    Then verify that status code is 403
    Then verify response error message "Invalid request - Food name cannot be null or empty."

  Scenario: user adds food without food type
    Given add new food to FoodDelivery without food type
      | description | imageUrl        | name   | price |
      | Wine        | https:foods.com | Merlot | 20.00 |
    Then verify that status code is 403
    Then verify response error message "Invalid request - Food type cannot be null or empty."

  Scenario: user adds food with invalid food type
    Given add new food to FoodDelivery with invalid food type
      | description | imageUrl        | name   | price | foodType |
      | Wine        | https:foods.com | Merlot | 20.00 | Soups    |
    Then verify that status code is 400
    Then verify response error message "Bad Request"

  Scenario: User should be able to list all added foods.
    Given user list all food in cache
    Then verify that status code is 200
    Then verify that response contains all cached foods

  Scenario: user updates food price with out of range price
    Given user updates "T-Bone steak"'s price to 125.50
    Then verify that status code is 403
    Then verify that error message "Invalid request - Food price should be kept less than 125" is displayed

  Scenario: User commits all changes to DB
    Given user saves all chached food
    Then verify that status code is 200
    Then verify number of saved food
    Then verify response message "Food Cache is committed to db"
    Then verify that all food information is saved in DB

  Scenario: user commits changes excluding
    Given user saves all chached food excluding "Diet Coke"
    Then verify that status code is 200
    Then verify number of saved food
    Then verify response message "Food Cache is committed to db"
    Then verify that all food information is saved in DB