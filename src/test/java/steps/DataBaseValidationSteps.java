package steps;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import beans.CartItems;
import beans.Food;
import beans.Orders;
import cucumber.api.java.en.When;
import org.junit.Assert;
import utils.beanutils.BeanHelper;
import utils.db.DataBaseUtils;

import java.sql.SQLException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DataBaseValidationSteps {

    //TODO: IMPLEMENT HERE

    //this is needed for deleting related table with primary and foreign key.
    private static final String DISABLE_FOREIGNKEY_CHECKS_QUERY = "SET FOREIGN_KEY_CHECKS = 0;";
    private static final String ENABLE_FOREIGNKEY_CHECKS_QUERY = "SET FOREIGN_KEY_CHECKS = 1;";

    private String query;
    private List<Map<String, Object>> dbResultSet;

    @Given("^remove all records from cart_item table$")
    public void remove_all_records_from_cart_item_table() throws Throwable {
        DataBaseUtils.executeQuery(DISABLE_FOREIGNKEY_CHECKS_QUERY);
        query = "TRUNCATE table cart_item;";
        DataBaseUtils.executeQuery(query);
        DataBaseUtils.executeQuery(ENABLE_FOREIGNKEY_CHECKS_QUERY);
        query = "SELECT * from cart_item;";
        dbResultSet = DataBaseUtils.executeQuery(query);
        Assert.assertTrue(dbResultSet.isEmpty());

    }

    @When("^add new data into the cart_items table$")
    public void add_new_data_into_the_cart_items_table(List<CartItems> items) throws Throwable {
        DataBaseUtils.executeQuery(DISABLE_FOREIGNKEY_CHECKS_QUERY);
        query = "INSERT INTO cart_item VALUES(?, ?, ?, ?);";
        for (CartItems cart_item : items) {
            DataBaseUtils.executeInsert(query, cart_item, BeanHelper.getBeanPropertyNames(CartItems.class));
        }
        DataBaseUtils.executeQuery(ENABLE_FOREIGNKEY_CHECKS_QUERY);


    }

    @Then("^verify that new data has been inserted$")
    public void verify_that_new_data_has_been_inserted(List<CartItems> items) throws Throwable {
        query = "Select * from cart_item order by id;";
        List<CartItems> listItemsFromDB = DataBaseUtils.executeQueryToBean(CartItems.class, query);
        List<CartItems> itemsExpected = new ArrayList<>(items);
        Collections.sort(listItemsFromDB);
        Collections.sort(itemsExpected);
        Assert.assertEquals(listItemsFromDB.size(), itemsExpected.size());
        Assert.assertEquals(listItemsFromDB, itemsExpected);

    }



}
