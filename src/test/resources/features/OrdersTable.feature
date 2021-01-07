Feature: Orders item DB test


  Scenario: Add new data to DB orders
    Given remove all records from orders table
    When  add new data into the orders table
      | id | order_placed_at     | order_status | order_updated_at    | custom_user_id |
      | 1  | 2020-01-01 12:20:32 | 2            | 2020-01-12 12:40:01 | 1              |
      | 2  | 2020-06-12 10:00:01 | 1            | 2020-01-12 11:00:00 | 2              |
      | 3  | 2020-02-12 09:30:21 | 2            | 2020-01-12 10:22:00 | 1              |
    Then verify That new data has been inserted
      | id | order_placed_at     | order_status | order_updated_at    | custom_user_id |
      | 1  | 2020-01-01 12:20:32 | 2            | 2020-01-12 12:40:01 | 1              |
      | 2  | 2020-06-12 10:00:01 | 1            | 2020-01-12 11:00:00 | 2              |
      | 3  | 2020-02-12 09:30:21 | 2            | 2020-01-12 10:22:00 | 1              |

  @orders
  Scenario: add new data to DB column
    Given add new data to column order_status grouped with id
      | id | order_status |
      | 3  | 3            |
      | 2  | 3            |
      | 1  | 3            |

    Then  verify that new data has been inserted to order_status
      | id | order_placed_at     | order_status | order_updated_at    | custom_user_id |
      | 1  | 2020-01-01 12:20:32 | 3            | 2020-01-12 12:40:01 | 1              |
      | 2  | 2020-06-12 10:00:01 | 3            | 2020-01-12 11:00:00 | 2              |
      | 3  | 2020-02-12 09:30:21 | 3            | 2020-01-12 10:22:00 | 1              |

    And remove all orders placed after "'2020-06-12'"
    Then  verify that new data has been deleted from order_status
      | id | order_placed_at     | order_status | order_updated_at    | custom_user_id |
      | 1  | 2020-01-01 12:20:32 | 3            | 2020-01-12 12:40:01 | 1              |
      | 3  | 2020-02-12 09:30:21 | 3            | 2020-01-12 10:22:00 | 1              |
