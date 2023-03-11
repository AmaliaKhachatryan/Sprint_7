package listOfOrders;

import dataBaseURI.BaseURI;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class ListOfOrdersManager extends BaseURI {
    private static final String COURIER_URI = URI_SCOOTER_API + "orders/";

    @Step(" Get list of orders")
    public ValidatableResponse getListOfOrders() {
        return given()
                .spec(getBaseReqSpec())
                .when()
                .get(COURIER_URI)
                .then();
    }
}