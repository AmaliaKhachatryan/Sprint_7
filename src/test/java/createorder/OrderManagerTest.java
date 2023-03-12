package createorder;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;


public class OrderManagerTest {
    private OrderManager manager;
    private int track;

    @BeforeClass
    public static void globalSetUp() {
        RestAssured.filters(
                new RequestLoggingFilter(), new ResponseLoggingFilter(),
                new AllureRestAssured());
    }

    @Before
    public void setUp() {
        manager = new OrderManager();
    }

    @Test
    public void getOrderByTrackWithValidNumberTest() {
        CreateOrder order = GeneratorOrder.getRandom();
        track = manager.create(order)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("track", notNullValue())
                .extract().path("track");
        GetTrackOrder newOrder = manager.getTrackByNumber(track)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .extract().body().as(GetTrackOrder.class);
    }

    @Test
    public void getOrderByTrackWithNotValidNumberTest() {
        manager.getTrackByNumber((int) (Math.random() * 50000))
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .assertThat()
                .body("message", equalTo("Заказ не найден"));

    }

    @Test
    public void getOrderByTrackWithoutNumberTest() {
        manager.getTrackWithoutNumber()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для поиска"));

    }
}

