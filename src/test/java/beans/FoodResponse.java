package beans;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class FoodResponse {
    private List<FoodDeliveryApi2> foodCached;
    private String errorMessage;
}
