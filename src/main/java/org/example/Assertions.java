package org.example;

import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.regex.Matcher;

public class Assertions {

    private final RequestSpecification requestSpecification = RestAssured.given()
            .and().baseUri("https://restful-booker.herokuapp.com")
            .and().filters(new RequestLoggingFilter(),new ResponseLoggingFilter());
    @Test
    public void getRequest()
    {
        Response inlineResponse = requestSpecification
                .basePath("/booking/{bookingId}")
                .pathParam("bookingId",10)
                .when().get().then().assertThat()
                .body("bookingdates.checkin",Matchers.equalTo("2020-06-08")).extract().response();
Map<String,Object> bookingDateMap=inlineResponse.jsonPath().getMap("bookingdates");
        System.out.println(inlineResponse);
        System.out.println(bookingDateMap);
    }

}
