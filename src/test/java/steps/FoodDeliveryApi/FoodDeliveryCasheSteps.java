package steps.FoodDeliveryApi;

import beans.FoodDeliveryApi2;
import beans.FoodResponse;
import com.google.gson.Gson;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.restassured.response.Response;
import org.junit.Assert;
import utils.restapi.RestApiUtils;

import java.util.List;

public class FoodDeliveryCasheSteps {
    private Gson gson = new Gson();
    private String jsonBody;
    private Response response;
    private List<FoodDeliveryApi2> expectedFoodDelivery;


    @Given("^user list all food in cache$")
    public void userListAllFoodInCache() {
        response = RestApiUtils.requestSpecification()
                .get("food/cache/list");


    }

    @Then("^verify that Status code is (\\d+)$")
    public void verifyThatStatusCodeIs(int StatusCode) {
        response.then().assertThat().statusCode(StatusCode);
    }

    @Then("^verify that response contains all cached foods$")
    public void verifyThatResponseContainsAllCachedFoods(List<FoodDeliveryApi2> expectedFood) {
        FoodResponse actualFood = gson.fromJson(response.asString(), FoodResponse.class);
        for (int i = 0; i < actualFood.getFoodCached().size(); i++) {
            if (actualFood.getFoodCached().get(i).getFoodType().equals(expectedFood.get(0).getFoodType())
                    && actualFood.getFoodCached().get(i).getDescription().equals(expectedFood.get(0).getDescription())) {
                Assert.assertEquals(expectedFood.get(0).getFoodType(), actualFood.getFoodCached().get(i).getFoodType());
                Assert.assertEquals(expectedFood.get(0).getDescription(), actualFood.getFoodCached().get(i).getDescription());
                Assert.assertEquals(expectedFood.get(0).getImageUrl(), actualFood.getFoodCached().get(i).getImageUrl());
                Assert.assertEquals(expectedFood.get(0).getPrice(), actualFood.getFoodCached().get(i).getPrice());
                Assert.assertEquals(expectedFood.get(0).getName(), actualFood.getFoodCached().get(i).getName());


            }

        }
        response.prettyPrint();
    }
}