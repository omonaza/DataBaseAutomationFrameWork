Feature: Cart item DB tests
  @cart_item
  Scenario: Add new data to DB cart_item
    Given remove all records from cart_item table
    When  add new data into the cart_items table
      |id   |quantity|total_price|food_id|
      | 101 |  11    |  12.34    | 1234  |
      | 102 |  34    |  90.11    | 1235  |
      | 103 |  13    |  78.99    |  1236 |
      | 104 |  18    |  45.46    | 1237  |
      | 105 |  19    |  23.78    | 1238  |
    Then verify that new data has been inserted
      |id   |quantity|total_price|food_id|
      | 101 |  11    |  12.34    | 1234  |
      | 102 |  34    |  90.11    | 1235  |
      | 103 |  13    |  78.99    |  1236 |
      | 104 |  18    |  45.46    | 1237  |
      | 105 |  19    |  23.78    | 1238  |

#  - Remove all records from food table  ---> homework
#  - Remove all records from orders table   ---> homework
#  - Add new data into the food tables and verify that new data has been inserted   ---> homework
#  - Add new data into the orders tables and verify that new data has been inserted   ---> homework
#  (make sure to insert order_placed_at field between that was placed after "2020-01-09"
#  and "2020-01-18")   ---> homework