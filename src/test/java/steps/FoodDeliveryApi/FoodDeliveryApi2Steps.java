package steps.FoodDeliveryApi;

import beans.Food;
import beans.FoodDeliveryApi1;
import beans.FoodDeliveryApi2;
import beans.FoodResponse;
import com.google.gson.Gson;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;
import utils.restapi.RestApiUtils;

import java.util.List;
import java.util.Map;

public class FoodDeliveryApi2Steps {
    private Gson gson = new Gson();
    private String jsonBody;
    private Response response;
    private List<FoodDeliveryApi2> expectedFoodDelivery;
    private String QUERY;
    private List<Object> databaseResult;

    @Given("^add new food to FoodDelivery (?:with|without)|(?:the following fields|image url|price)$")
    public void addNewFoodToFoodDeliveryWithTheFollowingFields(List<FoodDeliveryApi2> foods) {
        for (FoodDeliveryApi2 food : foods) {
            if(food.getName().equals("null")){
                food.setName(null);
            }
            jsonBody = gson.toJson(food);
            response = RestApiUtils.requestSpecification()
                    .body(jsonBody)
                    .post("food/cache/add");
        }
        expectedFoodDelivery = foods;

    }

    @Then("^Verify that status code is (\\d+)$")
    public void verifyThatStatusCodeIs(int StatusCode) {
        response.then().assertThat().statusCode(StatusCode);

    }

    @Then("^verify that food has been successfully added$")
    public void verifyThatFoodHasBeenSuccessfullyAdded(List<FoodDeliveryApi2> expectedFood) {
        FoodResponse actualFood = gson.fromJson(response.body().asString(), FoodResponse.class);

             for(int i = 0 ; i <actualFood.getFoodCached().size();i++){
                 if(actualFood.getFoodCached().get(i).getDescription().equals(expectedFood.get(0).getDescription())){
                     Assert.assertEquals(expectedFood.get(0).getFoodType(), actualFood.getFoodCached().get(i).getFoodType());
                     Assert.assertEquals(expectedFood.get(0).getImageUrl(), actualFood.getFoodCached().get(i).getImageUrl());
                     Assert.assertEquals(expectedFood.get(0).getPrice(), actualFood.getFoodCached().get(i).getPrice());
                     Assert.assertEquals(expectedFood.get(0).getName(), actualFood.getFoodCached().get(i).getName());
                 }


             }


            }


    @Then("^verify response error message$")
    public void verifyResponseErrorMessage(List<Map<String, String>> expectedErrorMessage) {
        FoodResponse foodResponse = gson.fromJson(response.body().asString(),FoodResponse.class);
        Assert.assertEquals(expectedErrorMessage.get(0).get("errorMessage"), foodResponse.getErrorMessage());
    }


    @Then("^verify response error message \"([^\"]*)\" in \"([^\"]*)\"$")
    public void verifyResponseErrorMessageIn(String message, String field) throws Throwable {
        response.then().assertThat().body(field, Matchers.equalTo(message));
    }


}




