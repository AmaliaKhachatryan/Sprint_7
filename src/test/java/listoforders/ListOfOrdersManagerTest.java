package listoforders;

import createcourier.CourierCreate;
import createcourier.CourierLogin;
import createcourier.CourierManager;
import createcourier.GeneratorCourier;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;


public class ListOfOrdersManagerTest {
    private CourierManager courierManager;
    private int courierId;
    private ListOfOrdersManager manager;


    @BeforeClass
    public static void globalSetUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter(),
                new AllureRestAssured());
    }

    @Before
    public void setUp() {
        manager = new ListOfOrdersManager();
        courierManager = new CourierManager();
    }

    @After
    public void removeCourier() {
        courierManager.removeCourier(courierId);
    }

    @Test
    public void getListTest() {
        ListGetOrders list = manager.getListOfOrders()
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .extract()
                .body()
                .as(ListGetOrders.class);
        MatcherAssert.assertThat(list, notNullValue());

    }

    @Test //v1/orders?limit=10&page=0&nearestStation=["110"]
    public void getListByParametersTest() {

        ListGetOrders list = manager.getListOfOrdersByParameters()
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .extract()
                .body()
                .as(ListGetOrders.class);
        MatcherAssert.assertThat(list, notNullValue());

    }

    @Test ///v1/orders?limit=10&page=0
    public void getListOfOrdersByLimitAndPage() {
        ListGetOrders list = manager.getListOfOrdersByLimitAndPage()
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .extract()
                .body()
                .as(ListGetOrders.class);
        MatcherAssert.assertThat(list, notNullValue());

    }

    @Test   //v1/orders?courierId=1
    public void getListByCourierIdTest() {
        CourierCreate courier = GeneratorCourier.getRandom();
        courierManager.create(courier);
        courierId = courierManager.login(CourierLogin.from(courier))
                .assertThat()
                .body("id", notNullValue())
                .extract().path("id");
        ListGetOrders list = manager.getListOfOrdersByCourierId(courierId)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .extract()
                .body()
                .as(ListGetOrders.class);
        MatcherAssert.assertThat(list, notNullValue());

    }

    @Test   //v1/orders?courierId=1
    public void getListByNotValidCourierIdTest() {
        int id = ((int) (Math.random() * 50));
        manager.getListOfOrdersByCourierId(id)
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .assertThat()
                .body("message", equalTo("Курьер с идентификатором " + id + " не найден"));

    }

    @Test  ///v1/orders?limit=10&page=0&nearestStation=["110"]
    public void getTenAvailableListOfOrdersTest() {
        ListGetOrders list = manager.getTenAvailableListOfOrders()
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .extract()
                .body()
                .as(ListGetOrders.class);
        MatcherAssert.assertThat(list, notNullValue());
    }
}