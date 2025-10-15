package org.example;

import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileWriter;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;


public class jsonschemavalidations {
    private final RequestSpecification requestSpecification = RestAssured.given()
            .baseUri("https://restful-booker.herokuapp.com")
            .filters(new RequestLoggingFilter(),new ResponseLoggingFilter(LogDetail.STATUS));
@Test()
    public Response getResponse() {
        Response response;
        response = requestSpecification
            .basePath("/booking/{bookingId}")
            .pathParam("bookingId",20)
            .get();

    return response;
}
@Test
 public void writeToJson() throws ParseException {

    String jsonResponse = getResponse().asString();
    String path = "C:\\Users\\jayas\\IntellijWorkSpace\\restApiEx\\src\\main\\resources\\Schema\\jsonSchema.json";
    JSONParser parser = new JSONParser();
     JSONObject object;
     object=(JSONObject) parser.parse(jsonResponse);
     try(FileWriter writer = new FileWriter(path)){
         writer.write(object.toJSONString());
         System.out.println("Response recorded in JSON Successfully");

     } catch (Exception e) {
         throw new RuntimeException(e);
     }
 }
 @Test
    public void jsonSchemaValidations(){
   Response inlineValidation=getResponse()
           .then()
           .assertThat()
           .body("bookingdates.checkin",is(notNullValue()))
           .and()
           .body(JsonSchemaValidator.matchesJsonSchema(new File("C:\\Users\\jayas\\IntellijWorkSpace\\restApiEx\\src\\main\\resources\\Schema\\jsonSchema.json")))
           .extract().response();
     System.out.println(inlineValidation.asString());
 }

}
