package createorder;

import databaseuri.BaseURI;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderManager extends BaseURI {

    @Step("Create order")
    public ValidatableResponse create(CreateOrder order) {
        return given()
                .spec(getBaseReqSpec())
                .body(order)
                .when()
                .post(COURIER_URI_ORDER)
                .then();
    }

    @Step("Get Track")//Получить заказ по его номеру
    public ValidatableResponse getTrackByNumber(int number) {
        return given()
                .spec(getBaseReqSpec())
                .when()
                .queryParam("t", number)
                .get(COURIER_URI_ORDER_GET_TRACK)
                .then();
    }

    @Step("Get Track")//Получить заказ по его номеру
    public ValidatableResponse getTrackWithoutNumber() {
        return given()
                .spec(getBaseReqSpec())
                .when()
                .queryParam("t")
                .get(COURIER_URI_ORDER_GET_TRACK)
                .then();
    }
}
