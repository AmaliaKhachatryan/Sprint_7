package listoforders;

import databaseuri.BaseURI;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class ListOfOrdersManager extends BaseURI {

    @Step(" Get list of orders")
    public ValidatableResponse getListOfOrders() {
        return given()
                .spec(getBaseReqSpec())
                .when()
                .get(COURIER_URI_ORDER)
                .then();
    }

    @Step(" Get list of orders By Parameters")
    public ValidatableResponse getListOfOrdersByParameters() {
        return given()
                .queryParam("limit", (Math.random() * 30))
                .queryParam("page", (Math.random() * 10))
                .queryParam("nearestStation", (Math.random() * 110))
                .get(COURIER_URI_ORDER)
                .then();
    }

    @Step(" Get list of orders By Parameters")
    public ValidatableResponse getListOfOrdersByLimitAndPage() {
        return given()
                .queryParam("limit", 10)
                .queryParam("page", 0)
                .get(COURIER_URI_ORDER)
                .then();
    }

    @Step(" Get list of orders By id")
    public ValidatableResponse getListOfOrdersByCourierId(int courierId) {
        return given()
                .queryParam("courierId", courierId)
                .get(COURIER_URI_ORDER)
                .then();
    }

    //v1/orders?limit=10&page=0&nearestStation=["110"]
    @Step(" Get list of orders By Parameters")
    public ValidatableResponse getTenAvailableListOfOrders() {
        return given()
                .queryParam("limit", 10)
                .queryParam("page", 0)
                .queryParam("nearestStation", (Math.random() * 110))
                .get(COURIER_URI_ORDER)
                .then();
    }
}