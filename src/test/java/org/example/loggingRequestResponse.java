package org.example;

import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestLogSpecification;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class loggingRequestResponse {

    RequestLoggingFilter requestLoggingFilter;
    ResponseLoggingFilter responseLoggingFilter;


    @Test
    public void getRequest()
    {
        RequestSpecification requestSpecification = RestAssured.given();
        requestLoggingFilter = new RequestLoggingFilter(LogDetail.ALL);
        //Create a new response logging filter
        responseLoggingFilter = new ResponseLoggingFilter(LogDetail.STATUS);
        Response response = requestSpecification.baseUri("https://restful-booker.herokuapp.com")
                .basePath("/booking/{bookingId}")
                .pathParam("bookingId",10)
                .contentType(ContentType.JSON)
                .filters(requestLoggingFilter,responseLoggingFilter).get();
        response.prettyPrint();
    }
}
