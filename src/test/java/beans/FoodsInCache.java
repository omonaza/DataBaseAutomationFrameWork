package beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter
@Setter
@ToString
public class FoodsInCache {

    private String numberOfFoodsInCache;
    private String numberOfAppetizers;
    private String numberOfMainDishes;
    private String numberOfUnknownFood;
    private String foodCached;
    private List<FoodResponse> objects;
}
