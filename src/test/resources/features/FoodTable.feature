Feature: Food item DB test

  @food
  Scenario: Add new data to DB food
    Given remove all records from food table
    When  add new data into the food table
      | id | description | food_type | image_url    | name    | price |
      | 1  | fruits      | 12        | www.devX.com | Alex    | 20.43 |
      | 2  | drinks      | 13        | www.devX.com | Susanne | 23.67 |
      | 3  | vegetables  | 14        | www.devX.com | Eric    | 24.32 |
      | 4  | berries     | 15        | www.devX.com | Aslan   | 21.45 |
    Then Verify that new data has been inserted
      | id | description | food_type | image_url    | name    | price |
      | 1  | fruits      | 12        | www.devX.com | Alex    | 20.43 |
      | 2  | drinks      | 13        | www.devX.com | Susanne | 23.67 |
      | 3  | vegetables  | 14        | www.devX.com | Eric    | 24.32 |
      | 4  | berries     | 15        | www.devX.com | Aslan   | 21.45 |

    And update the price in food with "27.43" on id "1"
    Then Verify that new data has been inserted
      | id | description | food_type | image_url    | name    | price |
      | 1  | fruits      | 12        | www.devX.com | Alex    | 27.43 |
      | 2  | drinks      | 13        | www.devX.com | Susanne | 23.67 |
      | 3  | vegetables  | 14        | www.devX.com | Eric    | 24.32 |
      | 4  | berries     | 15        | www.devX.com | Aslan   | 21.45 |

    And delete the price in food with "27.43" on id "1"
    Then Verify that new data has been delete
      | id | description | food_type | image_url    | name    | price |
      | 2  | drinks      | 13        | www.devX.com | Susanne | 23.67 |
      | 3  | vegetables  | 14        | www.devX.com | Eric    | 24.32 |
      | 4  | berries     | 15        | www.devX.com | Aslan   | 21.45 |























#    Then verify that new price in food  has been updated
#      | id | description | food_type | image_url    | name    | price |
#      | 1  | fruits      | 12        | www.devX.com | Alex    | 27.43 |
#      | 2  | drinks      | 13        | www.devX.com | Susanne | 23.67 |
#      | 3  | vegetables  | 14        | www.devX.com | Eric    | 24.32 |
#      | 4  | berries     | 15        | www.devX.com | Aslan   | 21.45 |










#  Scenario: Add new data to DB food
#    Given remove all records from food table
#    When  add new data into the food table
#      | id | description | food_type | image_url | name   | price |
#      | 1  | student     | 88        | www.devX.com  | Azamat | 10.99 |
#      | 2  | student     | 99        | www.devX.com  | Irina  | 5.99  |
#      | 3  | student     | 66        | www.devX.com  | Jiyde  | 10.00 |
#      | 4  | student     | 55        | www.devX.com  | Elvina | 11.11 |
#    Then Verify that new data has been inserted
#      | id | description | food_type | image_url | name   | price |
#      | 1  | student     | 88        | www.devX.com  | Azamat | 10.99 |
#      | 2  | student     | 99        | www.devX.com  | Irina  | 5.99  |
#      | 3  | student     | 66        | www.devX.com  | Jiyde  | 10.00 |
#      | 4  | student     | 55        | www.devX.com  | Elvina | 11.11 |