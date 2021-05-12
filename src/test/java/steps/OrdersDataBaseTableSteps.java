package steps;

import beans.FoodDeliveryApi1;
import beans.Orders;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import utils.beanutils.BeanHelper;
import utils.db.DataBaseUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class OrdersDataBaseTableSteps {


    //TODO: IMPLEMENT HERE

    //this is needed for deleting related table with primary and foreign key.
    private static final String DISABLE_FOREIGNKEY_CHECKS_QUERY = "SET FOREIGN_KEY_CHECKS = 0;";
    private static final String ENABLE_FOREIGNKEY_CHECKS_QUERY = "SET FOREIGN_KEY_CHECKS = 1;";

    private String query;
    private List<Map<String, Object>> dbResultSet;
    private List<Map<String, Object>> databaseResult;


    @Given("^remove all records from orders table$")
    public void remove_all_records_from_orders_table() throws Throwable {
        DataBaseUtils.executeQuery(DISABLE_FOREIGNKEY_CHECKS_QUERY);
        query = "TRUNCATE table orders;";
        DataBaseUtils.executeQuery(query);
        DataBaseUtils.executeQuery(ENABLE_FOREIGNKEY_CHECKS_QUERY);
        query = "SELECT * from orders;";
        dbResultSet = DataBaseUtils.executeQuery(query);
        Assert.assertTrue(dbResultSet.isEmpty());

    }

    @When("^add new data into the orders table$")
    public void add_new_data_into_the_orders_table(List<Orders> ordersList) throws Throwable {
        DataBaseUtils.executeQuery(DISABLE_FOREIGNKEY_CHECKS_QUERY);
        query = "INSERT INTO orders VALUES(?, ?, ?, ?, ?);";
        for (Orders orders : ordersList) {
            DataBaseUtils.executeInsert(query, orders, BeanHelper.getBeanPropertyNames(Orders.class));
        }
        DataBaseUtils.executeQuery(ENABLE_FOREIGNKEY_CHECKS_QUERY);

    }

    @Then("^verify That new data has been inserted$")
    public void verify_That_new_data_has_been_inserted(List<Orders> ordersList) throws Throwable {
        query = "Select * from orders order by id;";
        List<Orders> listItemsFromDB = DataBaseUtils.executeQueryToBean(Orders.class, query);
        List<Orders> itemsExpected = new ArrayList<>(ordersList);
        Collections.sort(listItemsFromDB);
        Collections.sort(itemsExpected);
        Assert.assertEquals("first assertion failure", listItemsFromDB.size(), itemsExpected.size());
        Assert.assertEquals("second assertion failure ", listItemsFromDB, itemsExpected);

    }


    @Given("^add new data to column order_status grouped with id$")
    public void addNewDataToColumnOrder_statusGroupedWithId(List<Orders> ordersList) throws SQLException {
        DataBaseUtils.executeQuery(DISABLE_FOREIGNKEY_CHECKS_QUERY);

        for (Orders o : ordersList) {
            query = "UPDATE orders SET order_status = " + o.getOrder_status() + " WHERE id = " + o.getId() + ";";
            DataBaseUtils.executeUpdate(query);
        }
        DataBaseUtils.executeQuery(ENABLE_FOREIGNKEY_CHECKS_QUERY);
    }

    @Then("^verify that new data has been inserted to order_status$")
    public void verifyThatNewDataHasBeenInsertedToOrder_status(List<Orders> ordersList) throws SQLException {

        query = "select * from orders;";
        List<Orders> ordersFromDataBase = DataBaseUtils.executeQueryToBean(Orders.class, query);
        ArrayList<Orders> ordersExpected = new ArrayList<>(ordersList);
        Assert.assertEquals("first assertion failure", ordersFromDataBase.size(), ordersExpected.size());
        Assert.assertEquals("second assertion failure ", ordersFromDataBase, ordersExpected);

//        ordersFromDataBase.forEach(System.out::println);
//        System.out.println();
//        ordersExpected.forEach(System.out::println);


    }

    @And("^remove all orders placed after \"([^\"]*)\"$")
    public void removeAllOrdersPlacedAfter(String time) throws Throwable {
        DataBaseUtils.executeQuery(DISABLE_FOREIGNKEY_CHECKS_QUERY);
        query = "SELECT * from orders WHERE order_placed_at > " + time + ";";
        List<Orders> ordersListFromDataBase = DataBaseUtils.executeQueryToBean(Orders.class, query);
        Assert.assertFalse("List is not empty failure ", ordersListFromDataBase.isEmpty());
        for (Orders o : ordersListFromDataBase) {
            query = "delete from orders where id = '" + o.getId() + "';";
            DataBaseUtils.executeQuery(query);
            System.out.println("this is id from data base----> " + o.getId());
        }


    }


    @Then("^verify that new data has been deleted from order_status$")
    public void verifyThatNewDataHasBeenDeletedFromOrder_status(List<Orders> ordersList) throws SQLException {
        query = "select * from orders;";
        List<Orders> ordersFromDataBase = DataBaseUtils.executeQueryToBean(Orders.class, query);
        ArrayList<Orders> ordersExpected = new ArrayList<>(ordersList);
        Assert.assertEquals("first/1 assertion failure", ordersFromDataBase.size(), ordersExpected.size());
        Assert.assertEquals("second/2  assertion failure ", ordersFromDataBase, ordersExpected);
        ordersFromDataBase.forEach(System.out::println);
        System.out.println();
        ordersExpected.forEach(System.out::println);


    }



}