Feature: verify food cashe

  @checkingFoodsCahse
  Scenario Outline:  check all added foods in cache
    Given user list all food in cache
    Then verify that Status code is 200
    Then verify that response contains all cached foods
      | description   | imageUrl   | price   | name   | foodType   |
      | <description> | <imageUrl> | <price> | <name> | <foodType> |
    Examples:
      | description | imageUrl        | price | name   | foodType   |
      | Wine        | https:foods.com | 20.00 | Merlot | Beverages  |
      | Wine        | https:foods.com | 20.00 | Merlot | Appetizers |
      | Wine        | https:foods.com | 20.00 | Merlot | MainDish   |



