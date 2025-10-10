package org.example;

import POJO.Request.Bookingdates;
import POJO.Request.CreatsBookingReq;
import POJO.Request.Response.CreateBookingRes;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class responseParsing {

    private final RequestSpecification requestSpecification = RestAssured.given()
            .and().baseUri("https://restful-booker.herokuapp.com")
            .and().filters(new RequestLoggingFilter(), new ResponseLoggingFilter());


    @Test
    public void parseResponseIntoPojo(){
        CreatsBookingReq requestBody = this.getCreatsBookingReqPojo("Sri", "BS", 500);
        Response response=this.requestSpecification
                .basePath("/booking")
                .and().contentType(ContentType.JSON)
                .and().body(requestBody)
                .when().post()
                .then().assertThat()
                .statusCode(200)
                .and().body("bookingid", is(not(equalTo(0))))
                .extract().response();
        CreateBookingRes createBookingRes = response.as(CreateBookingRes.class);
        Assert.assertEquals(createBookingRes.getBooking().getFirstName(),"Sri");
    }

    public CreatsBookingReq getCreatsBookingReqPojo(String firstName, String lastName, int totalPrice){

        CreatsBookingReq requestBody = new CreatsBookingReq();
        requestBody.setFirstName(firstName);
        requestBody.setLastName(lastName);
        requestBody.setTotalPrice(totalPrice);
        requestBody.setDepositPaid(true);
        requestBody.setAdditionalNeeds("BreakFast");
        Bookingdates bookingdates = new Bookingdates();
        bookingdates.setCheckin("2025-10-10");
        bookingdates.setCheckout("2025-10-11");
        requestBody.setBookingDates(bookingdates);
        return requestBody;

    }
}
