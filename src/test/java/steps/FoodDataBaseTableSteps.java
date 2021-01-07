package steps;

import beans.CartItems;
import beans.Food;
import cucumber.api.DataTable;
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

public class FoodDataBaseTableSteps {

    //this is needed for deleting related table with primary and foreign key.
    private static final String DISABLE_FOREIGNKEY_CHECKS_QUERY = "SET FOREIGN_KEY_CHECKS = 0;";
    private static final String ENABLE_FOREIGNKEY_CHECKS_QUERY = "SET FOREIGN_KEY_CHECKS = 1;";


    private String query;
    private List<Map<String, Object>> dbResultSet;

    @Given("^remove all records from food table$")
    public void remove_all_records_from_food_table() throws Throwable {
        DataBaseUtils.executeQuery(DISABLE_FOREIGNKEY_CHECKS_QUERY);
        query = "TRUNCATE table food;";
        DataBaseUtils.executeQuery(query);
        DataBaseUtils.executeQuery(ENABLE_FOREIGNKEY_CHECKS_QUERY);
        query = "SELECT * from food;";
        dbResultSet = DataBaseUtils.executeQuery(query);
        Assert.assertTrue(dbResultSet.isEmpty());

    }

    @When("^add new data into the food table$")
    public void add_new_data_into_the_food_table(List<Food> foodList) throws Throwable {
        DataBaseUtils.executeQuery(DISABLE_FOREIGNKEY_CHECKS_QUERY);
        query = "INSERT INTO food VALUES(?, ?, ?, ?, ?, ?);";
        for (Food food : foodList) {
            DataBaseUtils.executeInsert(query, food, BeanHelper.getBeanPropertyNames(Food.class));
        }
        DataBaseUtils.executeQuery(ENABLE_FOREIGNKEY_CHECKS_QUERY);


    }

    @Then("^Verify that new data has been (?:inserted|delete)$")
    public void verify_that_new_data_has_been_inserted(List<Food> foodList) throws Throwable {
        query = "Select * from food order by id;";
        List<Food> listItemsFromDB = DataBaseUtils.executeQueryToBean(Food.class, query);
        List<Food> itemsExpected = new ArrayList<>(foodList);
        Collections.sort(listItemsFromDB);
        Collections.sort(itemsExpected);
        Assert.assertEquals(listItemsFromDB.size(), itemsExpected.size());
        Assert.assertEquals(listItemsFromDB, itemsExpected);

    }


    @And("^update the price in food with \"([^\"]*)\" on id \"([^\"]*)\"$")
    public void updateThePriceInFoodWithOnId(double price, int id) throws Throwable {
        DataBaseUtils.executeQuery(DISABLE_FOREIGNKEY_CHECKS_QUERY);
        query = "UPDATE food\n" +
                "SET price = "+price+"\n" +
                "WHERE id = "+id+";";

       DataBaseUtils.executeQuery(query);
        DataBaseUtils.executeQuery(ENABLE_FOREIGNKEY_CHECKS_QUERY);


    }

    @And("^delete the price in food with \"([^\"]*)\" on id \"([^\"]*)\"$")
    public void deleteThePriceInFoodWithOnId(double price, String id) throws Throwable {
        DataBaseUtils.executeQuery(DISABLE_FOREIGNKEY_CHECKS_QUERY);
        query = "DELETE FROM food WHERE price = "+price+" and id = "+id+";";
        DataBaseUtils.executeQuery(query);
        DataBaseUtils.executeQuery(ENABLE_FOREIGNKEY_CHECKS_QUERY);

    }


}
