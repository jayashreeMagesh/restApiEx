package org.example;

import POJO.Bookingdates;
import POJO.CreatesBookingReq;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;

public class postRequest {

    private final RequestSpecification requestSpecification = RestAssured.given()
            .baseUri("https://restful-booker.herokuapp.com")
            .filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

    @Test
    public void createPostRequest() {
        Map<String, Object> requestBody = createPayload("Sri", "BS", 500);
        Response response = this.requestSpecification
                .basePath("/booking")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().post()
                .then().assertThat()
                .statusCode(200)
                .body("bookingid", is(not(equalTo(0))))
                .extract().response();
    }

    @Test
    public void createPostRequestWithPojo() {
        CreatesBookingReq requestBody = this.getCreatesBookingReqPojo("Sri", "BS", 500);
        Response response = this.requestSpecification
                .basePath("/booking")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().post()
                .then().assertThat()
                .statusCode(200)
                .body("bookingid", is(not(equalTo(0))))
                .extract().response();
    }

    public CreatesBookingReq getCreatesBookingReqPojo(String firstName, String lastName, int totalPrice) {
        CreatesBookingReq requestBody = new CreatesBookingReq();
        requestBody.setFirstName(firstName);
        requestBody.setLastName(lastName);
        requestBody.setTotalPrice(totalPrice);
        requestBody.setDepositPaid(true);
        requestBody.setAdditionalNeeds("BreakFast");

        Bookingdates bookingDates = new Bookingdates();
        bookingDates.setCheckin("2025-10-10");
        bookingDates.setCheckout("2025-10-11");
        requestBody.setBookingDates(bookingDates);

        return requestBody;
    }

    @Test
    public void updateRequest() {
        Map<String, Object> requestBody = createPayload("Sridhar", "BS", 500);
        Response response = this.requestSpecification
                .basePath("/booking/{bookingId}")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .pathParam("bookingId", 2261)
                .auth().preemptive().basic("admin", "password123")
                .when().put()
                .then().assertThat()
                .statusCode(200)
                .extract().response();
    }

    public Map<String, Object> createPayload(String firstName, String lastName, int totalPrice) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("firstname", firstName);
        payload.put("lastname", lastName);
        payload.put("totalprice", totalPrice);
        payload.put("depositpaid", false);
        payload.put("additionalneeds", "Breakfast");

        Map<String, Object> bookingDates = new HashMap<>();
        bookingDates.put("checkin", "2025-10-10");
        bookingDates.put("checkout", "2025-10-11");
        payload.put("bookingdates", bookingDates);

        return payload;
    }

}
