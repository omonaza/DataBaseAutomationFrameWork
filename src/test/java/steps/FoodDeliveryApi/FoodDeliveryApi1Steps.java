package steps.FoodDeliveryApi;

import beans.FoodDeliveryApi1;
import com.google.gson.Gson;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;
import utils.db.DataBaseUtils;
import utils.restapi.RestApiUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class FoodDeliveryApi1Steps {

    private Gson gson = new Gson();
    private String jsonBody;
    private Response response;
    private List<FoodDeliveryApi1> expectedFoodDelivery;
    private String QUERY;
    private List<Map<String, Object>> databaseResult;


    @Given("^user registers to food delivery app(?: with | without )|(?:the following fields|empty username|an existing username|null username|empty fullname| out password |null fullname| without password |null password|empty password):$")
    public void user_registers_to_food_delivery_app_with_the_following_fields(List<FoodDeliveryApi1> foods) throws Throwable {

        for (FoodDeliveryApi1 food : foods) {
            if (food.getUsername().equals("null")) {
                food.setUsername(null);
            } else if (food.getFullName().equals("null")) {
                food.setFullName(null);
            } else if (food.getPassword().equals("null")) {
                food.setPassword(null);
            }
            jsonBody = gson.toJson(food);
            response = RestApiUtils.requestSpecification()
                    .body(jsonBody)
                    .post("user/registration");
        }
        expectedFoodDelivery = foods;

    }


    @Then("^verify that status code is (\\d+)$")
    public void verify_that_status_code_is(int StatusCode) throws Throwable {
        response.then().assertThat().statusCode(StatusCode);
    }


    @Then("^verify that response message is \"([^\"]*)\" in \"([^\"]*)\"$")
    public void verify_that_response_message_is_in(String message, String field) throws Throwable {
        response.prettyPrint();
        response.then().assertThat().body(field, Matchers.equalTo(message));
    }

    @Then("^verify that user information successfully saved in DB$")
    public void verify_that_user_information_successfully_saved_in_DB() throws Throwable {
        for (int i = 0; i < expectedFoodDelivery.size(); i++) {
            QUERY = "select b.username, b.password, a.full_name as fullName, a.address, a.city, a.state, a.zip, a.phone from food_delivery_db.user_profile a join food_delivery_db.custom_user b\n" +
                    "on b.user_profile_id = a.id where b.username = '" + expectedFoodDelivery.get(i).getUsername() + "';";
            databaseResult = DataBaseUtils.executeQuery(QUERY);
            List<FoodDeliveryApi1> actual = DataBaseUtils.executeQueryToBean(FoodDeliveryApi1.class, QUERY);
            actual.get(i).setPassword(expectedFoodDelivery.get(i).getPassword());
            assertEquals("Failed: Mismatch in lists size", expectedFoodDelivery.size(), actual.size());
            assertEquals("Failed: Mismatch in data", expectedFoodDelivery, actual);

        }
    }

    @Then("^remove record with username from food_delivery_db table$")
    public void removeRecordWithUsernameFromFood_delivery_dbTable(List<String> usernames) throws SQLException {
        for (int i = 0; i < usernames.size(); i++) {
            QUERY = "DELETE FROM food_delivery_db.custom_user WHERE username ='" + usernames.get(i) + "';";
            DataBaseUtils.executeQuery(QUERY);
            QUERY = "select b.username, b.password, a.full_name as fullName, a.address, a.city, a.state, a.zip, a.phone " +
                    "from food_delivery_db.user_profile a join food_delivery_db.custom_user b\n" +
                    "on b.user_profile_id = a.id where b.username = '" + usernames.get(i) + "';";
            databaseResult = DataBaseUtils.executeQuery(QUERY);
            List<FoodDeliveryApi1> actual = DataBaseUtils.executeQueryToBean(FoodDeliveryApi1.class, QUERY);
            Assert.assertTrue(actual.isEmpty());

        }

    }

    @Then("^verify that user information is not saved in DB$")
    public void verifyThatUserInformationIsNotSavedInDB() throws SQLException {

//        for (int i = 0; i < expectedFoodDelivery.size(); i++) {
//            QUERY = "select b.username, b.password, a.full_name as fullName, a.address, a.city, a.state, a.zip, a.phone from food_delivery_db.user_profile a join food_delivery_db.custom_user b\n" +
//                    "on b.user_profile_id = a.id where b.username = '" + expectedFoodDelivery.get(i).getUsername() + "';";
//            databaseResult = DataBaseUtils.executeQuery(QUERY);
//            List<FoodDeliveryApi1> actual = DataBaseUtils.executeQueryToBean(FoodDeliveryApi1.class, QUERY);
//            Assert.assertFalse(expectedFoodDelivery.isEmpty());
//            Assert.assertTrue(actual.isEmpty());
//
//        }


        for (int i = 0; i < expectedFoodDelivery.size(); i++) {
            if (null == expectedFoodDelivery.get(i).getUsername()) {
                QUERY = "select b.username, b.password, a.full_name as fullName, a.address, a.city, a.state, a.zip, a.phone from " +
                        "food_delivery_db.user_profile a join food_delivery_db.custom_user b on b.user_profile_id = " +
                        "a.id where a.full_name = '" + expectedFoodDelivery.get(i).getFullName() + "' and " +
                        "a.address = '" + expectedFoodDelivery.get(i).getAddress() + "' and " +
                        "a.phone = '" + expectedFoodDelivery.get(i).getPhone() + "';";
            } else {
                QUERY = "select b.username, b.password, a.full_name as fullName, a.address, a.city, a.state, a.zip, a.phone from " +
                        "food_delivery_db.user_profile a join food_delivery_db.custom_user b on b.user_profile_id = " +
                        "a.id where b.username = '" + expectedFoodDelivery.get(i).getUsername() + "';";
            }
            databaseResult = DataBaseUtils.executeQuery(QUERY);
            Assert.assertTrue(databaseResult.isEmpty());
            System.out.println(databaseResult);
        }
    }



}



