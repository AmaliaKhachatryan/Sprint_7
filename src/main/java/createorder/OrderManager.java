package createOrder;

import dataBaseURI.BaseURI;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderManager extends BaseURI {
    private static final String COURIER_URI = URI_SCOOTER_API + "orders/";
       @Step("Create order")
    public ValidatableResponse create(CreateOrder order) {
        return given()
                .spec(getBaseReqSpec())
                .body(order)
                .when()
                .post(COURIER_URI)
                .then();
    }
}
