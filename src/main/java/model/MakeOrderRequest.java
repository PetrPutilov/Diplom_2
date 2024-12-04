package model;

import java.util.List;

public class MakeOrderRequest {
    private List<String> ingredients;

    public MakeOrderRequest() {
    }

    public MakeOrderRequest(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
